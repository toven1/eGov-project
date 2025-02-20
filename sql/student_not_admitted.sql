CREATE TABLE student_not_admitted (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    application_number INT UNIQUE NOT NULL,
    application_date DATE NOT NULL,
    application_type VARCHAR(30) NOT NULL,
    faculty VARCHAR(30) NOT NULL,
    department VARCHAR(30) NOT NULL,
    department_code VARCHAR(4) NOT NULL
);