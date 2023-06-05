package com.digdes.school.project.controllers;

import com.digdes.school.project.filters.ProjectSearchFilter;
import com.digdes.school.project.impl.ProjectServiceImpl;
import com.digdes.school.project.input.ProjectDTO;
import com.digdes.school.project.enums.ProjectStatus;
import com.digdes.school.project.output.ProjectIdOutDTO;
import com.digdes.school.project.output.ProjectOutDTO;
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
@RequestMapping(value = "/api/project")
public class ProjectController {
    private final ProjectServiceImpl projectService;

    public ProjectController(ProjectServiceImpl projectService) {
        this.projectService = projectService;
    }
    @Operation(summary = "Создание проекта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Проект создан"),
            @ApiResponse(responseCode = "400", description = "Не все обязательные поля заполнены")})
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectIdOutDTO> create(@RequestBody ProjectDTO projectDTO) {
        ProjectIdOutDTO id = projectService.create(projectDTO);
        if(id != null) return ResponseEntity.status(HttpStatus.CREATED).body(id);
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Изменение проекта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Проект обновлен"),
            @ApiResponse(responseCode = "400", description = "Проект не найден или поля не заполнены")})
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody ProjectDTO projectDTO, @PathVariable UUID id) {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(projectService.update(projectDTO, userDetails.getId())) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Поиск проектов")
    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProjectOutDTO>> search(@RequestBody ProjectSearchFilter projectSearchFilter) {
        return ResponseEntity.ok().body(projectService.search(projectSearchFilter));
    }

    @Operation(summary = "Изменение статуса проекта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Неверные параметры запроса"),
            @ApiResponse(responseCode = "404", description = "Проект не найден")})
    @PutMapping(value = "/status/{id}")
    public ResponseEntity<Void> status(@PathVariable UUID id, @RequestParam ProjectStatus status) {
        int res = projectService.updateStatus(id, status);
        if(res == 2) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else if(res == 1) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.ok().build();
    }
}
