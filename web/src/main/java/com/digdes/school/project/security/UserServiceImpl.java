package com.digdes.school.project.security;

import com.digdes.school.project.input.UserDTO;
import com.digdes.school.project.mappers.UserMapper;
import com.digdes.school.project.model.Employee;
import com.digdes.school.project.model.User;
import com.digdes.school.project.enums.EmployeeStatus;
import com.digdes.school.project.repositories.EmployeeRepository;
import com.digdes.school.project.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository repository;
    private final EmployeeRepository employeeRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, EmployeeRepository employeeRepository, UserMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username).get();
        Employee employee = null;
        if(user != null) employee = employeeRepository.findById(user.getId()).orElse(null);
        boolean active = employee == null || employee.getStatus() == EmployeeStatus.ACTIVE;
        System.out.println(active);
        return new UserDetailsImpl(user, active);
    }

    public boolean save(UserDTO userDTO) {
        if (repository.findByUsername(userDTO.getUsername()).isPresent()) return false;
        User user = mapper.convertToEntity(userDTO);
        if (repository.count() == 0){
            user.setRole("ADMINISTRATOR");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return true;
    }


}
