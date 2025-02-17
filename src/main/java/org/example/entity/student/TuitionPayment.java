package org.example.entity.student;


import javax.persistence.*;

@Entity
@Table(name = "tuition_payments")
public class TuitionPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer studentNumber;

    @Column(name = "year_1_semester_1", nullable = false)
    private Boolean year1Semester1 = false;

    @Column(name = "year_1_semester_2", nullable = false)
    private Boolean year1Semester2 = false;

    @Column(name = "year_2_semester_1", nullable = false)
    private Boolean year2Semester1 = false;

    @Column(name = "year_2_semester_2", nullable = false)
    private Boolean year2Semester2 = false;

    @Column(name = "year_3_semester_1", nullable = false)
    private Boolean year3Semester1 = false;

    @Column(name = "year_3_semester_2", nullable = false)
    private Boolean year3Semester2 = false;

    @Column(name = "year_4_semester_1", nullable = false)
    private Boolean year4Semester1 = false;

    @Column(name = "year_4_semester_2", nullable = false)
    private Boolean year4Semester2 = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Boolean getYear1Semester1() {
        return year1Semester1;
    }

    public void setYear1Semester1(Boolean year1Semester1) {
        this.year1Semester1 = year1Semester1;
    }

    public Boolean getYear1Semester2() {
        return year1Semester2;
    }

    public void setYear1Semester2(Boolean year1Semester2) {
        this.year1Semester2 = year1Semester2;
    }

    public Boolean getYear2Semester1() {
        return year2Semester1;
    }

    public void setYear2Semester1(Boolean year2Semester1) {
        this.year2Semester1 = year2Semester1;
    }

    public Boolean getYear2Semester2() {
        return year2Semester2;
    }

    public void setYear2Semester2(Boolean year2Semester2) {
        this.year2Semester2 = year2Semester2;
    }

    public Boolean getYear3Semester1() {
        return year3Semester1;
    }

    public void setYear3Semester1(Boolean year3Semester1) {
        this.year3Semester1 = year3Semester1;
    }

    public Boolean getYear3Semester2() {
        return year3Semester2;
    }

    public void setYear3Semester2(Boolean year3Semester2) {
        this.year3Semester2 = year3Semester2;
    }

    public Boolean getYear4Semester1() {
        return year4Semester1;
    }

    public void setYear4Semester1(Boolean year4Semester1) {
        this.year4Semester1 = year4Semester1;
    }

    public Boolean getYear4Semester2() {
        return year4Semester2;
    }

    public void setYear4Semester2(Boolean year4Semester2) {
        this.year4Semester2 = year4Semester2;
    }
}
