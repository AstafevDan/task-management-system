package com.dan.taskmanagementsystem.http.controller.auth;

import com.dan.taskmanagementsystem.dto.auth.AuthenticationRequest;
import com.dan.taskmanagementsystem.dto.auth.AuthenticationResponse;
import com.dan.taskmanagementsystem.dto.auth.RegisterRequest;
import com.dan.taskmanagementsystem.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @Operation(
        summary = "Регистрация пользователя",
        description = "Позволяет зарегистрировать нового пользователя. Возвращает access и refresh токены",
        responses = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для регистрации")
        }
    )
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @Operation(
        summary = "Аутентификация пользователя",
        description = "Позволяет пользователю войти в систему. Возвращает access и refresh токены",
        responses = {
            @ApiResponse(responseCode = "200", description = "Аутентификация прошла успешно"),
            @ApiResponse(responseCode = "401", description = "Некорректные учетные данные")
        }
    )
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @Operation(
        summary = "Обновление токена",
        description = "Обновляет access токен, используя refresh токен. Отправляет новый токен в ответ",
        responses = {
            @ApiResponse(responseCode = "200", description = "Токен успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
        }
    )
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }
}
