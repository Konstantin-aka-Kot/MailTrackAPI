package com.akshentsev.mailtrackapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Size(min = 2, max = 50, message = "Имя должно содержать от 2 до 50 символов")
    private String firstname;
    @Size(min = 2, max = 50, message = "Фамилия должна содержать от 2 до 50 символов")
    private String lastname;
    @Pattern(regexp = "^((8|\\+374|\\+994|\\+995|\\+375|\\+7|\\+380|\\+38|\\+996|\\+998|\\+993)[\\- ]?)?\\(?\\d{3,5}\\)?[\\- ]?\\d{1}[\\- ]?\\d{1}[\\- ]?\\d{1}[\\- ]?\\d{1}[\\- ]?\\d{1}(([\\- ]?\\d{1})?[\\- ]?\\d{1})?$",
            message = "Номер телефона введен неверно")
    private String phone;
    @NotEmpty(message = "Пароль не может быть пустым")
    private String password;
}