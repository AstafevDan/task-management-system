package com.dan.taskmanagementsystem.integration.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.dan.taskmanagementsystem.entity.User;
import com.dan.taskmanagementsystem.integration.IntegrationTestBase;
import com.dan.taskmanagementsystem.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryIT extends IntegrationTestBase {

    private final UserRepository userRepository;

    private static final String EMAIL = "admin@gmail.com";

    @Test
    void testFindByEmail() {
        Optional<User> user = userRepository.findByEmail(EMAIL);

        assertTrue(user.isPresent());
        assertEquals(EMAIL, user.get().getEmail());
    }
}
