/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/
 */
package org.jasig.cas;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jasig.cas.authentication.Authentication;
import org.jasig.cas.authentication.ImmutableAuthentication;
import org.jasig.cas.authentication.principal.HttpBasedServiceCredentials;
import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.jasig.cas.authentication.principal.SimpleWebApplicationServiceImpl;
import org.jasig.cas.validation.Assertion;
import org.jasig.cas.validation.ImmutableAssertionImpl;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.validation.BindException;
import org.springframework.webflow.context.servlet.ServletExternalContext;
import org.springframework.webflow.test.MockRequestContext;

/**
 * @author Scott Battaglia
 * @version $Revision: 19842 $ $Date: 2010-01-28 23:13:27 -0500 (Thu, 28 Jan 2010) $
 * @since 3.0.2
 */
public final class TestUtils {

    public static final String CONST_USERNAME = "test";

    private static final String CONST_PASSWORD = "test1";

    private static final String CONST_BAD_URL = "http://www.acs.rutgers.edu";

    private static final String CONST_CREDENTIALS = "credentials";

    private static final String CONST_WEBFLOW_BIND_EXCEPTION = "org.springframework.validation.BindException.credentials";

    private static final String[] CONST_NO_PRINCIPALS = new String[0];

    public static final String CONST_EXCEPTION_EXPECTED = "Exception expected.";

    public static final String CONST_EXCEPTION_NON_EXPECTED = "Exception not expected.";
    
    public static final String CONST_GOOD_URL = "https://wwws.mint.com/";

    private TestUtils() {
        // do not instanciate
    }

    public static UsernamePasswordCredentials getCredentialsWithSameUsernameAndPassword() {
        return getCredentialsWithSameUsernameAndPassword(CONST_USERNAME);
    }

    public static UsernamePasswordCredentials getCredentialsWithSameUsernameAndPassword(
        final String username) {
        return getCredentialsWithDifferentUsernameAndPassword(username,
            username);
    }

    public static UsernamePasswordCredentials getCredentialsWithDifferentUsernameAndPassword() {
        return getCredentialsWithDifferentUsernameAndPassword(CONST_USERNAME,
            CONST_PASSWORD);
    }

    public static UsernamePasswordCredentials getCredentialsWithDifferentUsernameAndPassword(
        final String username, final String password) {
        // noinspection LocalVariableOfConcreteClass
        final UsernamePasswordCredentials usernamePasswordCredentials = new UsernamePasswordCredentials();
        usernamePasswordCredentials.setUsername(username);
        usernamePasswordCredentials.setPassword(password);

        return usernamePasswordCredentials;
    }

    public static HttpBasedServiceCredentials getHttpBasedServiceCredentials() {
        return getHttpBasedServiceCredentials(CONST_GOOD_URL);
    }

    public static HttpBasedServiceCredentials getBadHttpBasedServiceCredentials() {
        return getHttpBasedServiceCredentials(CONST_BAD_URL);
    }

    public static HttpBasedServiceCredentials getHttpBasedServiceCredentials(
        final String url) {
        try {
            return new HttpBasedServiceCredentials(new URL(url));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException();
        }
    }

    public static Principal getPrincipal() {
        return getPrincipal(CONST_USERNAME);
    }

    public static Principal getPrincipal(final String name) {
        return new SimplePrincipal(name);
    }

    public static Service getService() {
        return getService(CONST_USERNAME);
    }

    public static Service getService(final String name) {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("service", name);
        return SimpleWebApplicationServiceImpl.createServiceFrom(request);
    }

    public static Authentication getAuthentication() {
        return new ImmutableAuthentication(getPrincipal());
    }

    public static Authentication getAuthenticationWithService() {
        return new ImmutableAuthentication(getService());
    }

    public static Authentication getAuthentication(final String name) {
        return new ImmutableAuthentication(getPrincipal(name));
    }

    public static Assertion getAssertion(final boolean fromNewLogin) {
        return getAssertion(fromNewLogin, CONST_NO_PRINCIPALS);
    }

    public static Assertion getAssertion(final boolean fromNewLogin,
        final String[] extraPrincipals) {
        final List<Authentication> list = new ArrayList<Authentication>();
        list.add(TestUtils.getAuthentication());

        for (int i = 0; i < extraPrincipals.length; i++) {
            list.add(TestUtils.getAuthentication(extraPrincipals[i]));
        }
        return new ImmutableAssertionImpl(list, TestUtils.getService(),
            fromNewLogin);
    }

    public static MockRequestContext getContext() {
        return getContext(new MockHttpServletRequest());
    }

    public static MockRequestContext getContext(
        final MockHttpServletRequest request) {
        return getContext(request, new MockHttpServletResponse());
    }

    public static MockRequestContext getContext(
        final MockHttpServletRequest request,
        final MockHttpServletResponse response) {
        final MockRequestContext context = new MockRequestContext();
        context.setExternalContext(new ServletExternalContext(new MockServletContext(), request, response));
        return context;
    }

    public static MockRequestContext getContextWithCredentials(
        final MockHttpServletRequest request) {
        return getContextWithCredentials(request, new MockHttpServletResponse());
    }

    public static MockRequestContext getContextWithCredentials(
        final MockHttpServletRequest request,
        final MockHttpServletResponse response) {
        final MockRequestContext context = getContext(request, response);
        context.getRequestScope().put(CONST_CREDENTIALS, TestUtils
            .getCredentialsWithSameUsernameAndPassword());
        context.getRequestScope().put(CONST_WEBFLOW_BIND_EXCEPTION,
                new BindException(TestUtils
                    .getCredentialsWithSameUsernameAndPassword(),
                    CONST_CREDENTIALS));

        return context;
    }
}
