package com.inout.controller;

import com.inout.dto.ShiftDTO;
import com.inout.service.interf.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shifts")
@RequiredArgsConstructor
public class ShiftController {

    private final ShiftService shiftService;

    // POST: Create a new check-in (Employee only)
    @PostMapping("/checkin/{employeeId}")
    public ResponseEntity<ShiftDTO> checkIn(@PathVariable Long employeeId) {
        ShiftDTO shiftDTO = shiftService.checkIn(employeeId);
        return ResponseEntity.ok(shiftDTO);
    }

    // POST: Create a new check-out (Employee only)
    @PostMapping("/checkout/{employeeId}")
    public ResponseEntity<ShiftDTO> checkOut(@PathVariable Long employeeId) {
        ShiftDTO shiftDTO = shiftService.checkOut(employeeId);
        return ResponseEntity.ok(shiftDTO);
    }

    // GET: Get shifts for a specific employee
    @GetMapping("/lookFor/{employeeId}")
    public ResponseEntity<List<ShiftDTO>> getMyShifts(@PathVariable Long employeeId) {
        List<ShiftDTO> shifts = shiftService.getShiftsForEmployee(employeeId);
        return ResponseEntity.ok(shifts);
    }

    // GET: Get all shifts (Admin only)
    @GetMapping
    public ResponseEntity<List<ShiftDTO>> getAllShifts() {
        List<ShiftDTO> shifts = shiftService.getAllShifts();
        return ResponseEntity.ok(shifts);
    }

    // PUT: Update a shift (Admin or employee who owns the shift)
    @PutMapping("/{shiftId}")
    public ResponseEntity<ShiftDTO> updateShift(@PathVariable Long shiftId, @RequestBody ShiftDTO shiftDTO) {
        ShiftDTO updatedShift = shiftService.updateShift(shiftId, shiftDTO);
        return ResponseEntity.ok(updatedShift);
    }

    // PATCH: Partially update a shift (Admin or employee who owns the shift)
    @PatchMapping("/{shiftId}")
    public ResponseEntity<ShiftDTO> partiallyUpdateShift(@PathVariable Long shiftId, @RequestBody ShiftDTO shiftDTO) {
        ShiftDTO updatedShift = shiftService.partiallyUpdateShift(shiftId, shiftDTO);
        return ResponseEntity.ok(updatedShift);
    }

    // DELETE: Delete a shift (Admin or employee who owns the shift)
    @DeleteMapping("/{shiftId}")
    public ResponseEntity<Void> deleteShift(@PathVariable Long shiftId) {
        shiftService.deleteShift(shiftId);
        return ResponseEntity.noContent().build();
    }
}
