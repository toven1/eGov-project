package org.example.service.student.impl;

import org.example.config.AppConfig;
import org.example.entity.student.StudentCandidate;
import org.example.repository.student.StudentCandidateRepository;
import org.example.service.student.StudentCandidateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = AppConfig.class)
public class StudentCandidateServiceImplTest {

    @Autowired
    StudentCandidateRepository studentCandidateRepository;
    @Autowired
    StudentCandidateService studentCandidateService;

    @Before
    public void setUp() {
       assertThat(studentCandidateRepository).isNotNull();// Repository가 정상 주입되었는지 확인
       assertThat(studentCandidateService).isNotNull(); // service 가 정상 주입되었는지 확인
    }

    @Test
    public void 정상_후보자_데이터_입력_테스트(){
        // given
        StudentCandidate sc = new StudentCandidate();

        sc.setName("test Name");
        sc.setRrn("111111-1111111");
        sc.setPhone("010-1234-5678");
        sc.setAddress("서울특별시 무슨구 무슨동");
        sc.setApplicationDate(LocalDate.of(2024, 11, 11));
        sc.setApplicationNumber(12345678);
        sc.setApplicationType("수시");
        sc.setFaculty("IT 융합");
        sc.setDepartment("정보보안");

        // when
        Long savedId = studentCandidateService.saveCandidate(sc);

        // then
        System.out.println("sc.getRrn() = " + sc.getRrn());
        assertThat(savedId).isEqualTo(sc.getId());

    }

}