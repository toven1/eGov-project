package org.example.service.student.impl;

import org.example.entity.student.StudentActive;
import org.example.entity.student.StudentCandidate;
import org.example.repository.student.StudentActiveRepository;
import org.example.repository.student.StudentCandidateRepository;
import org.example.service.student.StudentActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentActiveServiceImpl implements StudentActiveService {

    @Autowired
    private final StudentCandidateRepository studentCandidateRepository;
    @Autowired
    private final StudentActiveRepository studentActiveRepository;

    public StudentActiveServiceImpl(StudentCandidateRepository studentCandidateRepository, StudentActiveRepository studentActiveRepository) {
        this.studentCandidateRepository = studentCandidateRepository;
        this.studentActiveRepository = studentActiveRepository;
    }

    @Override
    public Long signUp(StudentActive studentActive) {

        StudentCandidate candidate = studentCandidateRepository.findByStudentNumber(studentActive.getStudentNumber())
                .orElseThrow(() -> new IllegalArgumentException("해당 학생이 없습니다."));
        studentCandidateRepository.delete(candidate);

        return studentActiveRepository.save(studentActive).getId();
    }
}
