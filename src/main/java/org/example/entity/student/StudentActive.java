package org.example.entity.student;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "student_active")
public class StudentActive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String rrn;
    private String phone;
    private String address;
    private Integer studentNumber;
    private Status status;
    private String admissionType;
    private Integer academicYear;
    private Integer semester;
    private String faculty;
    private String department;
    private String classGroup;
    private LocalDate admissionDate;
}
