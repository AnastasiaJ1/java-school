package com.digdes.school.project.integration;




import com.digdes.school.project.input.EmployeeDTO;
import com.digdes.school.project.input.UserDTO;
import com.digdes.school.project.output.EmployeeOutDTO;
import com.digdes.school.project.output.UserOutDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = IntegrationTest.DataSourceInitializer.class)
@Testcontainers
@TestMethodOrder(MethodOrderer.MethodName.class)
public class IntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:12.9-alpine");

    static {
        database.start();
    }

    HttpHeaders headers = new HttpHeaders();

    @Autowired
    private TestRestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Order(1)
    public void testAEmployeeCreate() throws Exception {


        UserDTO userDTO = new UserDTO("user1", "user1");
        HttpEntity<UserDTO> entity = new HttpEntity<>(userDTO, headers);
        //create admin
        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/auth/signup", HttpMethod.POST,
                entity, Void.class);
        assert response.getStatusCode().equals(HttpStatus.OK);

        //create user
        userDTO = new UserDTO("user2", "user2");
        entity = new HttpEntity<>(userDTO, headers);
        response = restTemplate.exchange(
                "/api/auth/signup", HttpMethod.POST,
                entity, Void.class);

        assert response.getStatusCode().equals(HttpStatus.OK);


        //create employee
        // тест-кейс 1
        EmployeeDTO employeeDTO = EmployeeDTO.builder().firstname("Иван").lastname("Иванов").build();
        HttpEntity<EmployeeDTO> request = new HttpEntity<>(employeeDTO, headers);
        ResponseEntity<UserOutDTO> response1 = restTemplate.withBasicAuth("user2", "user2").
                exchange("/api/empl/create", HttpMethod.POST, request, UserOutDTO.class);
        assert response1.getStatusCode().equals(HttpStatus.CREATED);
        UUID user2Id = response1.getBody().getId();


        // тест-кейс 2
        employeeDTO = EmployeeDTO.builder().firstname("Иван").lastname("Иванов").build();
        request = new HttpEntity<>(employeeDTO, headers);
        response1 = restTemplate.withBasicAuth("user1", "user1").
                exchange("/api/empl/create", HttpMethod.POST, request, UserOutDTO.class);
        assert response1.getStatusCode().equals(HttpStatus.FORBIDDEN);

        // тест-кейс 3
        employeeDTO = EmployeeDTO.builder().build();
        request = new HttpEntity<>(employeeDTO, headers);
        response1 = restTemplate.withBasicAuth("user2", "user2").
                exchange("/api/empl/create", HttpMethod.POST, request, UserOutDTO.class);
        assert response1.getStatusCode().equals(HttpStatus.BAD_REQUEST);





    }

    @Test
    @Order(2)
    public void testBEmployeeUpdate() throws Exception {
        //create user
        UserDTO userDTO = new UserDTO("user3", "user3");
        HttpEntity<UserDTO> entity = new HttpEntity<>(userDTO, headers);
        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/auth/signup", HttpMethod.POST,
                entity, Void.class);

        assert response.getStatusCode().equals(HttpStatus.OK);


        //create employee
        EmployeeDTO employeeDTO = EmployeeDTO.builder().firstname("Иван").lastname("Иванов").build();
        HttpEntity<EmployeeDTO> request = new HttpEntity<>(employeeDTO, headers);
        ResponseEntity<UserOutDTO> response1 = restTemplate.withBasicAuth("user3", "user3").
                exchange("/api/empl/create", HttpMethod.POST, request, UserOutDTO.class);
        assert response1.getStatusCode().equals(HttpStatus.CREATED);
        UUID user2Id = response1.getBody().getId();

        //update employee
        // тест-кейс 4
        employeeDTO = EmployeeDTO.builder().firstname("Иван").lastname("Петров").build();
        HttpEntity<EmployeeDTO> request1 = new HttpEntity<>(employeeDTO, headers);
        ResponseEntity<EmployeeOutDTO> response2 = restTemplate.withBasicAuth("user3", "user3").
                exchange("/api/empl/" + user2Id, HttpMethod.PUT, request1, EmployeeOutDTO.class);
        assert response2.getStatusCode().equals(HttpStatus.OK);

        // тест-кейс 5
        request = new HttpEntity<>(employeeDTO, headers);
        ResponseEntity<?> response3 = restTemplate.withBasicAuth("user3", "user3").
                exchange("/api/empl/"+user2Id, HttpMethod.PUT, request, Void.class);
        assert response3.getStatusCode().equals(HttpStatus.BAD_REQUEST);


    }


    @Test
    @Order(3)
    public void testCEmployeeGet() throws Exception {
        //create user
        UserDTO userDTO = new UserDTO("user4", "user4");
        HttpEntity<UserDTO> entity = new HttpEntity<>(userDTO, headers);
        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/auth/signup", HttpMethod.POST,
                entity, Void.class);

        assert response.getStatusCode().equals(HttpStatus.OK);


        //create employee
        String account = "123";
        EmployeeDTO employeeDTO = EmployeeDTO.builder().firstname("Иван").lastname("Сидоров").account(account).build();
        HttpEntity<EmployeeDTO> request = new HttpEntity<>(employeeDTO, headers);
        ResponseEntity<UserOutDTO> response1 = restTemplate.withBasicAuth("user4", "user4").
                exchange("/api/empl/create", HttpMethod.POST, request, UserOutDTO.class);
        assert response1.getStatusCode().equals(HttpStatus.CREATED);
        UUID user2Id = response1.getBody().getId();

        //update employee
        // тест-кейс 6
        HttpEntity<Void> request1 = new HttpEntity<>( headers);
        ResponseEntity<EmployeeOutDTO> response2 = restTemplate.withBasicAuth("user4", "user4").
                exchange("/api/empl?id=" + user2Id, HttpMethod.GET, request1, EmployeeOutDTO.class);
        assert response2.getStatusCode().equals(HttpStatus.OK);
        assert response2.getBody().getLastname().equals("Сидоров");

        // тест-кейс 7
        HttpEntity<Void> request2 = new HttpEntity<>( headers);
        ResponseEntity<EmployeeOutDTO> response3 = restTemplate.withBasicAuth("user4", "user4").
                exchange("/api/empl?account=" + account, HttpMethod.GET, request1, EmployeeOutDTO.class);
        assert response3.getStatusCode().equals(HttpStatus.OK);
        assert response3.getBody().getLastname().equals("Сидоров");


    }


    @Test
    @Order(4)
    public void testDEmployeeSearch() throws Exception {

        // тест-кейс 8
        String account = "123";
        EmployeeDTO employeeDTO = EmployeeDTO.builder().firstname("Иван").lastname("Сидоров").account(account).build();
        HttpEntity<EmployeeDTO> request = new HttpEntity<>(employeeDTO, headers);
        ResponseEntity<List<EmployeeOutDTO>> response1 = restTemplate.withBasicAuth("user4", "user4").
                exchange("/api/empl/search", HttpMethod.POST, request, new ParameterizedTypeReference<List<EmployeeOutDTO>>() {
                });
        assert response1.getStatusCode().equals(HttpStatus.OK);
        assert response1.getBody().size() == 1;
        assert response1.getBody().get(0).getAccount().equals("123");

        // тест-кейс 9
        EmployeeDTO employeeDTO1 = EmployeeDTO.builder().firstname("Иван").build();
        HttpEntity<EmployeeDTO> request1 = new HttpEntity<>(employeeDTO1, headers);
        ResponseEntity<List<EmployeeOutDTO>> response2 = restTemplate.withBasicAuth("user4", "user4").
                exchange("/api/empl/search", HttpMethod.POST, request1, new ParameterizedTypeReference<List<EmployeeOutDTO>>() {
                });
        assert response2.getStatusCode().equals(HttpStatus.OK);
        assert response2.getBody().size() == 3;
    }


    @Test
    @Order(5)
    public void testEmployeeDelete() throws Exception {
        //create user
        UserDTO userDTO = new UserDTO("user5", "user5");
        HttpEntity<UserDTO> entity = new HttpEntity<>(userDTO, headers);
        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/auth/signup", HttpMethod.POST,
                entity, Void.class);

        assert response.getStatusCode().equals(HttpStatus.OK);


        //create employee
        String account = "1234";
        EmployeeDTO employeeDTO = EmployeeDTO.builder().firstname("Иван").lastname("Сидоров").account(account).build();
        HttpEntity<EmployeeDTO> request = new HttpEntity<>(employeeDTO, headers);
        ResponseEntity<UserOutDTO> response1 = restTemplate.withBasicAuth("user5", "user5").
                exchange("/api/empl/create", HttpMethod.POST, request, UserOutDTO.class);
        assert response1.getStatusCode().equals(HttpStatus.CREATED);
        UUID user2Id = response1.getBody().getId();

        // тест-кейс 10
        HttpEntity<Void> request1 = new HttpEntity<>(headers);
        ResponseEntity<Void> response2 = restTemplate.withBasicAuth("user1", "user1").
                exchange("/api/empl/"+ user2Id, HttpMethod.DELETE, request1, new ParameterizedTypeReference<Void>() {
                });
        assert response2.getStatusCode().equals(HttpStatus.OK);
    }

    public static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + database.getJdbcUrl(),
                    "spring.datasource.username=" + database.getUsername(),
                    "spring.datasource.password=" + database.getPassword()
            );
        }
    }
}

