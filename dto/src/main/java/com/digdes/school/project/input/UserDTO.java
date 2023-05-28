package com.digdes.school.project.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Schema(description = "Пользователь")
public class UserDTO {
    @Schema(required = true, description = "Имя пользователя")
    private String username;
    @Schema(required = true, description = "Пароль")
    private String password;
}
