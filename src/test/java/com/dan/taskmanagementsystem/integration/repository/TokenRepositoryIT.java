package com.dan.taskmanagementsystem.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.dan.taskmanagementsystem.entity.Token;
import com.dan.taskmanagementsystem.integration.IntegrationTestBase;
import com.dan.taskmanagementsystem.repository.TokenRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenRepositoryIT extends IntegrationTestBase {

    private final TokenRepository tokenRepository;

    private static final Long USER_ID = 1L;
    private static final String TOKEN = "1234";

    @Test
    void testFindAllValid() {
        List<Token> tokens = tokenRepository.findAllValidTokensByUser(USER_ID);

        assertNotNull(tokens);
        assertThat(tokens).hasSize(1);
    }

    @Test
    void testFindByToken() {
        Optional<Token> maybeToken = tokenRepository.findByToken(TOKEN);

        assertTrue(maybeToken.isPresent());
        assertEquals(TOKEN, maybeToken.get().getToken());
    }
}
