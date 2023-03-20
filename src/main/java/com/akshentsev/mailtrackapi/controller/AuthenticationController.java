package com.akshentsev.mailtrackapi.controller;

import com.akshentsev.mailtrackapi.dto.AuthenticationRequest;
import com.akshentsev.mailtrackapi.dto.AuthenticationResponse;
import com.akshentsev.mailtrackapi.dto.RegisterRequest;
import com.akshentsev.mailtrackapi.service.security.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Spring Security контроллер", description = "Создан для ограничения доступа к API")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    @Operation(
            summary = "Регистрация пользователя",
            description = "Позволяет зарегистрировать нового пользователя"
    )
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    @Operation(
            summary = "Аутентификация пользователя",
            description = "Позволяет аутентифицировать существующего пользователя"
    )
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}