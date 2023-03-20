package com.akshentsev.mailtrackapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность запроса на аутентификацию")
public class AuthenticationRequest {
    @Schema(description = "Номер телефона, используется в качестве логина")
    @Pattern(regexp = "^((8|\\+374|\\+994|\\+995|\\+375|\\+7|\\+380|\\+38|\\+996|\\+998|\\+993)[\\- ]?)?\\(?\\d{3,5}\\)?[\\- ]?\\d{1}[\\- ]?\\d{1}[\\- ]?\\d{1}[\\- ]?\\d{1}[\\- ]?\\d{1}(([\\- ]?\\d{1})?[\\- ]?\\d{1})?$",
    message = "Номер телефона введен неверно")
    private String phone;
    @NotEmpty(message = "Пароль не может быть пустым")
    @NotBlank(message = "Паоль не может состоять из пробелов")
    private String password;
}
