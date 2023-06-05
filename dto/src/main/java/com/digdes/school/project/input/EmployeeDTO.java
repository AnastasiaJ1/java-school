package com.digdes.school.project.input;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
//@Jacksonized
@Schema(description = "Сотрудник")
public class EmployeeDTO {
    @Schema(required = true, description = "Фамилия")
    private String lastname;
    @Schema(required = true, description = "Имя")
    private String firstname;
    @Schema(required = false, description = "Отчество")
    private String surname;
    @Schema(required = false, description = "Должность")
    private String jobTitle;
    @Schema(required = false, description = "Учетная запись")
    private String account;
    @Schema(required = false, description = "Адрес электронной почты")
    private String email;


}
