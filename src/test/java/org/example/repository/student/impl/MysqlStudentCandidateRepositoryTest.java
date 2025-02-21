package org.example.repository.student.impl;

import org.example.config.AppConfig;
import org.example.entity.student.StudentCandidate;
import org.example.repository.student.StudentCandidateRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class MysqlStudentCandidateRepositoryTest {

    @Autowired
    private StudentCandidateRepository studentCandidateRepository;

    @Test
    public void 데이터넣기테스트(){

        // given
        StudentCandidate sc = new StudentCandidate();
        sc.setName("testname");

        //    sc.setId(1L);
        sc.setRrn("999999-9999999");
        sc.setPhone("010-1234-5678");
        sc.setAddress("서울특별시 무슨구 무슨동");
        sc.setApplicationNumber(12345678);
        sc.setApplicationType("수시");
        sc.setFaculty("IT 융합");
        sc.setDepartment("정보보안학과");

        // when
        studentCandidateRepository.save(sc);

        // then
        StudentCandidate result = studentCandidateRepository.findById(sc.getId()).get();
        assertThat(sc).isEqualTo(result);
    }
}