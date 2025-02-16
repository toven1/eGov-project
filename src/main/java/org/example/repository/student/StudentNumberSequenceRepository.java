package org.example.repository.student;

import org.apache.ibatis.annotations.Param;
import org.example.entity.student.StudentNumberSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentNumberSequenceRepository extends JpaRepository<StudentNumberSequence, Long> {
    StudentNumberSequence findByYearAndDepartmentCode(int year, String departmentCode);
}