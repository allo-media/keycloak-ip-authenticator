package com.github.uhlive.keycloak.ipauthenticator;

import java.util.Collection;

import inet.ipaddr.IPAddressString;

public class IPChecker {
    private Collection<String> allowedIPs;

    public IPChecker(Collection<String> allowedIPs) {
        this.allowedIPs = allowedIPs;
    }

    public boolean isAllowed(String remoteIPAddress) {
        if (this.allowedIPs.isEmpty()) {
            // No restriction
            return true;
        }

        IPAddressString remoteIP = new IPAddressString(remoteIPAddress);
        return this.allowedIPs
                .stream()
                .map(ipAddress -> new IPAddressString(ipAddress))
                .anyMatch(ip -> ip.contains(remoteIP));
    }
}
