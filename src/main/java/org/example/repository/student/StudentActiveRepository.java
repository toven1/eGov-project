package org.example.repository.student;



import org.example.entity.student.Status;
import org.example.entity.student.StudentActive;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentActiveRepository {
    StudentActive save(StudentActive studentActive);

    Optional<StudentActive> findById(StudentActive studentActive);

    Optional<StudentActive> findByName(StudentActive studentActive);

    Optional<StudentActive> findByStudent_number(StudentActive studentActive);

    Optional<StudentActive> findByStatus(Status status);

    Optional<StudentActive> findByAcademic_year(int academic_year);

    Optional<StudentActive> findBySemester(int semester);

    Optional<StudentActive> findByFaculty(String faculty);

    Optional<StudentActive> findByDepartment(String department);

    Optional<StudentActive> findByClass_group(String class_group);

    StudentActive findAll();

}
