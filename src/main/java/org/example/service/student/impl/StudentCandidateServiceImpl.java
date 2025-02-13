package org.example.service.student.impl;

import org.example.entity.student.StudentCandidate;
import org.example.repository.student.StudentCandidateRepository;
import org.example.service.student.StudentCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.example.util.ResidentRegistrationNumberEncryptor.*;

@Service
@Transactional
public class StudentCandidateServiceImpl implements StudentCandidateService {

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
    public void isDuplicateCandidate(StudentCandidate studentCandidate) {

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
//        studentCandidateRepository.findByApplicationNumber(studentCandidate.getApplicationNumber())
//                .ifPresent(m -> {
//                    throw new IllegalStateException("이미 존재하는 후보자입니다.");
//                        });
    }

    // 필요 데이터 체크
    @Override
    public void checkRequiredData(StudentCandidate studentCandidate) {
        if (studentCandidate.getName() == null) throw new IllegalStateException("이름");
        if (studentCandidate.getRrn() == null) throw new IllegalStateException("주민번호");
        if (studentCandidate.getPhone() == null ) throw new IllegalStateException("전화번호");
        if (studentCandidate.getAddress() == null) throw new IllegalStateException("주소");
        if (studentCandidate.getApplicationDate() == null) throw new IllegalStateException("날짜");
        if (studentCandidate.getApplicationNumber() == null) throw new IllegalStateException("수험번호");
        if (studentCandidate.getApplicationType() == null ) throw new IllegalStateException("유형");
        if (studentCandidate.getFaculty() == null) throw new IllegalStateException("학부");
        if (studentCandidate.getDepartment() == null) throw new IllegalStateException("학과");
    }

    @Override
    public List<StudentCandidate> findAllStudentCandidates() {
        return studentCandidateRepository.findAll();
    }

    @Override
    public Optional<StudentCandidate> findOneStudentCandidate(int applicationNumber) {
        return studentCandidateRepository.findByStudentNumber(applicationNumber);
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
