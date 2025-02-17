package org.example.service.student.impl;

import org.example.entity.Department;
import org.example.entity.student.StudentCandidate;
import org.example.entity.student.StudentNumberSequence;
import org.example.entity.student.TuitionPayment;
import org.example.repository.DepartmentRepository;
import org.example.repository.student.StudentCandidateRepository;
import org.example.repository.student.StudentNumberSequenceRepository;
import org.example.repository.student.TuitionPaymentRepository;
import org.example.service.student.StudentCandidateService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.example.util.ResidentRegistrationNumberEncryptor.*;
import static org.junit.Assert.assertTrue;

@Service
@Transactional
public class StudentCandidateServiceImpl implements StudentCandidateService {

    @Autowired
    private final StudentCandidateRepository studentCandidateRepository;
    @Autowired
    private final StudentNumberSequenceRepository studentNumberSequenceRepository;
    @Autowired
    private final DepartmentRepository departmentRepository;
    @Autowired
    private final TuitionPaymentRepository tuitionPaymentRepository;

    @Autowired
    public StudentCandidateServiceImpl(StudentCandidateRepository studentCandidateRepository, StudentNumberSequenceRepository studentNumberSequenceRepository, DepartmentRepository departmentRepository, TuitionPaymentRepository tuitionPaymentRepository) {
        this.studentCandidateRepository = studentCandidateRepository;
        this.studentNumberSequenceRepository = studentNumberSequenceRepository;
        this.departmentRepository = departmentRepository;
        this.tuitionPaymentRepository = tuitionPaymentRepository;
    }

    @Override
    public Long saveCandidate(StudentCandidate studentCandidate) {

        // 필요 데이터 체크
        checkRequiredData(studentCandidate);

        // 주민번호 암호화
        studentCandidate.setRrn(encryptRrn(studentCandidate.getRrn()));

        // 중복 체크
        isDuplicateCandidate(studentCandidate);

        // 입력된 학과 이름을 기반으로 학과 코드 찾기
        Department department = departmentRepository.findByDepartmentName(studentCandidate.getDepartment())
                .orElseThrow(() -> new IllegalArgumentException("학과를 찾을 수 없습니다: " + studentCandidate.getDepartment()));

        // 학과 코드 저장
        studentCandidate.setDepartmentCode(department.getDepartmentCode());

        // 저장
        studentCandidateRepository.save(studentCandidate);

        return studentCandidate.getId();
    }

    // 중복 체크
    // 수험번호 + 주민번호
    @Override
    public void isDuplicateCandidate(@NotNull StudentCandidate studentCandidate) {

        // 주민번호
        Optional<StudentCandidate> rrn = studentCandidateRepository.findByRrn(studentCandidate.getRrn());
        // 수험번호
        Optional<StudentCandidate> applicationNumber = studentCandidateRepository.findByApplicationNumber(studentCandidate.getApplicationNumber());

        // 묶어서 체크
        Stream.of(rrn, applicationNumber).forEach(m -> m.ifPresent(
                n -> {
                    throw new IllegalStateException("이미 존재하는 후보자입니다.");
                }
        ));
    }

    // 필요 데이터 체크
    @Override
    public void checkRequiredData(@NotNull StudentCandidate studentCandidate) {
        if (studentCandidate.getName() == null) throw new IllegalStateException("이름");
        if (studentCandidate.getRrn() == null) throw new IllegalStateException("주민번호");
        if (studentCandidate.getPhone() == null ) throw new IllegalStateException("전화번호");
        if (studentCandidate.getAddress() == null) throw new IllegalStateException("주소");
        if (studentCandidate.getApplicationDate() == null) throw new IllegalStateException("지원날짜");
        if (studentCandidate.getApplicationNumber() == null) throw new IllegalStateException("수험번호");
        if (studentCandidate.getApplicationType() == null ) throw new IllegalStateException("지원유형");
        if (studentCandidate.getFaculty() == null) throw new IllegalStateException("학부");
        if (studentCandidate.getDepartment() == null) throw new IllegalStateException("학과");
    }

    // 후보자 합격처리 다수
    @Override
    public void acceptCandidates(List<Integer> applicationNumbers) {
        List<StudentCandidate> studentCandidates = studentCandidateRepository.findByApplicationNumberIn(applicationNumbers);
        studentCandidates.forEach(studentCandidate -> {
            studentCandidate.setAdmitted(true);

            // 학번 생성
            Integer studentNumber = generateStudentNumber(studentCandidate);

            // 등록금 테이블 생성
            TuitionPayment tuitionPayment = new TuitionPayment();
            tuitionPayment.setStudentNumber(studentNumber);
            tuitionPaymentRepository.save(tuitionPayment);

            // 저장
            studentCandidate.setStudentNumber(studentNumber);
        });
        studentCandidateRepository.saveAll(studentCandidates);
    }

    // 후보자 합격처리 한명만
    @Override
    public void acceptCandidate(Integer applicationNumber) {
        Optional<StudentCandidate> sc = studentCandidateRepository.findByApplicationNumber(applicationNumber);
        // 없으면 실행 X
        sc.ifPresent(studentCandidate->{
            studentCandidate.setAdmitted(true);

            // 학번 생성
            Integer studentNumber = generateStudentNumber(studentCandidate);

            // 등록금 테이블 생성
            TuitionPayment tuitionPayment = new TuitionPayment();
            tuitionPayment.setStudentNumber(studentNumber);
            tuitionPaymentRepository.save(tuitionPayment);

            // 저장
            studentCandidate.setStudentNumber(studentNumber);
            studentCandidateRepository.save(studentCandidate);
                });
    }

    // 학번 생성
    @Transactional
    @Override
    public Integer generateStudentNumber(StudentCandidate studentCandidate) {
        // 지원년도와 학과코드 추출
        int year = studentCandidate.getApplicationDate().getYear();
        String departmentCode = studentCandidate.getDepartmentCode();

        // 기존 시퀀스 조회
        StudentNumberSequence studentNumberSequence = studentNumberSequenceRepository.findByYearAndDepartmentCode(year, departmentCode);
        if (studentNumberSequence == null) {
            // 없으면 0으로 지정
            studentNumberSequence = new StudentNumberSequence();
            studentNumberSequence.setYear(year);
            studentNumberSequence.setDepartmentCode(departmentCode);

            studentNumberSequence.setSequence(0);
        }
        // 1을 더한 후 시퀀스 저장
        studentNumberSequence.setSequence(studentNumberSequence.getSequence()+1);
        studentNumberSequenceRepository.save(studentNumberSequence);

        // 만든 시퀀스를 기반으로 학번 생성
        Integer studentNumber = Integer.parseInt(String.format("%02d%s%04d", year % 100, departmentCode, studentNumberSequence.getSequence()));
        studentCandidate.setStudentNumber(studentNumber);

        // 학번 리턴
        return studentNumber;
    }

    @Override
    public List<StudentCandidate> findAllStudentCandidates() {
        return studentCandidateRepository.findAll();
    }

    @Override
    public Optional<StudentCandidate> findOneStudentCandidate(Integer applicationNumber) {
        return studentCandidateRepository.findByApplicationNumber(applicationNumber);
    }

    @Override
    public List<StudentCandidate> findStudentCandidatesByFaculty(String faculty) {
        return studentCandidateRepository.findByFaculty(faculty);
    }

    @Override
    public List<StudentCandidate> findStudentCandidatesByDepartment(String department) {
        return studentCandidateRepository.findByDepartment(department);
    }

    @Override
    public List<StudentCandidate> findStudentCandidatesByApplicationType(String applicationType) {
        return studentCandidateRepository.findByApplicationType(applicationType);
    }

    @Override
    public List<StudentCandidate> findAdmittedStudentCandidates() {
        return studentCandidateRepository.findByIsAdmitted(true);
    }

    @Override
    public List<StudentCandidate> findNotAdmittedStudentCandidates() {
        return studentCandidateRepository.findByIsAdmitted(false);
    }
}
