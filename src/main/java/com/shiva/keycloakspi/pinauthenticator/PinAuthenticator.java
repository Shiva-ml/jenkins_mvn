package com.shiva.keycloakspi.pinauthenticator;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.sessions.AuthenticationSessionModel;

public class PinAuthenticator implements Authenticator {
    private final UserService userService;
    private static final String FORMNAME = "pin_input.ftl";

    public PinAuthenticator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        context.challenge(context.form().createForm(FORMNAME));
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        String enteredPin = context.getHttpRequest().getDecodedFormParameters().getFirst("pin");
        AuthenticationSessionModel authSession = context.getAuthenticationSession();
        authSession.setAuthNote("pin", enteredPin);

        if (enteredPin == null || enteredPin.trim().isEmpty()) {
            context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS,
                    context.form().setError("PIN cannot be empty. Please enter your PIN.").createForm(FORMNAME));
            return;
        }

        // Fetching the user using UserService from RemoteUserStorageProvider
        UserModel userModel = context.getUser();

        if (userModel == null) {
            context.failureChallenge(AuthenticationFlowError.UNKNOWN_USER,
                    context.form().setError("Unknown user. Please try again.").createForm(FORMNAME));
            return;
        }

        boolean isPinValid = userService.verifyUserPin(userModel.getUsername(), enteredPin);
        if (isPinValid) {
            context.success();
        } else {
            context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS,
                    context.form().setError("Invalid PIN. Please try again.").createForm(FORMNAME));
        }
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
        // No required actions
    }

    @Override
    public void close() {
        // No resources to close
    }
}
