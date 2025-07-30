package com.inout.controller;

import com.inout.dto.ShiftDTO;
import com.inout.service.interf.ShiftService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class ShiftControllerTest {

    @Mock
    private ShiftService shiftService;

    @InjectMocks
    private ShiftController shiftController;

    private ShiftDTO shiftDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Create a sample ShiftDTO for the tests
        shiftDTO = new ShiftDTO();
        shiftDTO.setCheckIn(null);
        shiftDTO.setCheckOut(null);
        shiftDTO.setTotalHours(0.0);
    }

    @Test
    void testCheckIn() {
        // Simulate the service behavior for check-in
        when(shiftService.checkIn(1L)).thenReturn(shiftDTO);

        // Call the controller method
        ResponseEntity<ShiftDTO> response = shiftController.checkIn(1L);

        // Verify the response
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(shiftDTO);
        // Verify that the service method was called once
        verify(shiftService, times(1)).checkIn(1L);
    }

    @Test
    void testCheckOut() {
        // Simulate the service behavior for check-out
        when(shiftService.checkOut(1L)).thenReturn(shiftDTO);

        // Call the controller method
        ResponseEntity<ShiftDTO> response = shiftController.checkOut(1L);

        // Verify the response
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(shiftDTO);
        // Verify that the service method was called once
        verify(shiftService, times(1)).checkOut(1L);
    }
}
