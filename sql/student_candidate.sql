CREATE TABLE student_candidate (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    rrn VARCHAR(128) NOT NULL, -- 주민등록번호 (암호화 저장)
    phone VARCHAR(15) NOT NULL,
    address VARCHAR(100) NOT NULL,
    application_number INT NOT NULL, -- 수험번호
    application_date DATE NOT NULL, -- 지원날짜
    application_type VARCHAR(30) NOT NULL, -- 지원 유형
    faculty VARCHAR(30) NOT NULL, -- 학부
    department VARCHAR(30) NOT NULL, -- 학과
    is_admitted BOOLEAN NULL DEFAULT FALSE, -- 합격 여부 (기본값: false)
    student_number INT NULL, -- 합격 전에는 NULL, 합격 후 값이 들어감

    UNIQUE (application_number), -- 수험번호는 유일해야 함
    UNIQUE (student_number) -- 학생번호도 유일해야 함 (합격 후 생성됨)
);