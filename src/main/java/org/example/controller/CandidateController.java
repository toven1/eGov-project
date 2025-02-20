package org.example.controller;

import org.example.entity.student.StudentCandidate;
import org.example.repository.student.StudentCandidateRepository;
import org.example.service.student.StudentCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class CandidateController {



    @Autowired
    private final StudentCandidateService studentCandidateService;
    @Autowired
    private StudentCandidateRepository studentCandidateRepository;

    public CandidateController(StudentCandidateService studentCandidateService) {
        this.studentCandidateService = studentCandidateService;
    }

    @GetMapping("/admission-office")
    public String admissionOffice() {
        return "admissionOffice.jsp";
    }

    @GetMapping("/")
    @ResponseBody // 응답 바디에 직접 넣겠다.
    public String helloString() {
        return "hello "; // String 만 반환
    }

    @PostMapping("/admission-office/new")
    public String create(CandidateForm form) {
        StudentCandidate studentCandidate = new StudentCandidate();
        studentCandidate.setName(form.getName());
        studentCandidate.setRrn(form.getRrn());
        studentCandidate.setPhone(form.getPhone());
        studentCandidate.setAddress(form.getAddress());
        studentCandidate.setApplicationNumber(form.getApplicationNumber());
        studentCandidate.setApplicationType(form.getApplicationType());
        studentCandidate.setFaculty(form.getFaculty());
        studentCandidate.setDepartment(form.getDepartment());

        studentCandidateRepository.save(studentCandidate);
        return "redirect:/admission-office";
    }
}
