package org.example.entity.student;

import java.time.LocalDate;

public class StudentActive {
    private Long id;
    private String name;
    private String password;
    private String rrn;
    private String phone;
    private String address;
    private int student_number;
    private Status status;
    private String admission_type;
    private int academic_year;
    private int semester;
    private String faculty;
    private String department;
    private String class_group;
    private LocalDate admission_date;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getRrn() {return rrn;}
    public void setRrn(String rrn) {this.rrn = rrn;}

    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}

    public int getStudent_number() {return student_number;}
    public void setStudent_number(int student_number) {this.student_number = student_number;}

    public Status getStatus() {return status;}
    public void setStatus(Status status) {this.status = status;}

    public String getAdmission_type() {return admission_type;}
    public void setAdmission_type(String admission_type) {this.admission_type = admission_type;}

    public int getAcademic_year() {return academic_year;}
    public void setAdmission_date(){this.admission_date = LocalDate.parse(admission_date.toString());}
}
