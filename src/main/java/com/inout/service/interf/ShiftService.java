package com.inout.service.interf;

import com.inout.dto.ShiftDTO;

import java.util.List;

public interface ShiftService {
    ShiftDTO checkIn(Long employeeId);
    ShiftDTO checkOut(Long employeeId);
    List<ShiftDTO> getShiftsForEmployee(Long employeeId);
    List<ShiftDTO> getAllShifts(); // ← para admin
}
