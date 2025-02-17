package org.example.service.student;

import org.example.entity.student.StudentCandidate;

public interface StudentActiveService {

    // 학생 회원가입
    // 후보자 객체를 넣어주면 active 된 학생으로 변환해서 active 데이터베이스에 저장 후 active 학생을 반환
    Long signUp(StudentCandidate studentCandidate);

    // 필요한 데이터 체크 ( 1학년 1학기 등록금 납입 여부, 학번 존재 여부 )
    void checkRequiredData(StudentCandidate studentCandidate);
    // 중복체크
    // active 학생으로 변환
    //
}
