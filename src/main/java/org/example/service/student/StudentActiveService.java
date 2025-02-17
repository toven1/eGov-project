package org.example.service.student;

import org.example.entity.student.StudentActive;

public interface StudentActiveService {
    //교수, 직원 로그인까지 생각해서 login으로 만들었다.
    public boolean login(StudentActive number, String password) ;

}
