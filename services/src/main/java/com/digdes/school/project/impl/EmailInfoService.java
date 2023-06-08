package com.digdes.school.project.impl;

import com.digdes.school.project.model.Employee;
import com.digdes.school.project.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailInfoService {
    @Value("${spring.mail.username}")
    private String username;

    public String getUsername() {
        return username;
    }

    private final EmployeeRepository repository;


    public EmailInfoService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Employee getEmployee(UUID id){
        if(repository.existsById(id)){
            return repository.findById(id).get();
        }
        return null;
    }
}
