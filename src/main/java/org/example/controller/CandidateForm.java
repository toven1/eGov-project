package org.example.controller;

public class CandidateForm {
//    sc.setName("test Name");
//        sc.setRrn("111111-1111111");
//        sc.setPhone("010-1234-5678");
//        sc.setAddress("서울특별시 무슨구 무슨동");
//        sc.setApplicationNumber(12345678);
//        sc.setApplicationType("수시");
//        sc.setFaculty("IT 융합");
//        sc.setDepartment("정보보안학과");

    private String name;
    private String rrn;
    private String phone;
    private String address;
    private Integer applicationNumber;
    private String applicationType;
    private String faculty;
    private String department;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(Integer applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
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
}
