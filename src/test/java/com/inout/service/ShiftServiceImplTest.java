package com.inout.service;

import com.inout.dto.ShiftDTO;
import com.inout.model.Employee;
import com.inout.model.Shift;
import com.inout.repository.EmployeeRepository;
import com.inout.repository.ShiftRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShiftServiceImplTest {

    @InjectMocks
    private ShiftServiceImpl shiftService;

    @Mock
    private ShiftRepository shiftRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
    }

    @Test
    void checkIn_success() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(shiftRepository.existsByEmployeeAndCheckInBetween(any(), any(), any())).thenReturn(false);
        when(shiftRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        ShiftDTO dto = shiftService.checkIn(1L);

        assertNotNull(dto.getCheckIn());
        assertNull(dto.getCheckOut());
        assertNull(dto.getTotalHours());
    }

    @Test
    void checkIn_alreadyExists_shouldThrow() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(shiftRepository.existsByEmployeeAndCheckInBetween(any(), any(), any())).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> shiftService.checkIn(1L));
        assertEquals("Already checked in today", ex.getMessage());
    }

    @Test
    void checkOut_success() {
        Shift shift = new Shift();
        shift.setEmployee(employee);
        shift.setCheckIn(LocalDateTime.now().minusHours(4));

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(shiftRepository.findTopByEmployeeAndCheckOutIsNullOrderByCheckInDesc(any())).thenReturn(Optional.of(shift));
        when(shiftRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        ShiftDTO dto = shiftService.checkOut(1L);

        assertNotNull(dto.getCheckOut());
        assertTrue(dto.getTotalHours() > 0);
    }

    @Test
    void checkOut_withoutCheckIn_shouldThrow() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(shiftRepository.findTopByEmployeeAndCheckOutIsNullOrderByCheckInDesc(any())).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> shiftService.checkOut(1L));
        assertEquals("No open shift found", ex.getMessage());
    }

    @Test
    void getShiftsForEmployee_returnsShifts() {
        Shift s = new Shift();
        s.setCheckIn(LocalDateTime.now().minusHours(2));
        s.setCheckOut(LocalDateTime.now());
        s.setTotalHours(2.0);
        s.setEmployee(employee);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(shiftRepository.findByEmployeeOrderByCheckInDesc(employee)).thenReturn(List.of(s));

        List<ShiftDTO> result = shiftService.getShiftsForEmployee(1L);

        assertEquals(1, result.size());
        assertEquals(2.0, result.get(0).getTotalHours());
    }

    @Test
    void getAllShifts_returnsShifts() {
        Shift s = new Shift();
        s.setCheckIn(LocalDateTime.now().minusHours(3));
        s.setCheckOut(LocalDateTime.now());
        s.setTotalHours(3.0);
        s.setEmployee(employee);

        when(shiftRepository.findAll()).thenReturn(List.of(s));

        List<ShiftDTO> result = shiftService.getAllShifts();

        assertEquals(1, result.size());
        assertEquals(3.0, result.get(0).getTotalHours());
    }
}
