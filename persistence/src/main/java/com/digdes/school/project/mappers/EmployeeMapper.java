package com.digdes.school.project.mappers;

import com.digdes.school.project.input.EmployeeDTO;
import com.digdes.school.project.model.Employee;
import com.digdes.school.project.model.enums.EmployeeStatus;
import com.digdes.school.project.output.EmployeeOutDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EmployeeMapper {
    private final ModelMapper modelMapper;

    public EmployeeMapper() {
        this.modelMapper = new ModelMapper();
    }
    public Employee convertToEntity(EmployeeDTO employeeDTO) {
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        employee.setStatus(EmployeeStatus.ACTIVE);
        employee.setId(UUID.randomUUID());
        return employee;
    }

    public EmployeeOutDTO convertToDTO(Employee employee) {
        return modelMapper.map(employee, EmployeeOutDTO.class);
    }
}
