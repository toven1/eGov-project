package org.example.config;

import org.example.repository.student.StudentCandidateRepository;
import org.example.repository.student.impl.MysqlStudentCandidateRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ImportResource(locations = "classpath:applicationContext.xml")
@ComponentScan("org.example")
@EnableJpaRepositories("org.example")
public class AppConfig {

}
