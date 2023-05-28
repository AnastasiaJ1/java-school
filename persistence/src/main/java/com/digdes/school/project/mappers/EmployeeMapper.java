package com.digdes.school.project.mappers;

import com.digdes.school.project.input.EmployeeDTO;
import com.digdes.school.project.model.Employee;
import com.digdes.school.project.output.EmployeeOutDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class EmployeeMapper {
    private final ModelMapper modelMapper;

    public EmployeeMapper() {
        this.modelMapper = new ModelMapper();
    }
    public Employee convertToEntity(EmployeeDTO employeeDTO) {
        return modelMapper.map(employeeDTO, Employee.class);
    }

    public EmployeeOutDTO convertToDTO(Employee employee) {
        return modelMapper.map(employee, EmployeeOutDTO.class);
    }
}
