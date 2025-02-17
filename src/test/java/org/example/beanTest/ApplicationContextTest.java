package org.example.beanTest;

import org.example.config.AppConfig;
import org.example.repository.student.StudentCandidateRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = AppConfig.class)
public class ApplicationContextTest {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private StudentCandidateRepository studentCandidateRepository;

    @Test
    public void testRepositoryInstance() {
        assertTrue(studentCandidateRepository instanceof JpaRepository);
    }

    @Test
    public void printAllBeans() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println("Loaded Bean: " + beanName);
        }
    }
}
