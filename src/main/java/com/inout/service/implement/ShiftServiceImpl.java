package com.inout.service.implement;

import com.inout.dto.ShiftDTO;
import com.inout.model.Employee;
import com.inout.model.Shift;
import com.inout.repository.EmployeeRepository;
import com.inout.repository.ShiftRepository;
import com.inout.service.interf.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository shiftRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public ShiftDTO checkIn(Long employeeId) {
        // check-in
        return null;
    }

    @Override
    public ShiftDTO checkOut(Long employeeId) {
        // check-out
        return null;
    }

    @Override
    public List<ShiftDTO> getShiftsForEmployee(Long employeeId) {
        // obtain an employee's shifts
        return null;
    }

    @Override
    public List<ShiftDTO> getAllShifts() {
        // get all shifts (admin only)
        return null;
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
