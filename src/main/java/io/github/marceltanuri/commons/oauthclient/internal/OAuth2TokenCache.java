package io.github.marceltanuri.commons.oauthclient.internal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Marcel Tanuri
 */
public class OAuth2TokenCache {

	public TokenResponse get(String key) {
		TokenResponse token = _cache.get(key);

		if ((token != null) && token.isValid()) {
			return token;
		}

		return null;
	}

	public void put(String key, TokenResponse token) {
		_cache.put(key, token);
	}

	private final Map<String, TokenResponse> _cache = new ConcurrentHashMap<>();

}