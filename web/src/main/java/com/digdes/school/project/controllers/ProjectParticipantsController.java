package com.digdes.school.project.controllers;

import com.digdes.school.project.ProjectParticipantsService;
import com.digdes.school.project.input.ProjectParticipantsDTO;
import com.digdes.school.project.model.id.ProjectParticipantsId;
import com.digdes.school.project.output.EmployeeOutDTO;
import com.digdes.school.project.output.ProjectParticipantsOutDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/project")
public class ProjectParticipantsController {
    private final ProjectParticipantsService projectParticipantsService;

    public ProjectParticipantsController(ProjectParticipantsService projectParticipantsService) {
        this.projectParticipantsService = projectParticipantsService;
    }

    @Operation(summary = "Добавление сотрудника в команду проекта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Неверные параметры")})
    @PostMapping(value = "/team/add/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody ProjectParticipantsDTO projectParticipantsDTO, @PathVariable UUID id) {
        if(projectParticipantsService.create(projectParticipantsDTO, id) != null) return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Удаление сотрудника из команды проекта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "404", description = "Проект или сотрудник не найдены")})
    @DeleteMapping("/team/{id1}/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @PathVariable UUID id1) {
        if(projectParticipantsService.delete(new ProjectParticipantsId(id1, id))) return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Получение команды проекта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "404", description = "Проект не найден")})
    @GetMapping(value = "/team/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProjectParticipantsOutDTO>> getTeam(@PathVariable UUID id) {
        List<ProjectParticipantsOutDTO> list = projectParticipantsService.getAllProjectParticipants(id);
        if(list == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
}
