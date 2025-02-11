package org.example.repository.student.impl;

import org.example.entity.student.StudentCandidate;
import org.example.repository.student.StudentCandidateRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MysqlStudentCandidateRepository extends JpaRepository<StudentCandidate, Long>, StudentCandidateRepository {

  /// 12122

}
