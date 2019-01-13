package com.iszhouhua.blog.common.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * PBKDF2加密
 */
public class PBKDF2Utils {
    /**
     * 盐的长度
     */
    private static final int SALT_SIZE = 16;
    /**
     * 生成密文的长度
     */
    private static final int HASH_SIZE = 32;
    /**
     * 迭代次数
     */
    private static final int PBKDF2_ITERATIONS = 1000;

    /**
     * 密码验证
     * @param password 输入的密码
     * @param salt 盐
     * @param key 原密文
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static boolean verify(String password, String salt, String key)
            throws NoSuchAlgorithmException, InvalidKeySpecException, DecoderException {
        // 用相同的盐值对用户输入的密码进行加密
        String result = getPBKDF2(password, salt);
        // 把加密后的密文和原密文进行比较，相同则验证成功，否则失败
        return result.equals(key);
    }

    /**
     * 生成密文
     * @param password 密码
     * @param salt 盐
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static String getPBKDF2(String password, String salt) throws NoSuchAlgorithmException,
            InvalidKeySpecException, DecoderException {
        //将16进制字符串形式的salt转换成byte数组
        byte[] bytes = Hex.decodeHex(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), bytes, PBKDF2_ITERATIONS, HASH_SIZE * 4);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = secretKeyFactory.generateSecret(spec).getEncoded();
        //将byte数组转换为16进制的字符串
        return Hex.encodeHexString(hash);
    }

    /**
     * 生成随机盐
     */
    public static String getSalt() throws NoSuchAlgorithmException{
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] bytes = new byte[SALT_SIZE / 2];
        sr.nextBytes(bytes);
        //将byte数组转换为16进制的字符串
        return Hex.encodeHexString(bytes);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException, DecoderException {
        //随机盐
        String salt=getSalt();
        System.out.println(salt);
        //加密
        System.out.println(getPBKDF2("123456",salt));
    }
}
