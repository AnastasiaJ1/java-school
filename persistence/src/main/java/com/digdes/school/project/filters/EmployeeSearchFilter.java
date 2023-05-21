package com.digdes.school.project.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeSearchFilter {
    private String firstname;
    private String lastname;
    private String surname;
    private String jobTitle;
    private String account;
    private String email;
    private String status;
}
