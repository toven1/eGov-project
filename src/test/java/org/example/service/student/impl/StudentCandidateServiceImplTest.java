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
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;



import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
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
        sc.setApplicationNumber(12345678);
        sc.setApplicationType("수시");
        sc.setFaculty("IT 융합");
        sc.setDepartment("정보보안학과");

        // when
        Long savedId = studentCandidateService.saveCandidate(sc);

        // then
        assertThat(savedId).isEqualTo(sc.getId());
    }

    @Test
    public void 후보자_합격처리_한명만(){
        // given
        StudentCandidate sc = new StudentCandidate();

        sc.setName("test name");
        sc.setRrn("111111-1111111");
        sc.setPhone("010-1234-5678");
        sc.setAddress("서울특별시 무슨구 무슨동");
        sc.setApplicationNumber(12345678);
        sc.setApplicationType("수시");
        sc.setFaculty("IT 융합");
        sc.setDepartment("정보보안학과");
        studentCandidateService.saveCandidate(sc);

        // when
        studentCandidateService.acceptCandidate(sc.getApplicationNumber());
        Optional<StudentCandidate> candidate = studentCandidateService.findOneStudentCandidate(sc.getApplicationNumber());

        // then
        assertThat(candidate.get().isAdmitted()).isEqualTo(true);
    }

    @Test
    public void 후보자_합격처리_다수(){
        // given
        StudentCandidate sc1 = new StudentCandidate();

        sc1.setName("test name");
        sc1.setRrn("111111-1111111");
        sc1.setPhone("010-1234-5678");
        sc1.setAddress("서울특별시 무슨구 무슨동");
        sc1.setApplicationNumber(12345678);
        sc1.setApplicationType("수시");
        sc1.setFaculty("IT 융합");
        sc1.setDepartment("정보보안학과");

        studentCandidateService.saveCandidate(sc1);

        StudentCandidate sc2 = new StudentCandidate();

        sc2.setName("test name");
        sc2.setRrn("111111-2222222");
        sc2.setPhone("010-1234-5678");
        sc2.setAddress("서울특별시 무슨구 무슨동");
        sc2.setApplicationNumber(87654321);
        sc2.setApplicationType("수시");
        sc2.setFaculty("IT 융합");
        sc2.setDepartment("정보보안학과");

        studentCandidateService.saveCandidate(sc2);

        // when
        studentCandidateService.acceptCandidates(List.of(sc1.getApplicationNumber(), sc2.getApplicationNumber()));
        List<StudentCandidate> results = studentCandidateService.findAdmittedStudentCandidates();

        // then
        // 리스트 내의 모둔 객체가 모든 조건을 만족
        assertThat(results).allSatisfy(result -> {
            assertThat(result.isAdmitted()).isEqualTo(true);
        });
    }

    @Test
    public void 후보자_데이터_이름_없음(){

        // given
        StudentCandidate sc = new StudentCandidate();

        sc.setRrn("111111-1111111");
        sc.setPhone("010-1234-5678");
        sc.setAddress("서울특별시 무슨구 무슨동");
        sc.setApplicationNumber(12345678);
        sc.setApplicationType("수시");
        sc.setFaculty("IT 융합");
        sc.setDepartment("정보보안학과");

        // when
        try {
            Long savedId = studentCandidateService.saveCandidate(sc);
            fail("오류 X");
        } catch (Exception e) {
            // then
            assertThat(e.getMessage()).isEqualTo("이름");
        }
    }

    @Test
    public void 후보자_데이터_주민번호_없음(){

        // given
        StudentCandidate sc = new StudentCandidate();

        sc.setName("test name");
        sc.setPhone("010-1234-5678");
        sc.setAddress("서울특별시 무슨구 무슨동");
        sc.setApplicationNumber(12345678);
        sc.setApplicationType("수시");
        sc.setFaculty("IT 융합");
        sc.setDepartment("정보보안학과");

        // when
        try {
            Long savedId = studentCandidateService.saveCandidate(sc);
            fail("오류 X");
        } catch (Exception e) {
            // then
            assertThat(e.getMessage()).isEqualTo("주민번호");
        }
    }

    @Test
    public void 후보자_데이터_전화번호_없음(){

        // given
        StudentCandidate sc = new StudentCandidate();

        sc.setName("test name");
        sc.setRrn("111111-1111111");
        sc.setAddress("서울특별시 무슨구 무슨동");
        sc.setApplicationNumber(12345678);
        sc.setApplicationType("수시");
        sc.setFaculty("IT 융합");
        sc.setDepartment("정보보안학과");

        // when
        try {
            Long savedId = studentCandidateService.saveCandidate(sc);
            fail("오류 X");
        } catch (Exception e) {
            // then
            assertThat(e.getMessage()).isEqualTo("전화번호");
        }
    }

    @Test
    public void 후보자_데이터_주소_없음(){

        // given
        StudentCandidate sc = new StudentCandidate();

        sc.setName("test name");
        sc.setRrn("111111-1111111");
        sc.setPhone("010-1234-5678");
        sc.setApplicationNumber(12345678);
        sc.setApplicationType("수시");
        sc.setFaculty("IT 융합");
        sc.setDepartment("정보보안학과");

        // when
        try {
            Long savedId = studentCandidateService.saveCandidate(sc);
            fail("오류 X");
        } catch (Exception e) {
            // then
            assertThat(e.getMessage()).isEqualTo("주소");
        }
    }

    @Test
    public void 후보자_데이터_지원날짜_없음(){

        // given
        StudentCandidate sc = new StudentCandidate();

        sc.setName("test name");
        sc.setRrn("111111-1111111");
        sc.setPhone("010-1234-5678");
        sc.setAddress("서울특별시 무슨구 무슨동");
        sc.setApplicationNumber(12345678);
        sc.setApplicationType("수시");
        sc.setFaculty("IT 융합");
        sc.setDepartment("정보보안학과");

        // when
//        try {
//            Long savedId = studentCandidateService.saveCandidate(sc);
//            fail("오류 X");
//        } catch (Exception e) {
//            // then
//            assertThat(e.getMessage()).isEqualTo("지원날짜");
//        }
        Long savedId = studentCandidateService.saveCandidate(sc);

        // then
        assertThat(sc.getId()).isEqualTo(savedId);

    }

    @Test
    public void 후보자_데이터_수험번호_없음(){

        // given
        StudentCandidate sc = new StudentCandidate();

        sc.setName("test name");
        sc.setRrn("111111-1111111");
        sc.setPhone("010-1234-5678");
        sc.setAddress("서울특별시 무슨구 무슨동");
        sc.setApplicationType("수시");
        sc.setFaculty("IT 융합");
        sc.setDepartment("정보보안학과");

        // when
        try {
            Long savedId = studentCandidateService.saveCandidate(sc);
            fail("오류 X");
        } catch (Exception e) {
            // then
            assertThat(e.getMessage()).isEqualTo("수험번호");
        }
    }

    @Test
    public void 후보자_데이터_지원유형_없음(){

        // given
        StudentCandidate sc = new StudentCandidate();

        sc.setName("test name");
        sc.setRrn("111111-1111111");
        sc.setPhone("010-1234-5678");
        sc.setAddress("서울특별시 무슨구 무슨동");
        sc.setApplicationNumber(12345678);
        sc.setFaculty("IT 융합");
        sc.setDepartment("정보보안학과");

        // when
        try {
            Long savedId = studentCandidateService.saveCandidate(sc);
            fail("오류 X");
        } catch (Exception e) {
            // then
            assertThat(e.getMessage()).isEqualTo("지원유형");
        }
    }

    @Test
    public void 후보자_데이터_학부_없음(){

        // given
        StudentCandidate sc = new StudentCandidate();

        sc.setName("test name");
        sc.setRrn("111111-1111111");
        sc.setPhone("010-1234-5678");
        sc.setAddress("서울특별시 무슨구 무슨동");
        sc.setApplicationNumber(12345678);
        sc.setApplicationType("수시");
        sc.setDepartment("정보보안학과");

        // when
        try {
            Long savedId = studentCandidateService.saveCandidate(sc);
            fail("오류 X");
        } catch (Exception e) {
            // then
            assertThat(e.getMessage()).isEqualTo("학부");
        }
    }

    @Test
    public void 후보자_데이터_학과_없음(){

        // given
        StudentCandidate sc = new StudentCandidate();

        sc.setName("test name");
        sc.setRrn("111111-1111111");
        sc.setPhone("010-1234-5678");
        sc.setAddress("서울특별시 무슨구 무슨동");
        sc.setApplicationNumber(12345678);
        sc.setApplicationType("수시");
        sc.setFaculty("IT 융합");

        // when
        try {
            Long savedId = studentCandidateService.saveCandidate(sc);
            fail("오류 X");
        } catch (Exception e) {
            // then
            assertThat(e.getMessage()).isEqualTo("학과");
        }
    }

    @Test
    public void 모두조회_조건없음(){

        // given
        StudentCandidate sc1 = new StudentCandidate();

        sc1.setName("test Name");
        sc1.setRrn("111111-1111111");
        sc1.setPhone("010-1234-5678");
        sc1.setAddress("서울특별시 무슨구 무슨동");
        sc1.setApplicationNumber(12345678);
        sc1.setApplicationType("수시");sc1.setFaculty("IT 융합");sc1.setDepartment("정보보안학과");

        StudentCandidate sc2 = new StudentCandidate();

        sc2.setName("test Name");
        sc2.setRrn("111111-2222222");
        sc2.setPhone("010-1234-5678");
        sc2.setAddress("서울특별시 무슨구 무슨동");
        sc2.setApplicationNumber(87654321);
        sc2.setApplicationType("수시");
        sc2.setFaculty("IT 융합");
        sc2.setDepartment("정보보안학과");

        // when
        studentCandidateService.saveCandidate(sc1);
        studentCandidateService.saveCandidate(sc2);
        List<StudentCandidate> result = studentCandidateService.findAllStudentCandidates();

        // then
        assertThat(result.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void 한명찾기_수험번호로(){

        // given
        StudentCandidate sc1 = new StudentCandidate();

        sc1.setName("test Name");
        sc1.setRrn("111111-1111111");
        sc1.setPhone("010-1234-5678");
        sc1.setAddress("서울특별시 무슨구 무슨동");
        sc1.setApplicationNumber(12345678);
        sc1.setApplicationType("수시");sc1.setFaculty("IT 융합");sc1.setDepartment("정보보안학과");

        // when
        studentCandidateService.saveCandidate(sc1);
        StudentCandidate result = studentCandidateService.findOneStudentCandidate(12345678).get();

        // then
        assertThat(result.getId()).isEqualTo(sc1.getId());
    }

    @Test
    public void 모두조회_학부로(){
        // given
        StudentCandidate sc1 = new StudentCandidate();

        sc1.setName("test Name");
        sc1.setRrn("111111-1111111");
        sc1.setPhone("010-1234-5678");
        sc1.setAddress("서울특별시 무슨구 무슨동");
        sc1.setApplicationNumber(12345678);
        sc1.setApplicationType("수시");
        sc1.setFaculty("IT 융합1");
        sc1.setDepartment("정보보안학과");

        StudentCandidate sc2 = new StudentCandidate();

        sc2.setName("test Name");
        sc2.setRrn("111111-2222222");
        sc2.setPhone("010-1234-5678");
        sc2.setAddress("서울특별시 무슨구 무슨동");
        sc2.setApplicationNumber(87654321);
        sc2.setApplicationType("수시");
        sc2.setFaculty("IT 융합2");
        sc2.setDepartment("정보보안학과");

        // when
        studentCandidateService.saveCandidate(sc1);
        studentCandidateService.saveCandidate(sc2);
        List<StudentCandidate> results = studentCandidateService.findStudentCandidatesByFaculty("IT 융합1");

        // then
        // 리스트 내의 모둔 객체가 모든 조건을 만족
        assertThat(results).allSatisfy(result -> {
            assertThat(result.getFaculty()).isEqualTo("IT 융합1");
        });
    }

    @Test
    public void 모두조회_학과로(){
        // given
        StudentCandidate sc1 = new StudentCandidate();

        sc1.setName("test Name");
        sc1.setRrn("111111-1111111");
        sc1.setPhone("010-1234-5678");
        sc1.setAddress("서울특별시 무슨구 무슨동");
        sc1.setApplicationNumber(12345678);
        sc1.setApplicationType("수시");
        sc1.setFaculty("IT 융합");
        sc1.setDepartment("정보보안학과");

        StudentCandidate sc2 = new StudentCandidate();

        sc2.setName("test Name");
        sc2.setRrn("111111-2222222");
        sc2.setPhone("010-1234-5678");
        sc2.setAddress("서울특별시 무슨구 무슨동");
        sc2.setApplicationNumber(87654321);
        sc2.setApplicationType("수시");
        sc2.setFaculty("IT 융합");
        sc2.setDepartment("인공지능학과");

        // when
        studentCandidateService.saveCandidate(sc1);
        studentCandidateService.saveCandidate(sc2);
        List<StudentCandidate> results = studentCandidateService.findStudentCandidatesByDepartment("정보보안학과1");

        // then
        // 리스트 내의 모둔 객체가 모든 조건을 만족
        assertThat(results).allSatisfy(result -> {
            assertThat(result.getDepartment()).isEqualTo("정보보안학과1");
        });
    }

    @Test
    public void 모두조회_유형으로(){
        // given
        StudentCandidate sc1 = new StudentCandidate();

        sc1.setName("test Name");
        sc1.setRrn("111111-1111111");
        sc1.setPhone("010-1234-5678");
        sc1.setAddress("서울특별시 무슨구 무슨동");
        sc1.setApplicationNumber(12345678);
        sc1.setApplicationType("수시");
        sc1.setFaculty("IT 융합");
        sc1.setDepartment("정보보안학과");

        StudentCandidate sc2 = new StudentCandidate();

        sc2.setName("test Name");
        sc2.setRrn("111111-2222222");
        sc2.setPhone("010-1234-5678");
        sc2.setAddress("서울특별시 무슨구 무슨동");
        sc2.setApplicationNumber(87654321);
        sc2.setApplicationType("정시");
        sc2.setFaculty("IT 융합");
        sc2.setDepartment("정보보안학과");

        // when
        studentCandidateService.saveCandidate(sc1);
        studentCandidateService.saveCandidate(sc2);
        List<StudentCandidate> results = studentCandidateService.findStudentCandidatesByApplicationType("수시");

        // then
        // 리스트 내의 모둔 객체가 모든 조건을 만족
        assertThat(results).allSatisfy(result -> {
            assertThat(result.getApplicationType()).isEqualTo("수시");
        });
    }


}