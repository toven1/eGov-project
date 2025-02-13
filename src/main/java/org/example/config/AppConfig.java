package org.example.config;

import org.example.repository.student.StudentCandidateRepository;
import org.example.repository.student.impl.MysqlStudentCandidateRepository;
import org.example.service.student.StudentActiveService;
import org.example.service.student.StudentCandidateService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import java.util.Map;

@Configuration
@ImportResource(locations = "classpath:applicationContext.xml")
@ComponentScan("org.example")
@EnableJpaRepositories("org.example")
public class AppConfig {



    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaPropertyMap(Map.of("hibernate.hbm2ddl.auto", "validate"));
        return factoryBean;
    }
}
