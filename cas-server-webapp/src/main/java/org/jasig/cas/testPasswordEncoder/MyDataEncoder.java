package org.jasig.cas.testPasswordEncoder;

import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;

/**
 * MyDataEncoder
 *
 * @Description: uuuuuuuuuuuuuuuuu
 * @Author: tretert
 * @Time:1
 */
public class MyDataEncoder extends AbstractUsernamePasswordAuthenticationHandler {
    @Override
    protected boolean authenticateUsernamePasswordInternal(UsernamePasswordCredentials credentials) throws AuthenticationException {


        System.out.println(credentials.getUsername());
        System.out.println(credentials.getPassword());

        if("name123".equals(credentials.getUsername()) && "pwd123".equals(credentials.getPassword())){
            return true;
        }

        return false;
    }
}
