package com.digdes.school.project.impl;

import com.digdes.school.project.EmployeeService;
import com.digdes.school.project.input.EmployeeDTO;
import com.digdes.school.project.mappers.EmployeeMapper;
import com.digdes.school.project.model.Employee;
import com.digdes.school.project.enums.EmployeeStatus;
import com.digdes.school.project.output.EmployeeOutDTO;
import com.digdes.school.project.repositories.EmployeeRepository;
import com.digdes.school.project.specifications.EmployeeSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeMapper mapper;
    private final EmployeeRepository repository;


    public EmployeeServiceImpl(EmployeeMapper mapper, EmployeeRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Employee create(EmployeeDTO employeeDTO, UUID id) {
        if(repository.existsById(id)) return null;
        Employee employee = mapper.convertToEntity(employeeDTO);
        employee.setStatus(EmployeeStatus.ACTIVE);
        employee.setId(id);
        if(employee.getLastname() != null && employee.getFirstname() != null
                && (employee.getAccount() == null || repository.findByAccount(employee.getAccount()).isEmpty()))
            return repository.save(employee);
        return null;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean update(EmployeeDTO employee, UUID id) {
        Employee employeePrev = repository.findById(id).orElse(null);

        if (employeePrev == null || employeePrev.getStatus().equals(EmployeeStatus.DELETED))
            return false;
        int changeFlag = 0;
        if (employee.getLastname() != null
                && !employee.getLastname().equals(employeePrev.getLastname())) {
            employeePrev.setLastname(employee.getLastname());
            changeFlag++;
        }
        if (employee.getFirstname() != null
                && !employee.getFirstname().equals(employeePrev.getFirstname())) {
            employeePrev.setFirstname(employee.getFirstname());
            changeFlag++;
        }
        if (employee.getSurname() != null
                && !employee.getSurname().equals(employeePrev.getSurname())) {
            employeePrev.setSurname(employee.getSurname());
            changeFlag++;
        }
        if (employee.getJobTitle() != null
                && !employee.getJobTitle().equals(employeePrev.getJobTitle())) {
            employeePrev.setJobTitle(employee.getJobTitle());
            changeFlag++;
        }
        if (employee.getAccount() != null
                && !employee.getAccount().equals(employeePrev.getAccount())) {
            employeePrev.setAccount(employee.getAccount());
            changeFlag++;
        }
        if (employee.getEmail() != null
                && !employee.getEmail().equals(employeePrev.getEmail())) {
            employeePrev.setEmail(employee.getEmail());
            changeFlag++;
        }
        if (changeFlag > 0) repository.save(employeePrev);
        return changeFlag > 0;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean delete(UUID id) {
        Employee employee = repository.findById(id).orElse(null);
        if (employee != null && employee.getStatus() == EmployeeStatus.ACTIVE) {
            employee.setStatus(EmployeeStatus.DELETED);
            repository.save(employee);
            return true;
        }
        return false;
    }

    @Override
    public List<Employee> search(EmployeeDTO employeeDTO) {
        return repository.findAll(EmployeeSpecification.getSpec(employeeDTO));
    }

    @Override
    public EmployeeOutDTO getOutDTO(UUID id) {
        Employee employee = repository.findById(id).orElse(null);
        if (employee != null) {
            return mapper.convertToDTO(employee);
        }
        return null;
    }

    @Override
    public Employee get(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public EmployeeOutDTO getByAccount(String account) {
        return mapper.convertToDTO(repository.findByAccount(account).orElse(null));
    }
}
