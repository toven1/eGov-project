package org.example.controller;

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
            model.addAttribute("stNumber",studentNumber);
            return "main";
        }else{
            return "login";
        }

    }
}
