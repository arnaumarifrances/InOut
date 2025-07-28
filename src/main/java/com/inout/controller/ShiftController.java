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

    @PostMapping("/checkin")
    public ResponseEntity<ShiftDTO> checkIn(@RequestParam Long employeeId) {
        return ResponseEntity.ok(shiftService.checkIn(employeeId));
    }

    @PostMapping("/checkout")
    public ResponseEntity<ShiftDTO> checkOut(@RequestParam Long employeeId) {
        return ResponseEntity.ok(shiftService.checkOut(employeeId));
    }

    @GetMapping("/me")
    public ResponseEntity<List<ShiftDTO>> getMyShifts(@RequestParam Long employeeId) {
        return ResponseEntity.ok(shiftService.getShiftsForEmployee(employeeId));
    }

    @GetMapping
    public ResponseEntity<List<ShiftDTO>> getAllShifts() {
        return ResponseEntity.ok(shiftService.getAllShifts());
    }
}
