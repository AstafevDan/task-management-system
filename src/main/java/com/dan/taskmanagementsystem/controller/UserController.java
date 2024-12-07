package com.dan.taskmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "Executor user")
public class UserController {

    @Operation(
            description = "Get endpoint for executor user",
            summary = "This is a summary for executor user get endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping
    public String get() {
        return "GET:: user controller";
    }

    @PostMapping
    public String post() {
        return "POST:: user controller";
    }

    @PutMapping
    public String put() {
        return "PUT:: user controller";
    }

    @DeleteMapping
    public String delete() {
        return "DELETE:: user controller";
    }
}
