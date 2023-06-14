package com.digdes.school.project.security;

import com.digdes.school.project.model.Employee;
import com.digdes.school.project.model.User;
import com.digdes.school.project.enums.EmployeeStatus;
import com.digdes.school.project.repositories.EmployeeRepository;
import com.digdes.school.project.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;
    private final EmployeeRepository employeeRepository;

    public UserDetailsServiceImpl(UserRepository repository, EmployeeRepository employeeRepository) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        User user = null;
        Optional<User> userOptional = repository.findByUsername(username);
        Employee employee = null;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            Optional<Employee> employeeOptional = employeeRepository.findById(userOptional.get().getId());
            if(employeeOptional.isPresent()) employee = employeeOptional.get();
        }
        return new UserDetailsImpl(user, employee == null || employee.getStatus() == EmployeeStatus.ACTIVE);
    }




}
