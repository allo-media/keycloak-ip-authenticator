package com.github.uhlive.keycloak.ipauthenticator;

import java.util.List;

public class IPChecker {

    public boolean is_allowed(List<String> ipAddresses, String remoteIPAddress) {
        if (ipAddresses.isEmpty()) {
            return true;
        }
        if (ipAddresses.getFirst().equals(remoteIPAddress)) {
            return true;
        }
        return false;
    }
}
