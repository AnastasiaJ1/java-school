package com.digdes.school.project;

import com.digdes.school.project.filters.EmployeeSearchFilter;
import com.digdes.school.project.input.EmployeeDTO;
import com.digdes.school.project.model.Employee;
import com.digdes.school.project.output.EmployeeOutDTO;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    Employee save(EmployeeDTO employeeDTO);

    boolean change(Employee employee);

    boolean delete(UUID id);

    List<Employee> search(EmployeeSearchFilter employeeSearchFilter);

    EmployeeOutDTO getOutDTO(UUID id);

    Employee get(UUID id);
}
