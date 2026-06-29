package com.github.uhlive.keycloak.ipauthenticator;

import java.util.Collections;
import java.util.Map;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.*;

public class IPAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(IPAuthenticator.class);

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        KeycloakSession session = context.getSession();
        RealmModel realm = context.getRealm();
        UserModel user = context.getUser();

        String remoteIPAddress = context.getConnection().getRemoteAddr();

        logger.infof("Nothing for now. Realm %s, user %s logged from %s", realm.getName(), user.getUsername(), remoteIPAddress);

        context.success();
    }

    @Override
    public void action(AuthenticationFlowContext context) {
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
    }

    @Override
    public void close() {
    }

}
