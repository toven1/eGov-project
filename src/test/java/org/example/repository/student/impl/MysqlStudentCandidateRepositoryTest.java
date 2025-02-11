package org.example.repository.student.impl;

import org.example.config.AppConfig;
import org.example.entity.student.StudentCandidate;
import org.example.repository.student.StudentCandidateRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.Classes;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = AppConfig.class)
public class MysqlStudentCandidateRepositoryTest {

    @Autowired
    private StudentCandidateRepository studentCandidateRepository;

    @Before
    public void setUp() {
        assertThat(studentCandidateRepository).isNotNull(); // Repository가 정상 주입되었는지 확인
    }


    @Test
    public void 데이터넣기테스트(){

        //given
        StudentCandidate sc = new StudentCandidate();
        sc.setName("testname");

        //    sc.setId(1L);
        sc.setRrn("111111-1111111");
        sc.setPhone("010-1234-5678");
        sc.setAddress("서울특별시 무슨구 무슨동");
        sc.setApplicationDate(LocalDate.of(2024, 11, 11));
        sc.setApplicationNumber(12345678);
        sc.setApplicationType("수시");
        sc.setFaculty("IT 융합");
        sc.setDepartment("정보보안");

        //when
        studentCandidateRepository.save(sc);

        //then
        StudentCandidate result = studentCandidateRepository.findById(sc.getId()).get();
        assertThat(sc).isEqualTo(result);
    }
}