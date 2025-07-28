package com.inout.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inout.dto.ShiftDTO;
import com.inout.service.interf.ShiftService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShiftController.class)
class ShiftControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShiftService shiftService;

    private final ObjectMapper mapper = new ObjectMapper();

    private final ShiftDTO sampleShift = new ShiftDTO(
            LocalDateTime.of(2024, 7, 1, 9, 0),
            LocalDateTime.of(2024, 7, 1, 17, 0),
            8.0
    );

    @Test
    void checkIn_shouldReturnShiftDTO() throws Exception {
        Mockito.when(shiftService.checkIn(anyLong())).thenReturn(sampleShift);

        mockMvc.perform(post("/shifts/checkin?employeeId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalHours").value(8.0));
    }

    @Test
    void checkOut_shouldReturnShiftDTO() throws Exception {
        Mockito.when(shiftService.checkOut(anyLong())).thenReturn(sampleShift);

        mockMvc.perform(post("/shifts/checkout?employeeId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.checkOut").exists());
    }

    @Test
    void getMyShifts_shouldReturnList() throws Exception {
        Mockito.when(shiftService.getShiftsForEmployee(anyLong())).thenReturn(List.of(sampleShift));

        mockMvc.perform(get("/shifts/me?employeeId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].totalHours").value(8.0));
    }

    @Test
    void getAllShifts_shouldReturnList() throws Exception {
        Mockito.when(shiftService.getAllShifts()).thenReturn(List.of(sampleShift));

        mockMvc.perform(get("/shifts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].checkIn").exists());
    }
}
