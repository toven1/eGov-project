package org.example.entity.student;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_active")
@EntityListeners(AuditingEntityListener.class)
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
    private int academicYear;
    private int semester;
    private String faculty;
    private String department;
    private String departmentCode;
    private String classGroup;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime admissionDate;

    public StudentActive() {}

    public StudentActive(StudentCandidate studentCandidate) {
        this.name = studentCandidate.getName();
        this.rrn = studentCandidate.getRrn();
        this.phone = studentCandidate.getPhone();
        this.address = studentCandidate.getAddress();
        this.studentNumber = studentCandidate.getStudentNumber();
        this.admissionType = studentCandidate.getApplicationType();
        this.faculty = studentCandidate.getFaculty();
        this.department = studentCandidate.getDepartment();
        this.departmentCode = studentCandidate.getDepartmentCode();
        this.classGroup = "신입생";
    }

//    public Long getId() {return id;}
//    public void setId(Long id) {this.id = id;}
//
//    public String getName() {return name;}
//    public void setName(String name) {this.name = name;}
//
//    public String getPassword() {return password;}
//    public void setPassword(String password) {this.password = password;}
//
//    public String getRrn() {return rrn;}
//    public void setRrn(String rrn) {this.rrn = rrn;}
//
//    public String getPhone() {return phone;}
//    public void setPhone(String phone) {this.phone = phone;}
//
//    public String getAddress() {return address;}
//    public void setAddress(String address) {this.address = address;}
//
//    public int getStudent_number() {return student_number;}
//    public void setStudent_number(int student_number) {this.student_number = student_number;}
//
//    public Status getStatus() {return status;}
//    public void setStatus(Status status) {this.status = status;}
//
//    public String getAdmission_type() {return admission_type;}
//    public void setAdmission_type(String admission_type) {this.admission_type = admission_type;}
//
//    public int getAcademic_year() {return academic_year;}
//    public void setAdmission_date(){this.admission_date = LocalDate.parse(admission_date.toString());}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAdmissionType() {
        return admissionType;
    }

    public void setAdmissionType(String admissionType) {
        this.admissionType = admissionType;
    }

    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getClassGroup() {
        return classGroup;
    }

    public void setClassGroup(String classGroup) {
        this.classGroup = classGroup;
    }

    public LocalDateTime getAdmissionDateTime() {
        return admissionDate;
    }

    public void setAdmissionDateTime(LocalDateTime admissionDate) {
        this.admissionDate = admissionDate;
    }
}
