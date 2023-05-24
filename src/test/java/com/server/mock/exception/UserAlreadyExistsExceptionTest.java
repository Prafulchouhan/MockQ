package com.server.mock.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.*;

class UserAlreadyExistsExceptionTest {
        @Test
        public void testUserAlreadyExistsException() {
            assertThrows(UserAlreadyExistsException.class, () -> {
                throw new UserAlreadyExistsException();
            });
        }


    @BeforeEach
    void setUp() {
    }
}