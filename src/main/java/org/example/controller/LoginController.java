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



    @Autowired
    private final StudentActiveService studentService;

    public LoginController(StudentActiveService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping("/studentLogin")
    public String studentLoginForm(){
        return "studentLogin.jsp";
    }

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

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
            session.setAttribute("number",student); //세션에 로그인한 사용자 정보를 저장
            model.addAttribute("stNumber",studentNumber);

            return "redirect:/main";
        }else{
            //로그인 정보가 일치 않다는걸 이벤트 메세지로 띄움
            model.addAttribute("loginErrorMessage","회원정보가 일치하지 않습니다.");
            return "studentLogin";
        }

    }
}
