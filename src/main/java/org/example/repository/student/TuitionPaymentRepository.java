package org.example.repository.student;


import org.example.entity.student.TuitionPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TuitionPaymentRepository extends JpaRepository<TuitionPayment, Long> {
    Optional<TuitionPayment> findByStudentNumber(Integer studentNumber);
}
