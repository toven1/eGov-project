package org.example.entity.student;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_not_admitted")
@Data
public class StudentNotAdmitted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private Integer applicationNumber;
    private LocalDateTime applicationDate;
    private String applicationType;
    private String faculty;
    private String department;
    private String departmentCode;

    public StudentNotAdmitted() {}
    public StudentNotAdmitted(StudentCandidate studentCandidate) {
        this.name = studentCandidate.getName();
        this.phone = studentCandidate.getPhone();
        this.applicationNumber = studentCandidate.getApplicationNumber();
        this.applicationDate = studentCandidate.getApplicationDateTime();
        this.applicationType = studentCandidate.getApplicationType();
        this.faculty = studentCandidate.getFaculty();
        this.department = studentCandidate.getDepartment();
        this.departmentCode = studentCandidate.getDepartmentCode();
    }
}
