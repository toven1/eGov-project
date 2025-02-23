package org.example.controller;

import org.example.entity.student.StudentActive;
import org.example.entity.student.StudentCandidate;
import org.example.repository.student.StudentActiveRepository;
import org.example.service.student.impl.StudentActiveServiceImpl;
import org.example.service.student.impl.StudentCandidateServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/sign-up")
public class SignUpController {

    private final StudentActiveServiceImpl studentActiveService;
    private final StudentCandidateServiceImpl studentCandidateService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final StudentActiveRepository studentActiveRepository;

    public SignUpController(StudentActiveServiceImpl studentActiveService, StudentCandidateServiceImpl studentCandidateService, BCryptPasswordEncoder bCryptPasswordEncoder, StudentActiveRepository studentActiveRepository) {
        this.studentActiveService = studentActiveService;
        this.studentCandidateService = studentCandidateService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.studentActiveRepository = studentActiveRepository;
    }

    @GetMapping
    public String showSignupPage(HttpSession session, Model model) {
        Boolean isVerified = (Boolean) session.getAttribute("verified");
        if (isVerified == null || !isVerified) {
            return "redirect:/auth/verify";
        }
        Integer studentNumber = (Integer) session.getAttribute("studentNumber");
        model.addAttribute("studentNumber", studentNumber);
        return "studentSignUp.jsp";  // 회원가입 페이지
    }

    // 비밀번호 패턴 정의 (정규식)
    private static final String PASSWORD_REGEX =
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>?]).{8,}$";

    @PostMapping
    public String handleSignUp(@RequestParam("studentNumber") Integer studentNumber, @RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword, HttpSession session, Model model) {
        model.addAttribute("studentNumber", studentNumber);

        // 비밀번호 확인 필수 (일치 여부 체크)
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "studentSignUp.jsp"; //  다시 회원가입 페이지로
        }

        // 비밀번호 정책 검증
        if (!Pattern.matches(PASSWORD_REGEX, password)) {
            model.addAttribute("error", "비밀번호는 8자 이상이며, 대소문자, 숫자, 특수문자를 포함해야 합니다.");
            return "studentSignUp.jsp"; //  비밀번호 정책 위반 → 회원가입 페이지로 이동
        }

        StudentCandidate studentCandidate = new StudentCandidate();


        // 회원가입 진행
        StudentActive studentActive = studentCandidateService.transferCandidateToActive(studentNumber);

        String rawPassword = password;
        studentActive.setPassword(bCryptPasswordEncoder.encode(rawPassword));

        studentActiveService.signUp(studentActive);

        session.invalidate();
        return "redirect:/studentLogin"; //  회원가입 성공 → 로그인 페이지로 이동
    }
}
