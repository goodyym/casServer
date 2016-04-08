package org.jasig.cas.testPasswordEncoder;

import org.jasig.cas.authentication.handler.PasswordEncoder;

/**
 * Md5pwd
 *
 * @Description: uuuuuuuuuuuuuuuuu
 * @Author: tretert
 * @Time:1
 */
public class Md5pwd implements PasswordEncoder {
    public String encode(String password) {

        return MD5.GetMD5Code(password);
    }
}
