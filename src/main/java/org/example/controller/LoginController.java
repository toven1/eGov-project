package org.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.entity.student.StudentActive;
import org.example.service.student.StudentActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

// 아마 교수,직원 테이블이 생성되면 변경해야할꺼 같다.

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private final StudentActiveService studentService;

    public LoginController(StudentActiveService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping("/studentLogin")
    public String studentLoginForm(){
        return "studentLogin";
    }


    @PostMapping("/studentLogin")
    public String login(@RequestParam String studentNumber,
                        @RequestParam String password,
                        HttpSession session,
                        Model model){
        int studentNumberInt = Integer.parseInt(studentNumber);
        StudentActive student = studentService.getStudentbyNumber(studentNumberInt);

        boolean isAuthenticated = studentService.login(studentNumberInt,password);

        //학번이 틀렸을 경우
        if(student==null){
            model.addAttribute("loginErrorMessage","존재하지 않는 정보입니다.");
            logger.info("WrongStudentNumber Insert Sentence: {}", studentNumber); //로그 생성
            return "studentLogin";
        }

        if(isAuthenticated){
            logger.info("Studentname: {} Login Successful", studentNumber); //로그 생성
            session.setAttribute("loginStudent",student); //세션에 로그인한 사용자 정보를 저장

            model.addAttribute("stNumber",studentNumber);

            return "redirect:/main";
        }else{
            //실패한 로그를 남김 입력한 학번을 알수 있음
            logger.info("Studentname: {} Login Failed", studentNumber); //실패 로그도 생성

            //로그인 정보가 일치 않다는걸 이벤트 메세지로 띄움
            model.addAttribute("loginErrorMessage","회원정보가 일치하지 않습니다.");
            return "studentLogin";
        }

    }
}
