package org.example.service.student.impl;

import org.example.entity.student.StudentActive;
import org.example.entity.student.StudentCandidate;
import org.example.repository.student.StudentActiveRepository;
import org.example.repository.student.StudentCandidateRepository;
import org.example.service.student.StudentActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

//@Service
//@Transactional
//public class StudentActiveServiceImpl implements StudentActiveService {
//
//    @Autowired
//    private final StudentCandidateRepository studentCandidateRepository;
//    @Autowired
//    private final StudentActiveRepository studentActiveRepository;
//
//    public StudentActiveServiceImpl(StudentCandidateRepository studentCandidateRepository, StudentActiveRepository studentActiveRepository) {
//        this.studentCandidateRepository = studentCandidateRepository;
//        this.studentActiveRepository = studentActiveRepository;
//    }
//
//    @Override
//    public Long signUp(StudentActive studentActive) {
//
//        StudentCandidate candidate = studentCandidateRepository.findByStudentNumber(studentActive.getStudentNumber())
//                .orElseThrow(() -> new IllegalArgumentException("해당 학생이 없습니다."));
//        studentCandidateRepository.delete(candidate);
//
//        return studentActiveRepository.save(studentActive).getId();
//    }
//
//

@Service
public class StudentActiveServiceImpl implements StudentActiveService {

    private StudentActiveRepository studentActiveRepository;

    @Override
    public boolean login(StudentActive number, String password) {
        //입력받은 데이터(아이디, 비밀번호)를 데이터 베이스에서 비교한다.
        boolean isValidStudent = checkStudentCredential(number, password);
        if (isValidStudent) {
            //일치하면 메인으로 이동하고, 세션을 생성하고, 로그을 남긴다.
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            session.setAttribute("student", number); //세션에 로그인한 사용지의 정보 저장
            session.setAttribute("isLoggedIn", true);//로그인 상태를 저장
            //로그인 되었다는것을 이벤트 메세지로 띄우고 싶다.
            return true;
        } else {
            //일치하지 않으면 "회원정보가 일치 하지 않습니다."라고 띄우고 다시 로그인 화면으로 돌아간다
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            session.setAttribute("student", number); //세션에 로그인한 사용지의 정보 저장
            session.setAttribute("isFalsedLogIn", false); //로그인이 실패한 것도 남긴다.
            //로그인 정보가 일치 않다는걸 이벤트 메세지로 띄우고 싶다.

            return false;
        }
    }

    private boolean checkStudentCredential(StudentActive number, String password)
    {
        //데이터 베이스에 접근해 학번이 일치하는 것 사람을 찾는다.
        StudentActive student = studentActiveRepository.findByStudentNumber(number).orElse(null);

        //만약 null 값이 아니면 일치하는 학번이 있는것이므로 비밀번호를 비교해본다.
        if(student != null && student.getPassword().equals(password)){
            return true;
        }else
            return false;
    }
}


