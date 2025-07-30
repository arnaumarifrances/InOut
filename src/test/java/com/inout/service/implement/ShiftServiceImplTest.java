package com.inout.service.implement;

import com.inout.dto.ShiftDTO;
import com.inout.model.Employee;
import com.inout.model.Shift;
import com.inout.repository.EmployeeRepository;
import com.inout.repository.ShiftRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class ShiftServiceImplTest {

    @Mock
    private ShiftRepository shiftRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private ShiftServiceImpl shiftService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Create a mock employee for the tests
        employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");
        employee.setEmail("john.doe@example.com");
    }

    @Test
    void testCheckIn() {
        // Simulate employee search
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        // Simulate no shift checked in for today
        when(shiftRepository.existsByEmployeeAndCheckInBetween(any(), any(), any())).thenReturn(false);

        // Call the check-in service method
        ShiftDTO shiftDTO = shiftService.checkIn(1L);

        // Verify that the check-in was successful
        assertThat(shiftDTO).isNotNull();
        // Verify that the save method was called once
        verify(shiftRepository, times(1)).save(any(Shift.class));
    }

    @Test
    void testCheckOut() {
        // Simulate the employee and active shift
        Shift shift = new Shift();
        shift.setEmployee(employee);
        shift.setCheckIn(LocalDateTime.now().minusHours(8));
        shift.setCheckOut(null);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(shiftRepository.findTopByEmployeeAndCheckOutIsNullOrderByCheckInDesc(employee)).thenReturn(Optional.of(shift));

        // Call the check-out service method
        ShiftDTO shiftDTO = shiftService.checkOut(1L);

        // Verify that the check-out was successful
        assertThat(shiftDTO).isNotNull();
        // Verify that the save method was called once
        verify(shiftRepository, times(1)).save(any(Shift.class));
    }
}
