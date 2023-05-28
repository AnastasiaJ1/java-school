package com.digdes.school.project.mappers;

import com.digdes.school.project.input.UserDTO;
import com.digdes.school.project.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper() {
        this.modelMapper = new ModelMapper();
    }
    public User convertToEntity(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setId(UUID.randomUUID());
        user.setRole("USER");
        return user;
    }


}
