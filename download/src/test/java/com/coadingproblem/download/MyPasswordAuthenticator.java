package com.coadingproblem.download;

import org.apache.sshd.server.PasswordAuthenticator;
import org.apache.sshd.server.session.ServerSession;

/**
 * Basic PasswordAuthenticator used for unit tests.
 */
public class MyPasswordAuthenticator implements PasswordAuthenticator {

    public boolean authenticate(String username, String password, ServerSession session) {
        boolean retour = false;

        if ("login".equals(username) && "testPassword".equals(password)) {
            retour = true;
        }

        return retour;
    }
}