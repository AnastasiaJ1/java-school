package com.digdes.school.project.controllers;

import com.digdes.school.project.impl.UserServiceImpl;
import com.digdes.school.project.input.UserDTO;
import com.digdes.school.project.output.JwtResponse;
import com.digdes.school.project.security.JwtUtils;
import com.digdes.school.project.security.UserDetailsImpl;
import com.digdes.school.project.security.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthController {
    private final UserServiceImpl userService;

    @Autowired
    AuthenticationManager authenticationManager;





    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/api/auth/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody UserDTO userDTO) {
        System.out.println("xfe"+ userDTO);


        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
        System.out.println("xfe1"+ userDTO);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("xfe2"+ userDTO);
        String jwt = jwtUtils.generateJwtToken(authentication);
        System.out.println("xfe3"+ userDTO);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(new JwtResponse(jwt, userDetails.getUsername(),  roles, userDetails.getId()));
    }

    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }
    @Operation(summary = "Регистрация")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Пользователь уже был зарегистрирован")})
    @PostMapping(value = "/api/auth/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody UserDTO userDTO) {
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        if(!userService.save(userDTO)) return ResponseEntity.badRequest().build();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
