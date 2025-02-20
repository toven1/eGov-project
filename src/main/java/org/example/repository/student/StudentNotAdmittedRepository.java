package org.example.repository.student;

import org.example.entity.student.StudentNotAdmitted;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentNotAdmittedRepository extends JpaRepository<StudentNotAdmitted, Long> {
    Optional<StudentNotAdmitted> findByApplicationNumber(Integer applicationNumber);
}
