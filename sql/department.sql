CREATE TABLE department (
    department_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL,
    department_code CHAR(2) NOT NULL UNIQUE
);


-- 여기 있는 이름이랑 정확히 일치 해야합니다.
INSERT INTO department (department_name, department_code) VALUES
('정보보안학과', '01'),
('컴퓨터공학과', '02'),
('인공지능학과', '03');