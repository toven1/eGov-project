package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // 단방향 해시 암호 설정
    }
}
//BCrypt 알고리즘 : 패스워드를 생성할때 내부적으로 랜덤한 salt를 생성한다.
// salt: 데이터, 비밀번호를 해시 처리하는 단방향 함수의 추가 입력으로 사용되는 랜덤 데이터
// 참고한 사이트: https://bbubbush.tistory.com/36