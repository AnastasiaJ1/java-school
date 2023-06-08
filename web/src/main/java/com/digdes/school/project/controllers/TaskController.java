package com.digdes.school.project.controllers;

import com.digdes.school.project.amqp.MessageProducer;
import com.digdes.school.project.filters.TaskSearchFilter;
import com.digdes.school.project.impl.EmailServiceImpl;
import com.digdes.school.project.impl.TaskServiceImpl;
import com.digdes.school.project.input.TaskDTO;
import com.digdes.school.project.enums.TaskStatus;
import com.digdes.school.project.model.EmailContext;
import com.digdes.school.project.output.TaskIdOutDTO;
import com.digdes.school.project.output.TaskOutDTO;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class TaskController {
    private final TaskServiceImpl taskService;
    private final AuthorizationService authorizationService;
    private final EmailServiceImpl emailService;
    private final MessageProducer messageProducer;

    public TaskController(TaskServiceImpl taskService, AuthorizationService authorizationService, EmailServiceImpl emailService, MessageProducer messageProducer) {
        this.taskService = taskService;
        this.authorizationService = authorizationService;
        this.emailService = emailService;
        this.messageProducer = messageProducer;
    }

    @GetMapping(value = "/simple-email")
    public ResponseEntity<?> sendSimpleEmail(@RequestParam(required = false) String email) {

        try {
            String emailAddr = "masha.ivanova.1999@yandex.ru";
            if(email != null) emailAddr = email;
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("name", "Иван");
            context.put("taskName", "Задача");
            EmailContext emailContext = EmailContext.builder().email(emailAddr).templateLocation("email")
                    .to(emailAddr).subject("S").from("dolgaya1999a@gmail.com" +
                            "")
                    .context(context).build();
//            emailService.sendMail(emailContext);
            messageProducer.sendMessage(emailContext);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            e.printStackTrace();
            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Please check your inbox", HttpStatus.OK);
    }
    @Operation(summary = "Создание задачи")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача создана"),
            @ApiResponse(responseCode = "400", description = "Не все обязательные поля заполнены")})
    @PostMapping(value = "/api/project/{id}/task/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskIdOutDTO> create(@RequestBody TaskDTO taskDTO, @PathVariable UUID id) {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!authorizationService.taskCreateAccess(id, userDetails)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        TaskIdOutDTO taskId = taskService.create(taskDTO, id);
        if(id != null) return ResponseEntity.status(HttpStatus.CREATED).body(taskId);
        return ResponseEntity.badRequest().build();
    }
    @Operation(summary = "Изменение задачи")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача обновлена"),
            @ApiResponse(responseCode = "403", description = "Нет прав на обновление задачи"),
            @ApiResponse(responseCode = "400", description = "Задача не найдена или поля не заполнены")})
    @PutMapping(value = "/api/task/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody TaskDTO taskDTO, @PathVariable UUID id) {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!authorizationService.taskUpdateAccess(id, userDetails)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();


        if(taskService.update(taskDTO,userDetails.getId(), id)) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Поиск задач")
    @PostMapping(value = "/api/task/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskOutDTO>> search(@RequestBody TaskSearchFilter taskSearchFilter) {
        return ResponseEntity.ok().body(taskService.search(taskSearchFilter));
    }

    @Operation(summary = "Изменение статуса задачи")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Неверные параметры запроса"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")})
    @PutMapping("/api/task/status/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable UUID id, @RequestParam TaskStatus status) {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!authorizationService.taskUpdateAccess(id, userDetails)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        int res = taskService.updateStatus(id, status);
        if(res == 2) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else if(res == 1) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.ok().build();
    }
}
