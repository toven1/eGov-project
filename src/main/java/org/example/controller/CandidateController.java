package org.example.controller;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@Controller
public class CandidateController {

    @Autowired
    private ApplicationContext applicationContext;
    
    @GetMapping("/admission-office")
    public String admissionOffice(Model model) {
        model.addAttribute("test", "test");
        System.out.println("Test");

        return "admissionOffice";
    }

    @GetMapping("/")
    @ResponseBody // 응답 바디에 직접 넣겠다.
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // String 만 반환
    }

}
