package org.example.service.student.impl;

import org.example.controller.LoginController;
import org.example.entity.student.StudentActive;
import org.example.entity.student.StudentCandidate;
import org.example.repository.student.StudentActiveRepository;
import org.example.repository.student.StudentCandidateRepository;
import org.example.service.student.StudentActiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Service("studentActiveService")
@Transactional
public class StudentActiveServiceImpl implements StudentActiveService {

    @Autowired
    private final StudentCandidateRepository studentCandidateRepository;
    @Autowired
    private final StudentActiveRepository studentActiveRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public StudentActiveServiceImpl(StudentCandidateRepository studentCandidateRepository, StudentActiveRepository studentActiveRepository) {
        this.studentCandidateRepository = studentCandidateRepository;
        this.studentActiveRepository = studentActiveRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    @Override
    public boolean login(int number, String password) {

        //입력받은 데이터(아이디, 비밀번호)를 데이터 베이스에서 비교한다.
        boolean isValidStudent = checkStudentCredential(number, password);

        if (isValidStudent) {
            StudentActive student = getStudentbyNumber(number);
            //일치하면 메인으로 이동하고, 세션을 생성하고, 로그을 남긴다.
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            session.setAttribute("studentNumber", student); //세션에 로그인한 사용자의 정보 저장
           //session.setAttribute("isLoggedIn", true);//로그인 상태를 저장
            logger.info("Studentname: {} Login Successful",number); //로그 생성
            return true;
        } else {
            //일치하지 않으면 "회원정보가 일치 하지 않습니다."라고 띄우고 다시 로그인 화면으로 돌아간다
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
           // session.setAttribute("isFalsedLogIn", false); //로그인이 실패한 것도 남긴다.
            //로그인 정보가 일치 않다는걸 이벤트 메세지로 띄움
            logger.info("Studentname: {} Login Failed", number); //실패 로그도 생성
            return false;
        }
    }
    @Override
    public StudentActive getStudentbyNumber(int number)
    {
        return studentActiveRepository.findByStudentNumber(number).orElse(null);
    }

    private boolean checkStudentCredential(int number, String password)
    {
        //데이터 베이스에 접근해 학번이 일치하는 것 사람을 찾는다.
        StudentActive student = studentActiveRepository.findByStudentNumber(number).orElse(null);

        //만약 null 값이 아니면 일치하는 학번이 있는것이므로 비밀번호를 비교해본다.
        if(student != null ){
            if(passwordEncoder.matches(password, student.getPassword())){
                return true;
            }else {
                logger.warn("password doesn't match:{}",number);
                return false;
            }
        }else{
            logger.warn("No student found with number: {}", number);
            return false;
        }

    }


    // 회원가입
    @Override
    public Long signUp(StudentActive studentActive) {

        StudentCandidate candidate = studentCandidateRepository.findByStudentNumber(studentActive.getStudentNumber())
                .orElseThrow(() -> new IllegalArgumentException("해당 학생이 없습니다."));
        studentCandidateRepository.delete(candidate);

        return studentActiveRepository.save(studentActive).getId();
    }
}


