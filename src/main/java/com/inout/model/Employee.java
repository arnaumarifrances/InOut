package com.inout.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Employee extends User {
    private String department;
    private String position;
}
