package com.inout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Employee extends User {

    private String department;
    private String position;

    //  bidirectional with Shift
    @OneToMany(mappedBy = "employee")
    private List<Shift> shifts;
}

