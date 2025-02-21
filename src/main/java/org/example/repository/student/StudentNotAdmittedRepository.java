package org.example.repository.student;

import org.example.entity.student.StudentNotAdmitted;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentNotAdmittedRepository extends JpaRepository<StudentNotAdmitted, Long> {
    Optional<StudentNotAdmitted> findByApplicationNumber(Integer applicationNumber);

    List<StudentNotAdmitted> findByNameContaining(String name);

    List<StudentNotAdmitted> findByPhoneContaining(String phone);

    List<StudentNotAdmitted> findByApplicationNumberContaining(Integer applicationNumber);

    List<StudentNotAdmitted> findByApplicationTypeContaining(String applicationType);

    List<StudentNotAdmitted> findByFacultyContaining(String faculty);

    List<StudentNotAdmitted> findByDepartmentCodeContaining(String departmentCode);
}
