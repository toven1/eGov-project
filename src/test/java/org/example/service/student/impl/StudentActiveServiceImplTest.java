package org.example.service.student.impl;

import junit.framework.TestCase;
import org.example.config.AppConfig;
import org.example.entity.student.StudentActive;
import org.example.entity.student.StudentCandidate;
import org.example.entity.student.TuitionPayment;
import org.example.repository.student.TuitionPaymentRepository;
import org.example.service.student.StudentActiveService;
import org.example.service.student.StudentCandidateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = AppConfig.class)
public class StudentActiveServiceImplTest extends TestCase {

    @Autowired
    StudentCandidateService studentCandidateService;
    @Autowired
    StudentActiveService studentActiveService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    TuitionPaymentRepository tuitionPaymentRepository;

    @Test
    public void 회원가입() {
        // given
        StudentCandidate studentCandidate = new StudentCandidate();

        studentCandidate.setName("test Name");
        studentCandidate.setRrn("111111-1111111");
        studentCandidate.setPhone("010-1234-5678");
        studentCandidate.setAddress("서울특별시 무슨구 무슨동");
        studentCandidate.setApplicationNumber(12345678);
        studentCandidate.setApplicationType("수시");
        studentCandidate.setFaculty("IT 융합");
        studentCandidate.setDepartment("정보보안학과");

        // when
        // 후보자 저장
        Long candidateSavedId = studentCandidateService.saveCandidate(studentCandidate);
        // 후보자 합격 처리
        studentCandidateService.acceptCandidate(studentCandidate.getApplicationNumber());
        // 등록금 납부
        Optional<TuitionPayment> tuitionPayment = tuitionPaymentRepository.findByStudentNumber(studentCandidate.getStudentNumber());
        tuitionPayment.ifPresent(m-> m.setYear1Semester1(true));

        // studentActive 객체 생성
        StudentActive studentActive = studentCandidateService.transferCandidateToActive(studentCandidate.getStudentNumber());
        // 비밀번호 설정
        String rawPassword = "12345678";
        studentActive.setPassword(bCryptPasswordEncoder.encode(rawPassword));

        // 회원가입 / 입학
        Long activeSavedId = studentActiveService.signUp(studentActive);

        // then
        assertThat(candidateSavedId).isEqualTo(studentCandidate.getId());
        assertThat(activeSavedId).isEqualTo(studentActive.getId());
    }
}