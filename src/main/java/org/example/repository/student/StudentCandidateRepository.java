package org.example.repository.student;


import org.example.entity.student.StudentCandidate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentCandidateRepository {
    StudentCandidate save(StudentCandidate studentCandidate);

    Optional<StudentCandidate> findById(Long id);

    List<StudentCandidate> findByName(String name);

    Optional<StudentCandidate> findByApplicationNumber(int applicationNumber);

    List<StudentCandidate> findByApplicationType(String applicationType);

    List<StudentCandidate> findByFaculty(String faculty);

    List<StudentCandidate> findByDepartment(String department);

    List<StudentCandidate> findByIsAdmitted(boolean isAdmitted);

    Optional<StudentCandidate> findByStudentNumber(int studentNumber);

    List<StudentCandidate> findAll();

}
