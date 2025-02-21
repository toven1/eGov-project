package org.example.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

@Service
public class EmailService {

    @Resource(name = "redisTemplate")
    private StringRedisTemplate redisTemplate;

    private static final int CODE_EXPIRATION_TIME = 5; // 5분 후 자동 삭제

    /**
     * 📩 이메일 인증 코드 전송
     */
    public String sendVerificationEmail(String email) {
        String verificationCode = generateVerificationCode();

        // Redis에 인증 코드 저장 (5분 후 만료)
        redisTemplate.opsForValue().set(email, verificationCode, CODE_EXPIRATION_TIME, TimeUnit.MINUTES);

        // 이메일 발송 로직 (콘솔 출력)
        System.out.println("인증 코드 [" + verificationCode + "]가 이메일 [" + email + "]로 전송되었습니다.");

        return verificationCode;
    }

    /**
     * ✅ 인증 코드 검증
     */
    public boolean verifyCode(String email, String inputCode) {
        String storedCode = redisTemplate.opsForValue().get(email);
        return storedCode != null && storedCode.equals(inputCode);
    }

    /**
     * 🔢 6자리 랜덤 코드 생성
     */
    private String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(1000000));
    }
}