package com.inout.repository;

import com.inout.model.Employee;
import com.inout.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

    boolean existsByEmployeeAndCheckInBetween(Employee employee, LocalDateTime start, LocalDateTime end);

    Optional<Shift> findTopByEmployeeAndCheckOutIsNullOrderByCheckInDesc(Employee employee);

    List<Shift> findByEmployeeOrderByCheckInDesc(Employee employee);
}
