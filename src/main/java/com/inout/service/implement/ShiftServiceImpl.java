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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository shiftRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public ShiftDTO checkIn(Long employeeId) {
        // check-in
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Verify if there is a active shift (checkin ok and checkout ko)
        LocalDate today = LocalDate.now();
        boolean alreadyCheckedIn = shiftRepository.existsByEmployeeAndCheckInBetween(
                employee,
                today.atStartOfDay(),
                today.plusDays(1).atStartOfDay()
        );

        if (alreadyCheckedIn) {
            throw new IllegalStateException("Already checked in today");
        }

        // Create a new shift
        Shift shift = new Shift();
        shift.setEmployee(employee);
        shift.setCheckIn(LocalDateTime.now());

        // Save shift
        shiftRepository.save(shift);

        return new ShiftDTO(shift.getCheckIn(), shift.getCheckOut(), shift.getTotalHours());
    }

    @Override
    public ShiftDTO checkOut(Long employeeId) {
        // check-out
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // find the last shift active (checkin ok and checkout ko)
        Shift shift = shiftRepository.findTopByEmployeeAndCheckOutIsNullOrderByCheckInDesc(employee)
                .orElseThrow(() -> new RuntimeException("No active shift found"));

        shift.setCheckOut(LocalDateTime.now()); // actual time check-out
        Duration duration = Duration.between(shift.getCheckIn(), shift.getCheckOut());
        //shift.setTotalHours((double) duration.toHours()); // Calculate total hours
        shift.setTotalHours(duration.toMinutes() / 60.0); // Calculate total hours
        shiftRepository.save(shift);

        return new ShiftDTO(shift.getCheckIn(), shift.getCheckOut(), shift.getTotalHours());
    }

    @Override
    public List<ShiftDTO> getShiftsForEmployee(Long employeeId) {
        // obtain an employee's shifts
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        //get all shifts for employee
        List<Shift> shifts = shiftRepository.findByEmployeeOrderByCheckInDesc(employee);

        //  ShiftDTO
        return shifts.stream()
                .map(shift -> {
                    // employee still working
                    if (shift.getCheckIn() != null && shift.getCheckOut() == null) {
                        shift.setCheckOut(LocalDateTime.now()); // asign actual hour how checkout
                        shift.setTotalHours(0.0); // if don't have checkout, for now, have 0 hours
                    }

                    // DTO
                    return new ShiftDTO(shift.getCheckIn(), shift.getCheckOut(), shift.getTotalHours());
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ShiftDTO> getAllShifts() {
        // get all shifts (admin only)
        List<Shift> shifts = shiftRepository.findAll();

        //DTO
        return shifts.stream()
                .map(shift -> {
                    if (shift.getCheckIn() != null && shift.getCheckOut() == null) {
                        shift.setCheckOut(LocalDateTime.now()); // asign actual hour how checkout
                        shift.setTotalHours(0.0); // if don't have checkout, for now, have 0 hours
                    }

                    return new ShiftDTO(shift.getCheckIn(), shift.getCheckOut(), shift.getTotalHours());
                })
                .collect(Collectors.toList());
    }

    @Override
    public ShiftDTO updateShift(Long shiftId, ShiftDTO shiftDTO) {
        // update a shift completely
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found"));

        shift.setCheckIn(shiftDTO.getCheckIn());
        shift.setCheckOut(shiftDTO.getCheckOut());
        shift.setTotalHours(shiftDTO.getTotalHours());

        shiftRepository.save(shift);

        return new ShiftDTO(shift.getCheckIn(), shift.getCheckOut(), shift.getTotalHours());
    }

    @Override
    public ShiftDTO partiallyUpdateShift(Long shiftId, ShiftDTO shiftDTO) {
        // partially update a shift
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found"));

        // Update only fields that are not null or empty
        if (shiftDTO.getCheckIn() != null) {
            shift.setCheckIn(shiftDTO.getCheckIn());
        }
        if (shiftDTO.getCheckOut() != null) {
            shift.setCheckOut(shiftDTO.getCheckOut());
        }
        if (shiftDTO.getTotalHours() != null) {
            shift.setTotalHours(shiftDTO.getTotalHours());
        }

        shiftRepository.save(shift);

        return new ShiftDTO(shift.getCheckIn(), shift.getCheckOut(), shift.getTotalHours());
    }
    @Override
    public void deleteShift(Long shiftId) {
        // Delete shift
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found"));

        shiftRepository.delete(shift);  // Delete shift in DB
    }
}
