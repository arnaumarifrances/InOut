package com.inout.service.interf;

import com.inout.dto.ShiftDTO;
import java.util.List;

public interface ShiftService {
    ShiftDTO checkIn(Long employeeId);
    ShiftDTO checkOut(Long employeeId);
    List<ShiftDTO> getShiftsForEmployee(Long employeeId);
    List<ShiftDTO> getAllShifts(); //admin only
    ShiftDTO updateShift(Long shiftId, ShiftDTO shiftDTO);


    ShiftDTO partiallyUpdateShift(Long shiftId, ShiftDTO shiftDTO);
    void deleteShift(Long shiftId);
}
