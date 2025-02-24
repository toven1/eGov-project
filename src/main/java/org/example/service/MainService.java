package org.example.service;

import org.example.entity.student.StudentActive;
import org.example.repository.student.StudentActiveRepository;  // 예시로 StudentRepository를 사용

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MainService {
    private static final Logger logger = LoggerFactory.getLogger(MainService.class);

    @Autowired
    private StudentActiveRepository studentRepository;

    public StudentActive getStudentInfo(int studentNumber) {
       //여기서 연결해주는 과정이 있어야 한다.

    }
}
