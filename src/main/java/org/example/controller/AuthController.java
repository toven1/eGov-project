package org.example.controller;

import org.example.repository.student.TuitionPaymentRepository;
import org.example.service.student.StudentActiveService;
import org.example.service.student.StudentCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.example.util.ResidentRegistrationNumberEncryptor.encryptRrn;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private final StudentCandidateService studentCandidateService;
    @Autowired
    private final StudentActiveService studentActiveService;
    @Autowired
    private final TuitionPaymentRepository tuitionPaymentRepository;

    public AuthController(StudentCandidateService studentCandidateService, StudentActiveService studentActiveService, TuitionPaymentRepository tuitionPaymentRepository) {
        this.studentCandidateService = studentCandidateService;
        this.studentActiveService = studentActiveService;
        this.tuitionPaymentRepository = tuitionPaymentRepository;
    }

    @GetMapping("/verify")
    public String showVerificationPage() {
        return "verify.jsp";  // 본인인증 페이지
    }


    @PostMapping("/verify")
    public String verifyUser(Model model, CandidateForm form, HttpServletRequest request) {
        HttpSession session = request.getSession();
        return studentCandidateService.findStudentCandidateByStudentNumber(form.getStudentNumber())
            .map(studentCandidate -> {
                if (studentCandidate.getName().equals(form.getName()) &&
                    studentCandidate.getRrn().equals(encryptRrn(form.getRrn())) &&
                    studentCandidate.getPhone().equals(form.getPhone())) {

                    if (tuitionPaymentRepository.findByStudentNumberAndYear1Semester1True(form.getStudentNumber()).isPresent()) {
                        session.invalidate(); // 기존 세션 삭제 (세션 고정 방지)
                        HttpSession newSession = request.getSession(true); // 새로운 세션 생성
                        newSession.setAttribute("verified", true);
                        newSession.setAttribute("studentNumber", form.getStudentNumber());
                        newSession.setMaxInactiveInterval(300); // 세션 5분 후 만료 설정

                        return "redirect:/sign-up";
                    }
                    model.addAttribute("error", "등록금이 미납되었습니다.");
                    return "verify.jsp";
                }
                model.addAttribute("error", "정보가 맞지 않습니다.");
                return "verify.jsp";
            })
            .orElseGet(() ->{
                model.addAttribute("error", "정보가 맞지 않습니다.");
                return "verify.jsp";
            });
    }


}
