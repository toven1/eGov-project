package org.example.service.student.impl;

import java.util.stream.Collectors;
import org.example.entity.Department;
import org.example.entity.student.*;
import org.example.repository.DepartmentRepository;
import org.example.repository.student.*;
import org.example.service.student.StudentCandidateService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.example.util.ResidentRegistrationNumberEncryptor.*;
import static org.junit.Assert.assertTrue;

@Service("studentCandidateService")
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
    private final StudentActiveRepository studentActiveRepository;
    @Autowired
    private final StudentNotAdmittedRepository studentNotAdmittedRepository;
    @Autowired
    private final ApplicationNumberRepository applicationNumberRepository;

    @Autowired
    public StudentCandidateServiceImpl(StudentCandidateRepository studentCandidateRepository, StudentNumberSequenceRepository studentNumberSequenceRepository, DepartmentRepository departmentRepository, TuitionPaymentRepository tuitionPaymentRepository, StudentActiveRepository studentActiveRepository, StudentNotAdmittedRepository studentNotAdmittedRepository, ApplicationNumberRepository applicationNumberRepository) {
        this.studentCandidateRepository = studentCandidateRepository;
        this.studentNumberSequenceRepository = studentNumberSequenceRepository;
        this.departmentRepository = departmentRepository;
        this.tuitionPaymentRepository = tuitionPaymentRepository;
        this.studentActiveRepository = studentActiveRepository;
        this.studentNotAdmittedRepository = studentNotAdmittedRepository;
        this.applicationNumberRepository = applicationNumberRepository;
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
        ApplicationNumber applicationNumber = new ApplicationNumber(studentCandidate.getApplicationNumber());
        applicationNumberRepository.save(applicationNumber);


        return studentCandidate.getId();
    }

    // 중복 체크
    // 수험번호 + 주민번호
    @Override
    public void isDuplicateCandidate(@NotNull StudentCandidate studentCandidate) {

        // 주민번호
        Optional<StudentCandidate> rrn = studentCandidateRepository.findByRrn(studentCandidate.getRrn());
        // 수험번호
        Optional<ApplicationNumber> applicationNumber = applicationNumberRepository.findByApplicationNumber(studentCandidate.getApplicationNumber());

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
    //    if (studentCandidate.getApplicationDateTime() == null) throw new IllegalStateException("지원날짜");
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
            if (studentCandidate.isAdmitted() != null) {
                throw new IllegalStateException("적절하지 못한 접근");
            }

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
            if (studentCandidate.isAdmitted() != null) {
                throw new IllegalStateException("적절하지 못한 접근");
            }

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
        int year = studentCandidate.getApplicationDateTime().getYear();
        String departmentCode = studentCandidate.getDepartmentCode();

        // 기존 시퀀스 조회
        StudentNumberSequence studentNumberSequence =
                studentNumberSequenceRepository.findByYearAndDepartmentCode(year, departmentCode);
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
        Integer studentNumber = Integer.parseInt(String.format("%02d%s%04d",
                year % 100, departmentCode, studentNumberSequence.getSequence()));
        studentCandidate.setStudentNumber(studentNumber);

        // 학번 리턴
        return studentNumber;
    }

    // 회원가입
    @Override
    public StudentActive transferCandidateToActive(Integer studentNumber) {

        StudentCandidate studentCandidate = studentCandidateRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found"));

        // 등록금 납부 확인
        boolean isTuitionPaid = tuitionPaymentRepository
                .findByStudentNumberAndYear1Semester1True(studentCandidate.getStudentNumber())
                .isPresent();
        if (!isTuitionPaid) {
            throw new IllegalStateException("등록금 미납");
        }

        // `StudentActive` 객체 생성 반환
        return new StudentActive(studentCandidate);
    }

    @Override
    public List<StudentCandidate> transferNotAdmittedToCandidate(List<StudentNotAdmitted> notAdmittedList) {
        return notAdmittedList.stream().map(notAdmitted -> {
            StudentCandidate candidate = new StudentCandidate();

            // 필요한 필드 매핑
            candidate.setId(notAdmitted.getId());
            candidate.setName(notAdmitted.getName());
            candidate.setPhone(notAdmitted.getPhone());
            candidate.setApplicationNumber(notAdmitted.getApplicationNumber());
            candidate.setApplicationDateTime(notAdmitted.getApplicationDate());
            candidate.setApplicationType(notAdmitted.getApplicationType());
            candidate.setFaculty(notAdmitted.getFaculty());
            candidate.setDepartment(notAdmitted.getDepartment());
            candidate.setDepartmentCode(notAdmitted.getDepartmentCode());

            // isAdmitted 필드가 없을 수도 있으므로 기본값 false 설정 (예시)
            candidate.setAdmitted(false);

            return candidate;
        }).collect(Collectors.toList());
    }


    // 불합격 처리
    @Override
    public void NotAdmittedCandidate(Integer applicationNumber) {
        StudentCandidate studentCandidate = studentCandidateRepository.findByApplicationNumber(applicationNumber)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found"));

        if(studentCandidate.isAdmitted() != null) {
            throw new IllegalStateException("적절하지 못한 접근");
        }
        studentCandidate.setAdmitted(false);
        StudentNotAdmitted studentNotAdmitted = new StudentNotAdmitted(studentCandidate);

        studentNotAdmittedRepository.save(studentNotAdmitted);
        studentCandidateRepository.delete(studentCandidate);

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
    public Optional<StudentCandidate> findStudentCandidateByStudentNumber(Integer studentNumber) {
        return studentCandidateRepository.findByStudentNumber(studentNumber);
    }


    @Override
    public List<StudentCandidate> findStudentCandidatesByName(String name) {
        return studentCandidateRepository.findByName(name);
    }



    @Override
    public List<StudentCandidate> findStudentCandidatesByPhone(String phone) {
        return studentCandidateRepository.findByPhone(phone);
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
    public Optional<StudentNotAdmitted> findNotAdmittedOne(Integer applicationNumber) {
        return studentNotAdmittedRepository.findByApplicationNumber(applicationNumber);
    }

    @Override
    public List<StudentCandidate> findNullAdmittedStudentCandidates() {
        return studentCandidateRepository.findByIsAdmittedIsNull();
    }

    @Override
    public List<StudentCandidate> searchCandidates(String now, String searchType, String searchText) {
        List<StudentCandidate> candidates = new ArrayList<>();
        switch (now) {
            case "main":
                switch (searchType) {
                    case "name":
                        candidates = studentCandidateRepository.findByNameContainingAndIsAdmittedIsNull(searchText);
                        break;
                    case "phone":
                        candidates = studentCandidateRepository.findByPhoneContainingAndIsAdmittedNull(searchText);
                        break;
                    case "applicationNumber":
                        candidates = studentCandidateRepository.findByApplicationNumberContainingAndIsAdmittedIsNull(Integer.parseInt(searchText));
                        break;
                    case "applicationType":
                        candidates = studentCandidateRepository.findByApplicationTypeContainingAndIsAdmittedIsNull(searchText);
                        break;
                    case "faculty":
                        candidates = studentCandidateRepository.findByFacultyContainingAndIsAdmittedIsNull(searchText);
                        break;
                    case "department":
                        candidates = studentCandidateRepository.findByDepartmentContainingAndIsAdmittedIsNull(searchText);
                        break;
                    default:
                        break;
                }
                break;
            case "pass":
                switch (searchType) {
                    case "name":
                        candidates = studentCandidateRepository.findByNameContainingAndIsAdmittedIsTrue(searchText);
                        break;
                    case "phone":
                        candidates = studentCandidateRepository.findByPhoneContainingAndIsAdmittedTrue(searchText);
                        break;
                    case "applicationNumber":
                        candidates = studentCandidateRepository.findByApplicationNumberContainingAndIsAdmittedIsTrue(Integer.parseInt(searchText));
                        break;
                    case "applicationType":
                        candidates = studentCandidateRepository.findByApplicationTypeContainingAndIsAdmittedIsTrue(searchText);
                        break;
                    case "faculty":
                        candidates = studentCandidateRepository.findByFacultyContainingAndIsAdmittedIsTrue(searchText);
                        break;
                    case "department":
                        candidates = studentCandidateRepository.findByDepartmentContainingAndIsAdmittedIsTrue(searchText);
                        break;
                    default:
                        break;
                }
                break;
            case "fail":
                switch (searchType) {
                    case "name":
                        candidates = transferNotAdmittedToCandidate(studentNotAdmittedRepository.findByNameContaining(searchText));
                        break;
                    case "phone":
                        candidates = transferNotAdmittedToCandidate(studentNotAdmittedRepository.findByPhoneContaining(searchText));
                        break;
                    case "applicationNumber":
                        candidates = transferNotAdmittedToCandidate(studentNotAdmittedRepository.findByApplicationNumberContaining(Integer.parseInt(searchText)));
                        break;
                    case "applicationType":
                        candidates = transferNotAdmittedToCandidate(studentNotAdmittedRepository.findByApplicationTypeContaining(searchText));
                        break;
                    case "faculty":
                        candidates = transferNotAdmittedToCandidate(studentNotAdmittedRepository.findByFacultyContaining(searchText));
                        break;
                    case "department":
                        candidates = transferNotAdmittedToCandidate(studentNotAdmittedRepository.findByDepartmentCodeContaining(searchText));
                        break;
                    default:
                        break;
                }
                break;
        }
        return candidates;
    }

    @Override
    public List<StudentNotAdmitted> findAllNotAdmitted() {
        return studentNotAdmittedRepository.findAll();
    }
}
