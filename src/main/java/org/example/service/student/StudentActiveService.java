package org.example.service.student;

import org.example.entity.student.StudentActive;
import org.example.entity.student.StudentCandidate;
import org.springframework.stereotype.Service;

public interface StudentActiveService {

    // 회원가입
    Long signUp(StudentActive studentActive);
}
