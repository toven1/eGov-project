package org.example.repository.student;



import org.example.entity.student.Status;
import org.example.entity.student.StudentActive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentActiveRepository extends JpaRepository<StudentActive, Long> {
    StudentActive save(StudentActive studentActive);

    Optional<StudentActive> findById(StudentActive studentActive);

    Optional<StudentActive> findByName(StudentActive studentActive);

    Optional<StudentActive> findByStudentNumber(int studentNumber);

    Optional<StudentActive> findByStatus(Status status);

    Optional<StudentActive> findByAcademicYear(int academic_year);

    Optional<StudentActive> findBySemester(int semester);

    Optional<StudentActive> findByFaculty(String faculty);

    Optional<StudentActive> findByDepartment(String department);

    Optional<StudentActive> findByClassGroup(String class_group);

    List<StudentActive> findAll();

}
