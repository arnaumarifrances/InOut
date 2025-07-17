package com.inout.advice;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleRuntime_shouldReturnBadRequest() {
        RuntimeException ex = new RuntimeException("Something went wrong");

        ResponseEntity<Map<String, String>> response = handler.handleRuntime(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Something went wrong", response.getBody().get("error"));
    }

    @Test
    void handleValidation_shouldReturnFormattedErrorMessage() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(
                new FieldError("object", "email", "must be valid"),
                new FieldError("object", "password", "must not be blank")
        ));

        ResponseEntity<Map<String, String>> response = handler.handleValidation(ex);

        assertEquals(400, response.getStatusCodeValue());
        String errorMsg = response.getBody().get("error");

        assertTrue(errorMsg.contains("email: must be valid"));
        assertTrue(errorMsg.contains("password: must not be blank"));
    }
}
