package org.example.service.student.impl;

import org.example.entity.student.StudentCandidate;
import org.example.repository.student.StudentCandidateRepository;
import org.example.service.student.StudentCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        // 중복 체크
        isDuplicateApplicationNumber(studentCandidate);

        studentCandidateRepository.save(studentCandidate);

        return studentCandidate.getId();
    }

    @Override
    public void isDuplicateApplicationNumber(StudentCandidate studentCandidate) {
        studentCandidateRepository.findByApplicationNumber(studentCandidate.getApplicationNumber())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 후보자입니다.");
                        });
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
