package com.github.uhlive.keycloak.ipauthenticator;

import java.util.List;

import inet.ipaddr.IPAddressString;

public class IPChecker {

    public boolean is_allowed(List<String> ipAddresses, String remoteIPAddress) {
        if (ipAddresses.isEmpty()) {
            // No restriction
            return true;
        }

        IPAddressString remoteIP = new IPAddressString(remoteIPAddress);
        return ipAddresses
                .stream()
                .map(ipAddress -> new IPAddressString(ipAddress))
                .anyMatch(ip -> ip.contains(remoteIP));
    }
}
