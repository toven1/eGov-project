package org.example.service.student.impl;

import org.example.config.AppConfig;
import org.example.entity.student.StudentCandidate;
import org.example.repository.student.StudentCandidateRepository;
import org.example.service.student.StudentCandidateService;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.example.util.ResidentRegistrationNumberEncryptor.*;
import static org.junit.Assert.assertTrue;

@Service
@Transactional
public class StudentCandidateServiceImpl implements StudentCandidateService {

    @Autowired
    private final StudentCandidateRepository studentCandidateRepository;

    @Autowired
    public StudentCandidateServiceImpl(StudentCandidateRepository studentCandidateRepository) {
        this.studentCandidateRepository = studentCandidateRepository;
    }

    @Override
    public Long saveCandidate(StudentCandidate studentCandidate) {

        // 필요 데이터 체크
        checkRequiredData(studentCandidate);

        // 주민번호 암호화
        studentCandidate.setRrn(encryptRrn(studentCandidate.getRrn()));

        // 중복 체크
        isDuplicateCandidate(studentCandidate);

        // 저장
        studentCandidateRepository.save(studentCandidate);

        return studentCandidate.getId();
    }

    // 중복 체크
    // 수험번호 + 주민번호
    @Override
    public void isDuplicateCandidate(@NotNull StudentCandidate studentCandidate) {

        // 주민번호
        Optional<StudentCandidate> rrn = studentCandidateRepository.findByRrn(studentCandidate.getRrn());
        // 수험번호
        Optional<StudentCandidate> applicationNumber = studentCandidateRepository.findByApplicationNumber(studentCandidate.getApplicationNumber());

        // 묶어서 체크
        Stream.of(rrn, applicationNumber).forEach(m -> m.ifPresent(
                n -> {
                    throw new IllegalStateException("이미 존재하는 후보자입니다.");
                }
        ));
    }

    // 필요 데이터 체크
    @Override
    public void checkRequiredData(@NotNull StudentCandidate studentCandidate) {
        if (studentCandidate.getName() == null) throw new IllegalStateException("이름");
        if (studentCandidate.getRrn() == null) throw new IllegalStateException("주민번호");
        if (studentCandidate.getPhone() == null ) throw new IllegalStateException("전화번호");
        if (studentCandidate.getAddress() == null) throw new IllegalStateException("주소");
        if (studentCandidate.getApplicationDate() == null) throw new IllegalStateException("지원날짜");
        if (studentCandidate.getApplicationNumber() == null) throw new IllegalStateException("수험번호");
        if (studentCandidate.getApplicationType() == null ) throw new IllegalStateException("지원유형");
        if (studentCandidate.getFaculty() == null) throw new IllegalStateException("학부");
        if (studentCandidate.getDepartment() == null) throw new IllegalStateException("학과");
    }

    @Override
    public void acceptCandidates(List<Integer> applicationNumbers) {
        List<StudentCandidate> candidates = studentCandidateRepository.findByApplicationNumberIn(applicationNumbers);
        candidates.forEach(candidate -> candidate.setAdmitted(true));
        studentCandidateRepository.saveAll(candidates);
    }

    @Override
    public void acceptCandidate(Integer applicationNumber) {
        Optional<StudentCandidate> sc = studentCandidateRepository.findByApplicationNumber(applicationNumber);
        // 없으면 실행 X
        sc.ifPresent(studentCandidate->{
            studentCandidate.setAdmitted(true);
            studentCandidateRepository.save(studentCandidate);
                });
    }

    @Override
    public List<StudentCandidate> findAllStudentCandidates() {
        return studentCandidateRepository.findAll();
    }

    @Override
    public Optional<StudentCandidate> findOneStudentCandidate(Integer applicationNumber) {
        return studentCandidateRepository.findByApplicationNumber(applicationNumber);
    }

    @Override
    public List<StudentCandidate> findStudentCandidatesByFaculty(String faculty) {
        return studentCandidateRepository.findByFaculty(faculty);
    }

    @Override
    public List<StudentCandidate> findStudentCandidatesByDepartment(String department) {
        return studentCandidateRepository.findByDepartment(department);
    }

    @Override
    public List<StudentCandidate> findStudentCandidatesByApplicationType(String applicationType) {
        return studentCandidateRepository.findByApplicationType(applicationType);
    }

    @Override
    public List<StudentCandidate> findAdmittedStudentCandidates() {
        return studentCandidateRepository.findByIsAdmitted(true);
    }

    @Override
    public List<StudentCandidate> findNotAdmittedStudentCandidates() {
        return studentCandidateRepository.findByIsAdmitted(false);
    }
}
