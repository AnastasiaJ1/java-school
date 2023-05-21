package com.digdes.school.project.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class EmployeeOutDTO {
    private UUID id;
    private String lastname;
    private String firstname;
    private String surname;
    private String jobTitle;
    private String account;
    private String email;
    private String status;
}
