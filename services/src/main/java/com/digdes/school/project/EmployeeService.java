package com.digdes.school.project;

import com.digdes.school.project.filters.EmployeeSearchFilter;
import com.digdes.school.project.input.EmployeeDTO;
import com.digdes.school.project.model.Employee;
import com.digdes.school.project.output.EmployeeOutDTO;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    Employee create(EmployeeDTO employeeDTO, UUID id);

    boolean update(EmployeeDTO employeeDTO, UUID id);

    boolean delete(UUID id);

    List<EmployeeOutDTO> search(EmployeeDTO employeeDTO);

    EmployeeOutDTO getOutDTO(UUID id);

    Employee get(UUID id);
}
