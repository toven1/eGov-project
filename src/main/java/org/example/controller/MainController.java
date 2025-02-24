package org.example.controller;

import org.example.entity.student.StudentActive;
import org.example.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@Controller
public class MainController {

    @Autowired
    private MainService mainService;

    @GetMapping("/main")
    public String showMainPage(@RequestParam(value = "stNumber", required = false) Integer stNumber,
                               HttpSession session, Model model) {
        Integer studentNumber = (Integer) session.getAttribute("number");

        if (studentNumber == null && stNumber != null && stNumber != 0) {
            studentNumber = stNumber;
            session.setAttribute("number", studentNumber);  // 세션에 학생 번호 저장
        }

        if (studentNumber == null) {
            return "redirect:/login";  // 로그인 페이지로 리다이렉트
        }

        //예외를 만들어야 한다..
        try {
            StudentActive student = mainService.getStudentInfo(studentNumber);

            if (student == null) {
                model.addAttribute("StudentSearchError", "학생 정보를 찾을 수 없습니다.");
                return "redirect:/studentLogin";  // 로그인 페이지로 리다이렉트
            }

            model.addAttribute("student", student);
            return "main.jsp";  // main.jsp 페이지로 이동
        } catch (Exception e) {
            model.addAttribute("error", "서버에서 문제가 발생했습니다: " + e.getMessage());
            return "errorPage";  // 에러 페이지로 이동
        }
    }
}
