package com.akshentsev.mailtrackapi.controller;

import com.akshentsev.mailtrackapi.dto.MailHistoryResponse;
import com.akshentsev.mailtrackapi.service.MailHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/track")
@RequiredArgsConstructor
@Tag(name = "Контроллер истории почтового отправления")
public class MailHistoryController {
    private final MailHistoryService mailHistoryService;
    @GetMapping("/{trackingCode}")
    @Operation(
            summary = "Просмотр всей истории почтового отправления",
            description = "Позволяет выгрузить всю историю почтового отправления по его трек-коду"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<MailHistoryResponse> getMail(
            @PathVariable
            @Parameter(description = "Трек-код")
            String trackingCode) {

        return ResponseEntity.ok(mailHistoryService.getHistory(trackingCode));
    }
}
