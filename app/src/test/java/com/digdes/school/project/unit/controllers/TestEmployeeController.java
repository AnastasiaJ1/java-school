package com.digdes.school.project.unit.controllers;

import com.digdes.school.project.controllers.EmployeeController;
import com.digdes.school.project.enums.UserRole;
import com.digdes.school.project.impl.EmployeeServiceImpl;
import com.digdes.school.project.input.EmployeeDTO;
import com.digdes.school.project.model.Employee;
import com.digdes.school.project.model.User;
import com.digdes.school.project.output.EmployeeOutDTO;
import com.digdes.school.project.security.AuthorizationService;
import com.digdes.school.project.security.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = EmployeeController.class,excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class TestEmployeeController {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeServiceImpl employeeService;

    @MockBean
    private AuthorizationService authorizationService;





    @Test
    public void testCreate() throws Exception {

        EmployeeDTO employeeDTO = new EmployeeDTO();
        UUID id = UUID.randomUUID();
        UserDetailsImpl userDetails = new UserDetailsImpl(new User(id, "name", "password", UserRole.USER), true);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);


        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);

        Employee employee = Employee.builder().lastname("ad").firstname("sv").build();
        when(employeeService.create(employeeDTO, userDetails.getId())).thenReturn(employee);
        mockMvc.perform(
                        post("/api/empl/create")
                                .content(objectMapper.writeValueAsString(employeeDTO))
                                .contentType(MediaType.APPLICATION_JSON).with(user(userDetails)))
                .andExpect(status().isCreated()
                );

        when(employeeService.create(employeeDTO, userDetails.getId())).thenReturn(null);
        mockMvc.perform(
                        post("/api/empl/create")
                                .content(objectMapper.writeValueAsString(employeeDTO))
                                .contentType(MediaType.APPLICATION_JSON).with(user(userDetails)))
                .andExpect(status().isBadRequest()
                );


    }
    @Test
    public void testUpdate() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        UUID id = UUID.randomUUID();
        UserDetailsImpl userDetails = new UserDetailsImpl(new User(id, "name", "password", UserRole.USER), true);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authorizationService.employeeUpdateAccess(id, userDetails)).thenReturn(false);
        when(employeeService.update(employeeDTO, userDetails.getId())).thenReturn(true);
        mockMvc.perform(
                        put("/api/empl/"+id)
                                .content(objectMapper.writeValueAsString(employeeDTO))
                                .contentType(MediaType.APPLICATION_JSON).with(user(userDetails)))
                .andExpect(status().isForbidden()
                );

        when(authorizationService.employeeUpdateAccess(id, userDetails)).thenReturn(true);
        when(employeeService.update(employeeDTO, userDetails.getId())).thenReturn(false);
        mockMvc.perform(
                        put("/api/empl/"+id)
                                .content(objectMapper.writeValueAsString(employeeDTO))
                                .contentType(MediaType.APPLICATION_JSON).with(user(userDetails)))
                .andExpect(status().isBadRequest()
                );
        when(authorizationService.employeeUpdateAccess(id, userDetails)).thenReturn(true);
        when(employeeService.update(employeeDTO, userDetails.getId())).thenReturn(true);
        mockMvc.perform(
                        put("/api/empl/"+id)
                                .content(objectMapper.writeValueAsString(employeeDTO))
                                .contentType(MediaType.APPLICATION_JSON).with(user(userDetails)))
                .andExpect(status().isOk()
                );
    }

    @Test
    public void testDelete() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        UUID id = UUID.randomUUID();
        UserDetailsImpl userDetails = new UserDetailsImpl(new User(id, "name", "password", UserRole.ADMINISTRATOR), true);

        when(employeeService.delete(id)).thenReturn(false);
        mockMvc.perform(
                        delete("/api/empl/"+id)
                                .content(objectMapper.writeValueAsString(employeeDTO))
                                .contentType(MediaType.APPLICATION_JSON).with(user(userDetails)))
                .andExpect(status().isNotFound()
                );

        when(employeeService.delete(id)).thenReturn(true);
        mockMvc.perform(
                        delete("/api/empl/"+id)
                                .content(objectMapper.writeValueAsString(employeeDTO))
                                .contentType(MediaType.APPLICATION_JSON).with(user(userDetails)))
                .andExpect(status().isOk()
                );

    }

    @Test
    public void testSearch() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        UUID id = UUID.randomUUID();
        UserDetailsImpl userDetails = new UserDetailsImpl(new User(id, "name", "password", UserRole.ADMINISTRATOR), true);


        when(employeeService.search(employeeDTO)).thenReturn(new ArrayList<>());
        mockMvc.perform(
                        post("/api/empl/search")
                                .content(objectMapper.writeValueAsString(employeeDTO))
                                .contentType(MediaType.APPLICATION_JSON).with(user(userDetails)))
                .andExpect(status().isOk()
                ).andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void testGet() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        UUID id = UUID.randomUUID();
        UserDetailsImpl userDetails = new UserDetailsImpl(new User(id, "name", "password", UserRole.ADMINISTRATOR), true);

        String account  = "1";
        when(employeeService.getOutDTO(id)).thenReturn(new EmployeeOutDTO());
        when(employeeService.getByAccount(account)).thenReturn(new EmployeeOutDTO());
        mockMvc.perform(
                        get("/api/empl").with(user(userDetails)))
                .andExpect(status().isBadRequest()
                );
        mockMvc.perform(
                        get("/api/empl")
                                .param("id", String.valueOf(id)).with(user(userDetails)))
                .andExpect(status().isOk()
                ).andExpect(content().contentType(MediaType.APPLICATION_JSON));
        mockMvc.perform(
                        get("/api/empl")
                                .param("account", account).with(user(userDetails)))
                .andExpect(status().isOk()
                ).andExpect(content().contentType(MediaType.APPLICATION_JSON));
        when(employeeService.getByAccount(account)).thenReturn(null);
        mockMvc.perform(
                        get("/api/empl")
                                .param("account", account).with(user(userDetails)))
                .andExpect(status().isNotFound()
                );

    }
}
