package com.digdes.school.project.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private String lastname;
    private String firstname;
    private String surname;
    private String jobTitle;
    private String account;

    private String email;


}
