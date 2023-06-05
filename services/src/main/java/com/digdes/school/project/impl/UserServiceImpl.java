package com.digdes.school.project.impl;

import com.digdes.school.project.UserService;
import com.digdes.school.project.enums.UserRole;
import com.digdes.school.project.input.UserDTO;
import com.digdes.school.project.mappers.UserMapper;
import com.digdes.school.project.model.User;
import com.digdes.school.project.repositories.EmployeeRepository;
import com.digdes.school.project.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final EmployeeRepository employeeRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository repository, EmployeeRepository employeeRepository, UserMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public boolean save(UserDTO userDTO) {
        logger.info("saving user " + userDTO.getUsername());
        if (repository.findByUsername(userDTO.getUsername()).isPresent()) {
            logger.debug("user already exist");
            return false;
        }
        User user = mapper.convertToEntity(userDTO);
        if (repository.count() == 0){
            user.setRole(UserRole.ADMINISTRATOR);
            logger.debug("administrator created");
        } else {
            logger.debug("user created");
            user.setRole(UserRole.USER);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return true;
    }
}
