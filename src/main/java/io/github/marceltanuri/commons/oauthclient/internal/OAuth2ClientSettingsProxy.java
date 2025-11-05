package io.github.marceltanuri.commons.oauthclient.internal;

import io.github.marceltanuri.commons.oauthclient.api.OAuth2ClientSettings;

import java.lang.annotation.Annotation;

public class OAuth2ClientSettingsProxy implements OAuth2ClientSettings {

    private static final String ENV_VAR_PREFIX = normalizeForEnvVar(OAuth2ClientSettings.class.getPackage().getName());

    private final OAuth2ClientSettings _settings;

    public OAuth2ClientSettingsProxy(OAuth2ClientSettings settings) {
        _settings = settings;
    }

    @Override
    public String clientName() {
        return _settings.clientName();
    }

    @Override
    public String tokenEndpoint() {
        return _settings.tokenEndpoint();
    }

    @Override
    public String clientId() {
        String clientName = _settings.clientName();
        String normalizedClientName = normalizeForEnvVar(clientName);
        String envVarName = ENV_VAR_PREFIX + "_" + normalizedClientName + "_CLIENT_ID";
        String envValue = System.getenv(envVarName);

        if (envValue != null) {
            return envValue;
        }

        return _settings.clientId();
    }

    @Override
    public String clientSecret() {
        String clientName = _settings.clientName();
        String normalizedClientName = normalizeForEnvVar(clientName);
        String envVarName = ENV_VAR_PREFIX + "_" + normalizedClientName + "_CLIENT_SECRET";
        String envValue = System.getenv(envVarName);

        if (envValue != null) {
            return envValue;
        }

        return _settings.clientSecret();
    }

    @Override
    public String audience() {
        return _settings.audience();
    }

    @Override
    public String scope() {
        return _settings.scope();
    }

    @Override
    public int cacheMaxEntries() {
        return _settings.cacheMaxEntries();
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return _settings.annotationType();
    }

    private static String normalizeForEnvVar(String name) {
        if (name == null) {
            return "";
        }
        return name.toUpperCase().replaceAll("[^A-Z0-9_]+", "_");
    }
}
