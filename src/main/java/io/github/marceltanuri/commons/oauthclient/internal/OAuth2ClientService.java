package io.github.marceltanuri.commons.oauthclient.internal;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.charset.StandardCharsets;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.marceltanuri.commons.oauthclient.api.*;


/**
 * @author Marcel Tanuri
 */
public class OAuth2ClientService implements OAuth2Service {

	public OAuth2ClientService(OAuth2ClientSettings settings, OAuth2TokenCache cache) {
		_settings = settings;
		_cache = cache;

		_httpClient = HttpClient.newHttpClient();
	}

	@Override
	public String getAccessToken() {
		String scope = _settings.scope();

		String cacheKey = _settings.clientId() + ":" + scope;

		TokenResponse token = _cache.get(cacheKey);

		if (token != null) {
			return token.getAccessToken();
		}

		token = _fetchAccessToken();

		_cache.put(cacheKey, token);

		return token.getAccessToken();
	}

	private TokenResponse _fetchAccessToken() {
		Map<String, String> formData = new HashMap<>();

		formData.put("client_id", _settings.clientId());
		formData.put("client_secret", _settings.clientSecret());
		formData.put("grant_type", "client_credentials");

		String scope = _settings.scope();
		if (scope != null && !scope.isBlank()) {
			formData.put("scope", scope);
		}

		String audience = _settings.audience();
		if (audience != null && !audience.isBlank()) {
			formData.put("audience", audience);
		}

		String form = formData.entrySet(
		).stream(
		).map(
			e -> e.getKey() + "=" + e.getValue()
		).collect(
			Collectors.joining("&")
		);

		HttpRequest httpRequest = HttpRequest.newBuilder(
		).uri(
			URI.create(_settings.tokenEndpoint())
		).header(
			"Content-Type", "application/x-www-form-urlencoded"
		).POST(
			HttpRequest.BodyPublishers.ofString(form, StandardCharsets.UTF_8)
		).build();

		try {
			HttpResponse<String> response = _httpClient.send(
				httpRequest, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 200) {
				return _objectMapper.readValue(
					response.body(), TokenResponse.class);
			}

			throw new OAuth2ClientException(
				"Failed to retrieve access token. Status code: " +
					response.statusCode());
		}
		catch (InterruptedException | IOException exception) {
			throw new OAuth2ClientException(
				"Error fetching access token", exception);
		}
	}

	private final OAuth2TokenCache _cache;
	private final HttpClient _httpClient;
	private final ObjectMapper _objectMapper = new ObjectMapper();
	private final OAuth2ClientSettings _settings;

}