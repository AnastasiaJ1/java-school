package com.digdes.school.project.unit.services;

import com.digdes.school.project.enums.EmployeeStatus;
import com.digdes.school.project.impl.EmployeeServiceImpl;
import com.digdes.school.project.input.EmployeeDTO;
import com.digdes.school.project.mappers.EmployeeMapper;
import com.digdes.school.project.model.Employee;
import com.digdes.school.project.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class TestEmployeeService {
    @Mock
    EmployeeRepository repository;

    EmployeeMapper mapper = new EmployeeMapper();
    EmployeeServiceImpl employeeService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeService = new EmployeeServiceImpl(mapper, repository);
    }

    @Test
    public void testCreate(){
        UUID id = UUID.randomUUID();

        EmployeeDTO employeeDTO = EmployeeDTO.builder().build();
        when(repository.existsById(id)).thenReturn(true);
        assert employeeService.create(employeeDTO, id) == null;

        when(repository.existsById(id)).thenReturn(false);
        assert employeeService.create(employeeDTO, id) == null;

        employeeDTO.setFirstname("Иван");
        employeeDTO.setLastname("Иванов");
        Employee employee = mapper.convertToEntity(employeeDTO);
        employee.setId(id);
        employee.setStatus(EmployeeStatus.ACTIVE);
        when(repository.save(employee)).thenReturn(employee);
        assert employeeService.create(employeeDTO, id).getFirstname().equals("Иван");
    }

    @Test
    public void testUpdate(){
        UUID id = UUID.randomUUID();

        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .account("qawse").lastname("Иванов1")
                .firstname("Иван1").surname("Иванович")
                .email("qawse@gmail.com").jobTitle("Разработчик").build();
        Employee employeePrev = Employee.builder()
                .id(id).lastname("Иванов").firstname("Иван").status(EmployeeStatus.ACTIVE).build();
        Employee employeeNew = Employee.builder()
                .id(id).status(EmployeeStatus.ACTIVE)
                .account("qawse").lastname("Иванов1")
                .firstname("Иван1").surname("Иванович")
                .email("qawse@gmail.com").jobTitle("Разработчик").build();
        Optional<Employee> employeeOptional = Optional.of(employeePrev);
        when(repository.findById(id)).thenReturn(employeeOptional);
        when(repository.save(employeeNew)).thenReturn(employeeNew);
        assert employeeService.update(employeeDTO, id);
        Mockito.verify(repository).save(employeeNew);
        Mockito.verify(repository, times(1)).save(employeeNew);

        employeeDTO = EmployeeDTO.builder()
                .lastname("Иванов")
                .firstname("Иван").build();
        employeePrev = Employee.builder()
                .id(id).lastname("Иванов").firstname("Иван").status(EmployeeStatus.ACTIVE).build();
        employeeNew = Employee.builder()
                .id(id).status(EmployeeStatus.ACTIVE)
                .lastname("Иванов")
                .firstname("Иван").build();
        employeeOptional = Optional.of(employeePrev);
        when(repository.findById(id)).thenReturn(employeeOptional);
        assert !employeeService.update(employeeDTO, id);
        Mockito.verify(repository, times(0)).save(employeeNew);

        employeeDTO = EmployeeDTO.builder()
                .lastname("Иванов")
                .firstname("Иван").build();
        employeeNew = Employee.builder()
                .id(id).status(EmployeeStatus.ACTIVE)
                .lastname("Иванов")
                .firstname("Иван").build();
        employeeOptional = Optional.empty();
        when(repository.findById(id)).thenReturn(employeeOptional);
        assert !employeeService.update(employeeDTO, id);
        Mockito.verify(repository, times(0)).save(employeeNew);

    }

    @Test
    public void testDelete(){
        UUID id = UUID.randomUUID();
        Employee employee = Employee.builder()
                .id(id).status(EmployeeStatus.ACTIVE).build();
        Optional<Employee> employeeOptional = Optional.of(employee);
        when(repository.findById(id)).thenReturn(employeeOptional);
        Employee employee1 = Employee.builder()
                .id(id).status(EmployeeStatus.DELETED).build();
        when(repository.save(employee1)).thenReturn(employee1);
        assert employeeService.delete(id);

        employeeOptional = Optional.empty();
        when(repository.findById(id)).thenReturn(employeeOptional);
        assert !employeeService.delete(id);

        employeeOptional = Optional.of(employee);
        when(repository.findById(id)).thenReturn(employeeOptional);
        assert !employeeService.delete(id);
    }

    @Test
    public void testGetOutDTO(){
        UUID id = UUID.randomUUID();
        Employee employee = Employee.builder()
                .id(id).status(EmployeeStatus.ACTIVE).build();
        Optional<Employee> employeeOptional = Optional.of(employee);
        when(repository.findById(id)).thenReturn(employeeOptional);
        assert employeeService.getOutDTO(id).equals(mapper.convertToDTO(employee));

        employeeOptional = Optional.empty();
        when(repository.findById(id)).thenReturn(employeeOptional);
        assert employeeService.getOutDTO(id) == null;
    }
}
