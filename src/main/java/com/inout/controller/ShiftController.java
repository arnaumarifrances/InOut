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

    // POST: Create a new check-in
    @PostMapping("/checkin")
    public ResponseEntity<ShiftDTO> checkIn(@RequestParam Long employeeId) {
        return ResponseEntity.ok(shiftService.checkIn(employeeId));
    }

    // POST: Create a new check-out
    @PostMapping("/checkout")
    public ResponseEntity<ShiftDTO> checkOut(@RequestParam Long employeeId) {
        return ResponseEntity.ok(shiftService.checkOut(employeeId));
    }

    // GET: Get shifts for a specific employee
    @GetMapping("/me")
    public ResponseEntity<List<ShiftDTO>> getMyShifts(@RequestParam Long employeeId) {
        return ResponseEntity.ok(shiftService.getShiftsForEmployee(employeeId));
    }

    // GET: Get all shifts (Admin only)
    @GetMapping
    public ResponseEntity<List<ShiftDTO>> getAllShifts() {
        return ResponseEntity.ok(shiftService.getAllShifts());
    }

    // PUT: Update a shift (if needed)
    @PutMapping("/{shiftId}")
    public ResponseEntity<ShiftDTO> updateShift(@PathVariable Long shiftId, @RequestBody ShiftDTO shiftDTO) {
        // Assuming updateShift method exists in service
        ShiftDTO updatedShift = shiftService.updateShift(shiftId, shiftDTO);
        return ResponseEntity.ok(updatedShift);
    }

    // PATCH: Partially update a shift (if needed)
    @PatchMapping("/{shiftId}")
    public ResponseEntity<ShiftDTO> partiallyUpdateShift(@PathVariable Long shiftId, @RequestBody ShiftDTO shiftDTO) {
        // Assuming partial update method exists in service
        ShiftDTO updatedShift = shiftService.partiallyUpdateShift(shiftId, shiftDTO);
        return ResponseEntity.ok(updatedShift);
    }

    // DELETE: Delete a shift
    @DeleteMapping("/{shiftId}")
    public ResponseEntity<Void> deleteShift(@PathVariable Long shiftId) {
        // Assuming deleteShift method exists in service
        shiftService.deleteShift(shiftId);
        return ResponseEntity.noContent().build();
    }
}
