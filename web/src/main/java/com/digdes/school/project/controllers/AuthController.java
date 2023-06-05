package com.digdes.school.project.controllers;

import com.digdes.school.project.impl.UserServiceImpl;
import com.digdes.school.project.input.UserDTO;
import com.digdes.school.project.security.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final UserServiceImpl userService;

    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }
    @Operation(summary = "Регистрация")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Пользователь уже был зарегистрирован")})
    @PostMapping(value = "/api/auth/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody UserDTO userDTO) {
        if(!userService.save(userDTO)) return ResponseEntity.badRequest().build();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
