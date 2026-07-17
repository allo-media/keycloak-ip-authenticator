package com.github.uhlive.keycloak.ipauthenticator;

import java.util.List;

import inet.ipaddr.IPAddressString;

public class IPChecker {
    private List<String> allowedIPs;

    public IPChecker(List<String> allowedIPs) {
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
