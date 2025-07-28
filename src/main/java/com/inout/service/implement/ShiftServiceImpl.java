package com.inout.service.implement;

import com.inout.dto.ShiftDTO;
import com.inout.model.Employee;
import com.inout.model.Shift;
import com.inout.repository.EmployeeRepository;
import com.inout.repository.ShiftRepository;
import com.inout.service.interf.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository shiftRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public ShiftDTO checkIn(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));

        LocalDate today = LocalDate.now();
        boolean alreadyCheckedIn = shiftRepository.existsByEmployeeAndCheckInBetween(
                employee,
                today.atStartOfDay(),
                today.plusDays(1).atStartOfDay()
        );

        if (alreadyCheckedIn) {
            throw new IllegalStateException("Already checked in today");
        }

        Shift shift = new Shift();
        shift.setEmployee(employee);
        shift.setCheckIn(LocalDateTime.now());

        return toDTO(shiftRepository.save(shift));
    }

    @Override
    public ShiftDTO checkOut(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));

        Shift shift = shiftRepository.findTopByEmployeeAndCheckOutIsNullOrderByCheckInDesc(employee)
                .orElseThrow(() -> new IllegalStateException("No open shift found"));

        shift.setCheckOut(LocalDateTime.now());

        Duration duration = Duration.between(shift.getCheckIn(), shift.getCheckOut());
        double hours = Math.round(duration.toMinutes() / 60.0 * 100.0) / 100.0; // redondeo 2 decimales
        shift.setTotalHours(hours);

        return toDTO(shiftRepository.save(shift));
    }

    @Override
    public List<ShiftDTO> getShiftsForEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));

        return shiftRepository.findByEmployeeOrderByCheckInDesc(employee)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShiftDTO> getAllShifts() {
        return shiftRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ShiftDTO toDTO(Shift shift) {
        return new ShiftDTO(
                shift.getCheckIn(),
                shift.getCheckOut(),
                shift.getTotalHours()
        );
    }
}
