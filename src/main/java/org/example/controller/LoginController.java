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

// 아마 교수,직원 테이블이 생성되면 변경해야할꺼 같다.

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private StudentActiveService studentService;

    @RequestMapping("/studentLogin")
    public String studentLoginForm(){
        return "studentLogin";
    }


    @PostMapping("/studentlogin")
    public String login(@RequestParam StudentActive studentNumber, @RequestParam String password, Model model){
        boolean isAuthenticated = studentService.login(studentNumber,password);
        if(isAuthenticated){
            logger.info("Studentname: {} Login Successful", studentNumber);
            model.addAttribute("stNumber",studentNumber);
            return "main";
        }else{
            //실패한 로그를 남김 입력한 학번을 알수 있음
            logger.info("Studentname: {} Login Failed", studentNumber);

            //로그인 정보가 일치 않다는걸 이벤트 메세지로 띄움
            model.addAttribute("loginErrorMessage","회원정보가 일치하지 않습니다.");
            return "login";
        }

    }
}
