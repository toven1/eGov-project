CREATE TABLE student_number_sequence (
    id INT AUTO_INCREMENT PRIMARY KEY,
    year INT NOT NULL,
    department_code CHAR(2) NOT NULL,
    sequence INT NOT NULL,
    UNIQUE KEY unique_year_department (year, department_code)
);