package com.dan.taskmanagementsystem.controller;

import com.dan.taskmanagementsystem.dto.TaskCreateEditDto;
import com.dan.taskmanagementsystem.dto.TaskReadDto;
import com.dan.taskmanagementsystem.entity.User;
import com.dan.taskmanagementsystem.repository.UserRepository;
import com.dan.taskmanagementsystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    /*@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TaskReadDto> create(@RequestBody TaskCreateEditDto dto, Principal principal) {
        Optional<User> user = userRepository.findByEmail(principal.getName());
        dto.setAuthorId(user.map(User::getId).orElse(null));
        return ResponseEntity.ok(taskService.create(dto));
    }*/
}
