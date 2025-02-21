package org.example.controller;

import org.example.service.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * 🔹 인증 코드 요청 (POST 방식)
     */
    @PostMapping("/send")
    @ResponseBody
    public String sendVerificationCode(@RequestParam("email") String email) {
        return emailService.sendVerificationEmail(email);
    }

    /**
     * 🔹 인증 코드 검증 (POST 방식)
     */
    @PostMapping("/verify")
    @ResponseBody
    public String verifyCode(@RequestParam("email") String email, @RequestParam("code") String code) {
        boolean isValid = emailService.verifyCode(email, code);
        return isValid ? "인증 성공" : "인증 실패";
    }
}