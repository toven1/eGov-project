package org.example.service.student;


import org.example.entity.student.StudentCandidate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface StudentCandidateService {


    Long saveCandidate(StudentCandidate studentCandidate);

    // 수험 번호로 중복 체크
    void isDuplicateCandidate(StudentCandidate studentCandidate);

    // 필요한 데이터 체크
    void checkRequiredData(StudentCandidate studentCandidate);

    List<StudentCandidate> findAllStudentCandidates();

    Optional<StudentCandidate> findOneStudentCandidate(int applicationNumber);

    List<StudentCandidate> findStudentCandidatesByFaculty(String faculty);
    List<StudentCandidate> findStudentCandidatesByDepartment(String department);
    List<StudentCandidate> findStudentCandidatesByApplicationType(String applicationType);
    List<StudentCandidate> findAdmittedStudentCandidates();
    List<StudentCandidate> findNotAdmittedStudentCandidates();



}
