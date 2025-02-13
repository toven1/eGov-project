package org.example.util;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class ResidentRegistrationNumberEncryptor {

    // AES 256 Key (32 bytes) - 환경 변수 관리 권장
    private static final String SECRET_KEY = to32ByteKey("eGov_project"); // 변경 금지
    private static final String ALGORITHM = "AES";
    private static final int IV_SIZE = 16; // 128비트 (AES 블록 크기)

    /**
     * 주민등록번호 암호화 (CBC 모드)
     */
    public static String encryptRrn(String plainRrn) {
        try {
            // IV 생성
            byte[] iv = new byte[IV_SIZE];
            new SecureRandom().nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            // 암호화
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            byte[] encryptedBytes = cipher.doFinal(plainRrn.getBytes());

            // IV + 암호문 결합 후 Base64 인코딩
            byte[] combined = new byte[IV_SIZE + encryptedBytes.length];
            System.arraycopy(iv, 0, combined, 0, IV_SIZE);
            System.arraycopy(encryptedBytes, 0, combined, IV_SIZE, encryptedBytes.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            throw new RuntimeException("RRN encryption failed", e);
        }
    }

    /**
     * 주민등록번호 복호화 (CBC 모드)
     */
    public static String decryptRrn(String encryptedRrn) {
        try {
            // Base64 디코딩 및 IV/암호문 분리
            byte[] combined = Base64.getDecoder().decode(encryptedRrn);
            byte[] iv = new byte[IV_SIZE];
            byte[] cipherText = new byte[combined.length - IV_SIZE];

            System.arraycopy(combined, 0, iv, 0, IV_SIZE);
            System.arraycopy(combined, IV_SIZE, cipherText, 0, cipherText.length);

            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

            return new String(cipher.doFinal(cipherText));
        } catch (Exception e) {
            throw new RuntimeException("RRN decryption failed", e);
        }
    }

    /**
     * 입력된 문자열을 AES 256 비밀키(32바이트)로 변환
     * @param input 사용자가 입력한 문자열
     * @return 32바이트로 변환된 문자열
     */
    public static String to32ByteKey(String input) {
        // 입력 문자열을 바이트 배열로 변환
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);

        // 32바이트 배열 생성
        byte[] keyBytes = new byte[32];

        // 원본 바이트 복사 (32바이트 초과 시 잘림)
        System.arraycopy(bytes, 0, keyBytes, 0, Math.min(bytes.length, keyBytes.length));

        // 바이트 배열을 문자열로 변환
        return new String(keyBytes, StandardCharsets.UTF_8);
    }
}
