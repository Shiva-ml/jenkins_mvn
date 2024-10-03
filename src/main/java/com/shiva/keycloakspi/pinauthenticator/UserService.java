package com.shiva.keycloakspi.pinauthenticator;

import org.jboss.logging.Logger;
import org.keycloak.models.KeycloakSession;
import org.keycloak.broker.provider.util.SimpleHttp;

import jakarta.ws.rs.core.Response;

public class UserService {

    private final KeycloakSession session;
    private static final Logger LOGGER = Logger.getLogger(UserService.class);

    public UserService(KeycloakSession session) {
        this.session = session;
    }

    public boolean verifyUserPin(String username, String pin) {
        boolean verified = false;
        try {
            SimpleHttp.Response response = SimpleHttp.doPost("http://localhost:8081/users/" + username + "/verify-pin", session)
                    .param("pin", pin)  // Add form parameter
                    .header("Content-Type", "application/x-www-form-urlencoded")  // Set the content type to form-urlencoded
                    .asResponse();  // Send the request and get the response

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                verified = true;
            }
        } catch (Exception e) {
            LOGGER.error("Error verifying user pin", e);
        }
        return verified;
    }
}