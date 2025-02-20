package org.example.repository.student;

import org.example.entity.student.ApplicationNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationNumberRepository extends JpaRepository<ApplicationNumber, Long> {
    Optional<ApplicationNumber> findByApplicationNumber(Integer applicaionNumber);
}
