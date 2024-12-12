package com.dan.taskmanagementsystem.http.rest;

import com.dan.taskmanagementsystem.dto.*;
import com.dan.taskmanagementsystem.dto.filter.TaskFilter;
import com.dan.taskmanagementsystem.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @Operation(
        summary = "Получить задачу по идентификатору",
        description = "Возвращает задачу с указанным идентификатором",
        responses = {
            @ApiResponse(responseCode = "200", description = "Задача успешно найдена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<TaskReadDto> getTask(@PathVariable Long id, Principal principal) {
        TaskReadDto task = taskService.findTaskById(id, principal);
        return ResponseEntity.ok(task);
    }

    @Operation(
        summary = "Создать новую задачу",
        description = "Создаёт новую задачу на основе переданных данных.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Задача успешно создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
        }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TaskReadDto> create(@Valid @RequestBody TaskCreateEditDto dto, Principal principal) {
        TaskReadDto task = taskService.createTask(dto, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @Operation(
        summary = "Найти задачи по фильтру",
        description = "Возвращает список задач, соответствующих фильтру.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Задачи успешно найдены"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные фильтра")
        }
    )
    @PostMapping("/filter")
    public ResponseEntity<Page<TaskReadDto>> findTasksByFilter(
            @Valid @RequestBody TaskFilter taskFilter,
            Principal principal,
            Pageable pageable
    ) {
        Page<TaskReadDto> tasks = taskService.findAllTasksByFilter(
                taskFilter.getAuthorId(),
                taskFilter.getExecutorId(),
                taskFilter.getStatus(),
                taskFilter.getPriority(),
                principal,
                pageable
        );
        return ResponseEntity.ok(tasks);
    }

    @Operation(
        summary = "Обновить задачу",
        description = "Обновляет существующую задачу по заданному идентификатору.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Задача успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<TaskReadDto> updateTask(@PathVariable Long id, @Valid @RequestBody TaskCreateEditDto dto, Principal principal) {
        TaskReadDto task = taskService.updateTask(id, dto, principal);
        return ResponseEntity.ok(task);
    }

    @Operation(
        summary = "Удалить задачу",
        description = "Удаляет задачу по заданному идентификатору.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Задача успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
        }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id, Principal principal) {
        if (!taskService.deleteTask(id, principal)) {
            throw new ResponseStatusException(NOT_FOUND);
        }
    }

    @Operation(
        summary = "Обновить статус задачи",
        description = "Обновляет статус задачи по заданному идентификатору.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Статус задачи успешно обновлён"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
        }
    )
    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskReadDto> updateTaskStatus(@PathVariable Long id, @Valid @RequestBody UpdateStatusDto dto, Principal principal) {
        TaskReadDto updatedTask = taskService.updateTaskStatus(id, dto.getStatus(), principal);
        return ResponseEntity.ok(updatedTask);
    }

    @Operation(
        summary = "Обновить приоритет задачи",
        description = "Обновляет приоритет задачи по заданному идентификатору",
        responses = {
            @ApiResponse(responseCode = "200", description = "Приоритет задачи успешно обновлён"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
        }
    )
    @PatchMapping("/{id}/priority")
    public ResponseEntity<TaskReadDto> updateTaskPriority(@PathVariable Long id, @Valid @RequestBody UpdatePriorityDto dto, Principal principal) {
        TaskReadDto updatedTask = taskService.updateTaskPriority(id, dto.getPriority(), principal);
        return ResponseEntity.ok(updatedTask);
    }

    @Operation(
        summary = "Назначить исполнителя задачи",
        description = "Назначает исполнителя для задачи по заданному идентификатору",
        responses = {
            @ApiResponse(responseCode = "200", description = "Исполнитель задачи успешно назначен"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
        }
    )
    @PatchMapping("/{id}/executor")
    public ResponseEntity<TaskReadDto> assignExecutor(@PathVariable Long id, @Valid @RequestBody AssignExecutorDto dto, Principal principal) {
        TaskReadDto updatedTask = taskService.assignExecutor(id, dto.getExecutorId(), principal);
        return ResponseEntity.ok(updatedTask);
    }
}
