package com.digdes.school.project.impl;

import com.digdes.school.project.EmployeeService;
import com.digdes.school.project.filters.EmployeeSearchFilter;
import com.digdes.school.project.input.EmployeeDTO;
import com.digdes.school.project.mappers.EmployeeMapper;
import com.digdes.school.project.model.Employee;
import com.digdes.school.project.model.enums.EmployeeStatus;
import com.digdes.school.project.output.EmployeeOutDTO;
import com.digdes.school.project.repositories.EmployeeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeMapper mapper;
    private final EmployeeRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public EmployeeServiceImpl(EmployeeMapper mapper, EmployeeRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Employee save(EmployeeDTO employeeDTO) {
        return repository.save(mapper.convertToEntity(employeeDTO));

    }

    @Override
    public boolean change(Employee employee) {
        Employee employeePrev = repository.getById(employee.getId());

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
        if (employee.getStatus() != null
                && !employee.getStatus().equals(employeePrev.getStatus())) {
            employeePrev.setStatus(employee.getStatus());
            changeFlag++;
        }
        if (changeFlag > 0) repository.save(employeePrev);
        return changeFlag > 0;
    }

    @Override
    public boolean delete(UUID id) {
        Employee employee = repository.getById(id);
        if (employee != null && employee.getStatus() == EmployeeStatus.ACTIVE) {
            employee.setStatus(EmployeeStatus.DELETED);
            repository.save(employee);
            return true;
        }
        return false;
    }


    @Override
    public List<Employee> search(EmployeeSearchFilter employeeSearchFilter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
        Root<Employee> r = query.from(Employee.class);
        List<Predicate> predicate = new ArrayList<>();

        if (employeeSearchFilter.getLastname() != null) {
            Predicate predicateCode = builder.equal(r.get("lastname"), employeeSearchFilter.getLastname());
            predicate.add(predicateCode);
        }
        if (employeeSearchFilter.getFirstname() != null) {
            Predicate predicateCode = builder.equal(r.get("firstname"), employeeSearchFilter.getFirstname());
            predicate.add(predicateCode);
        }
        if (employeeSearchFilter.getSurname() != null) {
            Predicate predicateCode = builder.equal(r.get("surname"), employeeSearchFilter.getSurname());
            predicate.add(predicateCode);
        }
        if (employeeSearchFilter.getJobTitle() != null) {
            Predicate predicateCode = builder.equal(r.get("job_title"), employeeSearchFilter.getJobTitle());
            predicate.add(predicateCode);
        }
        if (employeeSearchFilter.getAccount() != null) {
            Predicate predicateCode = builder.equal(r.get("account"), employeeSearchFilter.getAccount());
            predicate.add(predicateCode);
        }
        if (employeeSearchFilter.getEmail() != null) {
            Predicate predicateCode = builder.equal(r.get("email"), employeeSearchFilter.getEmail());
            predicate.add(predicateCode);
        }
        Predicate predicateCode = builder.equal(r.get("status"), EmployeeStatus.ACTIVE);
        predicate.add(predicateCode);


        Predicate And = builder.and(predicate.toArray(new Predicate[predicate.size()]));


        query.where(And);
        return entityManager.createQuery(query).getResultList();

    }

    @Override
    public EmployeeOutDTO getOutDTO(UUID id) {
        if (repository.existsById(id)) {
            return mapper.convertToDTO(repository.getById(id));
        }
        return null;
    }

    @Override
    public Employee get(UUID id) {
        return repository.getById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
