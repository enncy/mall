package cn.enncy.mall.utils;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * //TODO
 * <br/>Created in 15:42 2021/11/7
 *
 * @author enncy
 */
public class Security {

    private static final String MD5_KEY = "MALL_MD5_KEY_123456";

    public static String getMd5Content(String str){
        return str + MD5_KEY;
    }

    public static String stringToMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(Security.getMd5Content(str).getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
