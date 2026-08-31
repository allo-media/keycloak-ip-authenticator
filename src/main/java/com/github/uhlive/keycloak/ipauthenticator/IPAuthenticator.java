package com.github.uhlive.keycloak.ipauthenticator;

import java.util.Collection;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.KeycloakModelUtils;

public class IPAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(IPAuthenticator.class);

    private static final String IP_ADDRESSES_ATTRIBUTE = "allowed_ips";

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        UserModel user = context.getUser();
        String username = user.getUsername();
        String remoteIPAddress = context.getConnection().getRemoteAddr();

        // Get allowed IP adresses from user and group attributes
        Collection<String> ipAddresses = KeycloakModelUtils.resolveAttribute(user, IP_ADDRESSES_ATTRIBUTE, true);
        if (ipAddresses.isEmpty()) {
            // No IP address restriction
            logger.debugf("No IP address restriction setup for user=%s, remoteIP=%s", username, remoteIPAddress);
            context.success();
            return;
        }

        ipAddresses.forEach(ip -> logger.debugf("allowedIP=%s", ip));
        IPChecker ipChecker = new IPChecker(ipAddresses);
        if (ipChecker.isAllowed(remoteIPAddress)) {
            logger.infof("Access granted for user=%s, remoteIP=%s is allowed", username, remoteIPAddress);
            context.success();
            return;
        }

        logger.infof("Access denied for user=%s, remoteIP=%s is not allowed", username, remoteIPAddress);
        context.failure(AuthenticationFlowError.ACCESS_DENIED);
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
