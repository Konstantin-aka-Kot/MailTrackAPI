package com.akshentsev.mailtrackapi.controller;

import com.akshentsev.mailtrackapi.dto.MailCreateRequest;
import com.akshentsev.mailtrackapi.dto.MailCreateResponse;
import com.akshentsev.mailtrackapi.dto.MailUpdateRequest;
import com.akshentsev.mailtrackapi.dto.MailUpdateResponse;
import com.akshentsev.mailtrackapi.service.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mails")
@RequiredArgsConstructor
@Tag(name = "Почтовый контроллер", description = "Операции с почтовыми отправлениями")
public class MailController {
    private final MailService mailService;

    @PostMapping()
    @Operation(
            summary = "Регистрация почтовых отправлений",
            description = "Позволяет создать почтовое отправление"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<MailCreateResponse> registerMail(
            @RequestBody MailCreateRequest mailCreateRequest) {
        return ResponseEntity.ok(mailService.createMail(mailCreateRequest));
    }

    @PutMapping()
    @Operation(
            summary = "Обновление почтовых отправлений",
            description = "Позволяет проводить все операции с текущими почтовыми отправлениями"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<MailUpdateResponse> updateMail(@RequestBody MailUpdateRequest mailUpdateRequest) {
        return ResponseEntity.ok(mailService.updateMail(mailUpdateRequest));
    }
}
