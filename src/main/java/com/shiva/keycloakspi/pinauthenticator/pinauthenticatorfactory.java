package com.shiva.keycloakspi.pinauthenticator;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSessionFactory;

import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import java.util.List;


public class pinauthenticatorfactory implements AuthenticatorFactory {
    private static final String PROVIDER_ID = "pin-val-authenticator";

    @Override
    public String getDisplayType() {
        return "PIN Based Authenticator";
    }

    @Override
    public String getReferenceCategory() {
        return null;
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return new AuthenticationExecutionModel.Requirement[]{
                AuthenticationExecutionModel.Requirement.REQUIRED,
                AuthenticationExecutionModel.Requirement.ALTERNATIVE,
                AuthenticationExecutionModel.Requirement.DISABLED,
                AuthenticationExecutionModel.Requirement.CONDITIONAL
        };
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public String getHelpText() {
        return "Authenticator that validates a user's PIN.";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return ProviderConfigurationBuilder.create().build();
    }

    @Override
    public Authenticator create(KeycloakSession keycloakSession) {
        UserService userService = new UserService(keycloakSession); // Pass the KeycloakSession to UserService
        return new PinAuthenticator(userService);
    }

    @Override
    public void init(Config.Scope scope) {
        // Any initialization if needed
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        // Any post initialization if needed
    }

    @Override
    public void close() {
        // Any cleanup if needed
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}
