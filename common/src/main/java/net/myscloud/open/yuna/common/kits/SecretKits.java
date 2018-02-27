package net.myscloud.open.yuna.common.kits;

import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by genesis on 16/5/12.
 */
@Slf4j
public class SecretKits {

    public static final String HMAC_SHA1 = "HmacSHA1";

    public static final String MD5 = "MD5";

    private final static String[] HEX_DIGITS = {
            "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"
    };

    /**
     * 使用HmacSHA1进行加密(推荐)
     *
     * @param data 需加密数据
     * @param key  加密Key
     * @return
     */
    public static String hmacSha1(final String data, final String key) {
        try {
            byte[] keyBytes = key.getBytes(Charsets.UTF_8);
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1);
            Mac mac = Mac.getInstance(HMAC_SHA1);
            mac.init(signingKey);
            return byteArrayToHexString(mac.doFinal(data.getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }

    /**
     * 使用MD5加密
     *
     * @param data 需加密数据
     * @return
     */
    public static String md5(final String data) {
        try {
            MessageDigest md = MessageDigest.getInstance(MD5);
            return byteArrayToHexString(md.digest((data).getBytes(Charsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }

    public static String sign(final String data, final String key, final String signType) {
        if (SecretKits.MD5.equals(signType)) {
            return SecretKits.md5(data, key);
        } else if (SecretKits.HMAC_SHA1.equals(signType)) {
            return SecretKits.hmacSha1(data, key);
        }
        log.warn("不支持的签名类型, signType is {}", signType);
        return "";
    }

    /**
     * 使用MD5加密
     *
     * @param data 需加密数据
     * @param key  加密Key
     * @return
     */
    public static String md5(final String data, final String key) {
        return md5(data + key);
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte aB : b) {
            sb.append(byteToHexString(aB));
        }
        return sb.toString();
    }

    private static String byteToHexString(byte b) {
        return HEX_DIGITS[(b & 0xf0) >> 4] + HEX_DIGITS[b & 0x0f];
    }
}
