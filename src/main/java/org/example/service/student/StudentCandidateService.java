package org.example.service.student;


import org.example.entity.student.StudentActive;
import org.example.entity.student.StudentCandidate;
import org.example.entity.student.StudentNotAdmitted;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StudentCandidateService {

    // 수험생 저장
    Long saveCandidate(StudentCandidate studentCandidate);
    // 수험 번호로 중복 체크
    void isDuplicateCandidate(StudentCandidate studentCandidate);
    // 필요한 데이터 체크
    void checkRequiredData(StudentCandidate studentCandidate);
    // 지원자 다수 합격 처리
    void acceptCandidates(List<Integer> applicationNumbers);
    // 지원자 한명 합격 처리
    void acceptCandidate(Integer applicationNumber);
    // 학번 생성
    @Transactional
    Integer generateStudentNumber(StudentCandidate studentCandidate);
    // Active 객체로 변환
    @Transactional
    StudentActive transferCandidateToActive(Integer studentNumber);

    @Transactional
    List<StudentCandidate> transferNotAdmittedToCandidate(List<StudentNotAdmitted> notAdmittedList);

    // 불합격 처리
    void NotAdmittedCandidate(Integer applicationNumber);


    // 지원자 모두 찾기
    List<StudentCandidate> findAllStudentCandidates();
    // 지원자 한명 조회 ( 수험번호로 조회 )
    Optional<StudentCandidate> findOneStudentCandidate(Integer applicationNumber);

    // 이름으로 찾기
    List<StudentCandidate> findStudentCandidatesByName(String name);// 이름으로 찾기
    // 전화번호로 찾기
    List<StudentCandidate> findStudentCandidatesByPhone(String phone);
    // 학부로 지원자 조회
    List<StudentCandidate> findStudentCandidatesByFaculty(String faculty);
    // 학과로 지원자 조회
    List<StudentCandidate> findStudentCandidatesByDepartment(String department);
    // 지원유형으로 지원자 조회
    List<StudentCandidate> findStudentCandidatesByApplicationType(String applicationType);
    // 합격한 지원자 조회
    List<StudentCandidate> findAdmittedStudentCandidates();
    // 불합격
    List<StudentCandidate> findNotAdmittedStudentCandidates();
    // 아직 X
    List<StudentCandidate> findNullAdmittedStudentCandidates();

    List<StudentCandidate> searchCandidates(String now, String searchType, String searchText);

    List<StudentNotAdmitted> findAllNotAdmitted();
}
