# Keycloak IP authenticator

Keycloak Authenticator that checks if the user is coming from a trusted network
or not. This authenticator does the check if the user or group has an attribute
`allowed_ips`. If so, the user is authenticated only if its IP is contained in
the allow list.

The format supported for the allowed IP follows those from
[`IPAddressString`](https://seancfoley.github.io/IPAddress/IPAddress/apidocs/inet/ipaddr/IPAddressString.html).
For example:

- 1.*.2-3.4
- 1.2.3.4/255.255.0.0

## Build

The Keycloak SPI is very stable but always make sure that Keycloak SPI
dependencies and your Keycloak server versions match. Keycloak SPI dependencies
version is configured in `pom.xml` in the `keycloak.version` property.

To build the project execute the following command:

```bash
mvn package
```

## Deploy

Assuming `$KEYCLOAK_HOME` is pointing to you Keycloak installation.

Copy the artifact to the Keycloak `providers` directory:

```bash
cp target/keycloak-ip-authenticator.jar $KEYCLOAK_HOME/providers/
```
