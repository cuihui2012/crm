/**
 * 
 */
package com.neuedu.crm.utils;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author cuihui
 *
 */
public class Md5Util {
    
    /**
     * 加密算法
     */
    private static String algorithmName = "md5";
    /**
     * 加密算法迭代加密次数
     */
    private static int hashIterations = 2;
    
    /**
     * 
     * 描述：对信息进行MD5盐值加密
     * @author cuihui
     * @version 1.0
     * @param message
     * @param salt
     * @return String
     * @exception Nothing
     * @since 1.8
     *
     */
    public static String encrypt(String message, String salt){
        SimpleHash hash = new SimpleHash(algorithmName, message, salt, hashIterations);
        return hash.toHex();
    }

    public static void main(String[] args) {
        //如果用户名为空，则要重新进行查询注入，因为加密需要用到相应的用户名
        String account = "cuihui";
        String passwd = "since2012";
        //设置盐值（用户名 + 密码）
        String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
        System.out.println("salt----------------->" + salt2);
        //使用Md5Util进行Md5盐值加密
        String encodedPassword = Md5Util.encrypt(passwd, account + salt2);
        //加密后的用户密码
        System.out.println("加密密码----------->" + encodedPassword);
    }
}
