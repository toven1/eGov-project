CREATE TABLE student_active (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    password VARCHAR(128) NOT NULL, -- 비밀번호 (SHA-256 해싱 저장), 추후 보안 업그레이드를 고려하여 크기 측정
    rrn VARCHAR(128) NOT NULL, -- 주민등록번호 (AES-256 암호화 저장)
    phone VARCHAR(15) NOT NULL,
    address VARCHAR(100) NOT NULL,
    student_number INT NOT NULL, -- 학생번호 (학번)
    status ENUM('ENROLLED', 'LEAVE_OF_ABSENCE', 'MILITARY_LEAVE') NOT NULL DEFAULT 'ENROLLED', -- 재학 상태
    admission_type VARCHAR(30) NOT NULL,
    academic_year INT NOT NULL DEFAULT 1, -- 학년 (기본값: 1학년)
    semester INT NOT NULL DEFAULT 1, -- 학기 (기본값: 1학기)
    faculty VARCHAR(30) NOT NULL, -- 학부
    department VARCHAR(30) NOT NULL, -- 학과
    class_group VARCHAR(30) NOT NULL, -- 소속 클래스
    admission_date DATE NOT NULL,

    UNIQUE (student_number), -- 학생 번호는 유일해야함
    UNIQUE (rrn)
);