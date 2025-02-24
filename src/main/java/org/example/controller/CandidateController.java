package org.example.controller;

import org.example.entity.student.StudentCandidate;
import org.example.entity.student.StudentNotAdmitted;
import org.example.entity.student.TuitionPayment;
import org.example.repository.student.TuitionPaymentRepository;
import org.example.service.student.StudentCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
public class CandidateController {



    @Autowired
    private final StudentCandidateService studentCandidateService;
    @Autowired
    private final TuitionPaymentRepository tuitionPaymentRepository;

    public CandidateController(StudentCandidateService studentCandidateService, TuitionPaymentRepository tuitionPaymentRepository) {
        this.studentCandidateService = studentCandidateService;
        this.tuitionPaymentRepository = tuitionPaymentRepository;
    }

    @GetMapping("/admission/office")
    public String admissionOffice() {
        return "admissionOffice.jsp";
    }


    @GetMapping("/admission/main")
    public String admissionMain(Model model){
        List<StudentCandidate> candidates = studentCandidateService.findNullAdmittedStudentCandidates();
        model.addAttribute("candidates", candidates);
        model.addAttribute("now", "main");
        return "admissionOfficeMain.jsp";
    }
    @GetMapping("/admission/main/pass")
    public String admissionMainPass(Model model){
        List<StudentCandidate> candidates = studentCandidateService.findAdmittedStudentCandidates();
        model.addAttribute("candidates", candidates);
        model.addAttribute("now", "pass");
        return "admissionOfficeMain.jsp";
    }
    @GetMapping("/admission/main/fail")
    public String admissionMainFail(Model model){
        List<StudentCandidate> candidates =
                studentCandidateService.transferNotAdmittedToCandidate(studentCandidateService.findAllNotAdmitted());
        model.addAttribute("candidates", candidates);
        model.addAttribute("now", "fail");
        return "admissionOfficeMain.jsp";
    }
    @PostMapping("/admission/main/payment")
    public String payment(Model model, @RequestParam("applicationNumber") Integer applicationNumber){
        studentCandidateService.findOneStudentCandidate(applicationNumber).flatMap(m ->
                tuitionPaymentRepository.findByStudentNumber(m.getStudentNumber())).ifPresent(n -> {
            n.setYear1Semester1(true);
            tuitionPaymentRepository.save(n);
        });

        return "redirect:/admission/main";
    }


    @GetMapping("/")
    @ResponseBody // 응답 바디에 직접 넣겠다.
    public String helloString() {
        return "hello "; // String 만 반환
    }

    @PostMapping("/admission/office/new")
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

        studentCandidateService.saveCandidate(studentCandidate);
        return "redirect:/admission/office";
    }

    @PostMapping("/admission/main/search")
    public String search(@RequestParam("now") String now, @RequestParam("search-type") String searchType, @RequestParam("search-text") String searchText, Model model){
        List<StudentCandidate> candidates = studentCandidateService.searchCandidates(now, searchType, searchText);
        model.addAttribute("now", now);
        model.addAttribute("candidates", candidates);
        model.addAttribute("searchText", searchText);
        return "admissionOfficeMain.jsp";
    }

    @PostMapping("/admission/main/info")
    public String candidateInfo(@RequestParam("now") String now, @RequestParam("applicationNumber") Integer applicationNumber, Model model) {
        model.addAttribute("now", now);

        if (now.equals("fail")) {
            studentCandidateService.findNotAdmittedOne(applicationNumber).ifPresent(m->{
                List<StudentNotAdmitted> notAdmitted = Collections.singletonList(m);
                List<StudentCandidate> studentCandidates = studentCandidateService.transferNotAdmittedToCandidate(notAdmitted);
                StudentCandidate candidate = studentCandidates.get(0);
                model.addAttribute("candidate", candidate);
            });

        } else {
            StudentCandidate candidate = studentCandidateService.findOneStudentCandidate(applicationNumber)
                    .orElseThrow(() -> new IllegalStateException("지원자를 찾을 수 없습니다."));
            model.addAttribute("candidate", candidate);
        }
        return "admissionOfficeInfo.jsp";
    }

    @PostMapping("/admission/main/admit")
    public String admit(@RequestParam("applicationNumber") Integer applicationNumber, @RequestParam("isAdmitted") Boolean isAdmitted, Model model) {
        StudentCandidate candidate = studentCandidateService.findOneStudentCandidate(applicationNumber)
                .orElseThrow(() -> new IllegalStateException("지원자를 찾을 수 없습니다."));
        if (isAdmitted) {
            studentCandidateService.acceptCandidate(applicationNumber);
        } else {
            studentCandidateService.NotAdmittedCandidate(applicationNumber);
        }

        return "redirect:/admission/main";
    }


}
