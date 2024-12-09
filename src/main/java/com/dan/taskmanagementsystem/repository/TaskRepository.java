package com.dan.taskmanagementsystem.repository;

import com.dan.taskmanagementsystem.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("""
SELECT t FROM Task t 
WHERE (:authorId IS NULL OR t.author.id = :authorId) AND
(:executorId IS NULL OR t.executor.id = :executorId) AND
(:status IS NULL OR t.status = :status) AND\s
(:priority IS NULL OR t.priority = :priority)
""")
    Page<Task> findByFilter(
            @Param("authorId") Long authorId,
            @Param("executorId") Long executorId,
            @Param("status") String status,
            @Param("priority") String priority,
            Pageable pageable);
}
