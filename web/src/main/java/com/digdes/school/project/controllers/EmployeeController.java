package com.digdes.school.project.controllers;

import com.digdes.school.project.EmployeeService;
import com.digdes.school.project.impl.EmployeeServiceImpl;
import com.digdes.school.project.input.EmployeeDTO;
import com.digdes.school.project.model.Employee;
import com.digdes.school.project.output.EmployeeOutDTO;
import com.digdes.school.project.output.UserOutDTO;
import com.digdes.school.project.security.AuthorizationService;
import com.digdes.school.project.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/empl")
public class EmployeeController {
    private final EmployeeServiceImpl employeeService;
    private final AuthorizationService authorizationService;

    public EmployeeController(EmployeeServiceImpl employeeService, AuthorizationService authorizationService) {
        this.employeeService = employeeService;
        this.authorizationService = authorizationService;
    }
    @Operation(summary = "Создание профиля сотрудника")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Профиль создан"),
            @ApiResponse(responseCode = "400", description = "Профиль уже создан или не все обязательные поля заполнены")})
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserOutDTO> create(@RequestBody EmployeeDTO employeeDTO) {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(employeeService.create(employeeDTO, userDetails.getId()) != null) return ResponseEntity.status(HttpStatus.CREATED).body(new UserOutDTO(userDetails.getId()));
        return ResponseEntity.badRequest().build();
    }
    @Operation(summary = "Изменение сотрудника")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Профиль обновлен"),
            @ApiResponse(responseCode = "403", description = "Нет прав на обновление профиля сотрудника"),
            @ApiResponse(responseCode = "400", description = "Сотрудник не найден или поля не заполнены")})
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody EmployeeDTO employeeDTO, @PathVariable UUID id) {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!authorizationService.employeeUpdateAccess(id, userDetails)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        if(employeeService.update(employeeDTO, userDetails.getId())) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
    @Operation(summary = "Удаление сотрудника")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Профиль удален"),
            @ApiResponse(responseCode = "404", description = "Профиль не найден")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if(employeeService.delete(id)) return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @Operation(summary = "Поиск сотрудников")
    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeOutDTO>> search(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok().body(employeeService.search(employeeDTO));
    }
    @Operation(summary = "Получение карточки сотрудника")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Параметры запроса не заполнены"),
            @ApiResponse(responseCode = "404", description = "Сотрудник не найден")})
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeOutDTO> get(@RequestParam(value = "id", required=false) UUID id
            , @RequestParam(value = "account", required=false) String account) {
        System.out.println(id);
        EmployeeOutDTO employee = null;
        if((id == null && account == null)
                || (id != null && account != null)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if(id != null) employee = employeeService.getOutDTO(id);
        if(account != null) employee = employeeService.getByAccount(account);
        if(employee == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok().body(employee);
    }
}
