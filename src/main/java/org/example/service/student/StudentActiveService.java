package org.example.service.student;

import org.example.entity.student.StudentActive;

public interface StudentActiveService {
    //교수, 직원 로그인까지 생각해서 login으로 만들었다.
    boolean login(int number, String password) ;
    StudentActive getStudentbyNumber(int number);
    // 회원가입
    Long signUp(StudentActive studentActive);
}
