package org.example.repository.student;


import org.example.entity.student.StudentCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentCandidateRepository extends JpaRepository<StudentCandidate, Long> {
    StudentCandidate save(StudentCandidate studentCandidate);

    Optional<StudentCandidate> findById(Long id);

    List<StudentCandidate> findByName(String name);
    List<StudentCandidate> findByNameContainingAndIsAdmittedIsNull(String name);
    List<StudentCandidate> findByNameContainingAndIsAdmittedIsTrue(String name);

    List<StudentCandidate> findByPhone(String phone);
    List<StudentCandidate> findByPhoneContainingAndIsAdmittedNull(String phone);
    List<StudentCandidate> findByPhoneContainingAndIsAdmittedTrue(String phone);

    Optional<StudentCandidate> findByApplicationNumber(Integer applicationNumber);
    List<StudentCandidate> findByApplicationNumberContainingAndIsAdmittedIsNull(Integer applicationNumber);
    List<StudentCandidate> findByApplicationNumberContainingAndIsAdmittedIsTrue(Integer applicationNumber);

    List<StudentCandidate> findByApplicationType(String applicationType);
    List<StudentCandidate> findByApplicationTypeContainingAndIsAdmittedIsNull(String applicationType);
    List<StudentCandidate> findByApplicationTypeContainingAndIsAdmittedIsTrue(String applicationType);

    List<StudentCandidate> findByFaculty(String faculty);
    List<StudentCandidate> findByFacultyContainingAndIsAdmittedIsNull(String faculty);
    List<StudentCandidate> findByFacultyContainingAndIsAdmittedIsTrue(String faculty);

    List<StudentCandidate> findByDepartment(String department);
    List<StudentCandidate> findByDepartmentContainingAndIsAdmittedIsNull(String department);
    List<StudentCandidate> findByDepartmentContainingAndIsAdmittedIsTrue(String department);

    List<StudentCandidate> findByIsAdmitted(Boolean isAdmitted);
    List<StudentCandidate> findByIsAdmittedIsNull();

    Optional<StudentCandidate> findByRrn(String rrn);

    Optional<StudentCandidate> findByStudentNumber(Integer studentNumber);

    List<StudentCandidate> findAll();

    List<StudentCandidate> findByApplicationNumberIn(List<Integer> applicationNumbers);

}
