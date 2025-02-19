package org.example.util;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class ResidentRegistrationNumberEncryptor {

    // AES 256 Key (32 bytes) - 환경 변수 관리 권장
    private static final String SECRET_KEY = to32ByteKey("eGov_project"); // 변경 금지
    private static final String ALGORITHM = "AES";
    private static final int IV_SIZE = 16; // 128비트 (AES 블록 크기)

    /**
     * 주민등록번호 암호화 (항상 같은 값)
     */
    public static String encryptRrn(String plainRrn) {
        try {
            // IV를 입력값(plainRrn) 기반으로 생성
            byte[] iv = generateFixedIV(plainRrn);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            // 암호화
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            byte[] encryptedBytes = cipher.doFinal(plainRrn.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("RRN encryption failed", e);
        }
    }

    /**
     * 주민등록번호 복호화
     */
    public static String decryptRrn(String encryptedRrn, String plainRrn) {
        try {
            // 암호화 시 사용한 IV를 동일하게 생성
            byte[] iv = generateFixedIV(plainRrn);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

            byte[] decodedBytes = Base64.getDecoder().decode(encryptedRrn);
            return new String(cipher.doFinal(decodedBytes), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("RRN decryption failed", e);
        }
    }

    /**
     * 입력값에서 16바이트 IV 생성 (SHA-256 해시 활용)
     */
    private static byte[] generateFixedIV(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
        byte[] iv = new byte[IV_SIZE];
        System.arraycopy(hash, 0, iv, 0, IV_SIZE);
        return iv;
    }

    /**
     * 입력된 문자열을 AES 256 비밀키(32바이트)로 변환
     */
    public static String to32ByteKey(String input) {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        byte[] keyBytes = new byte[32];
        System.arraycopy(bytes, 0, keyBytes, 0, Math.min(bytes.length, keyBytes.length));
        return new String(keyBytes, StandardCharsets.UTF_8);
    }

}