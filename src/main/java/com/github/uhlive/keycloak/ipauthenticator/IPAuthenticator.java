package com.github.uhlive.keycloak.ipauthenticator;

import java.util.List;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public class IPAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(IPAuthenticator.class);

    private static final String IP_ADDRESSES_ATTRIBUTE = "allowed_ips";

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        KeycloakSession session = context.getSession();
        RealmModel realm = context.getRealm();
        UserModel user = context.getUser();

        // TODO: get the attribute value to look for from the authenticator config
        List<String> ipAddresses = user.getAttributeStream(IP_ADDRESSES_ATTRIBUTE).toList();
        for (String ip : ipAddresses) {
            logger.infof("Attribute value %s", ip);
        }
        // TODO: if no address in user attributes => success
        String remoteIPAddress = context.getConnection().getRemoteAddr();
        logger.infof("********** Realm %s, user %s logged from %s", realm.getName(), user.getUsername(), remoteIPAddress);
        if (ipAddresses.contains(remoteIPAddress)) {
            logger.infof("Remote IP %s match", remoteIPAddress);
            context.success();
        }

        logger.info("################# ACCESS DENIED ################");
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
