package io.github.marceltanuri.commons.oauthclient.internal;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A simple in-memory cache for storing and retrieving {@link TokenResponse} objects.
 * This cache has a maximum size and uses a LRU (Least Recently Used) eviction policy.
 *
 * @author Marcel Tanuri
 */
public class OAuth2TokenCache {

	private static final int DEFAULT_MAX_ENTRIES = 100;

	private final Map<String, TokenResponse> _cache;

	private OAuth2TokenCache(int maxEntries) {
		_cache = Collections.synchronizedMap(new LinkedHashMap<String, TokenResponse>(maxEntries, 0.75f, true) {
			@Override
			protected boolean removeEldestEntry(Map.Entry<String, TokenResponse> eldest) {
				return size() > maxEntries;
			}
		});
	}

	/**
     * Creates a new instance of the cache with the default max size.
     * * <p>The default size is defined by the constant 
     * {@link #DEFAULT_MAX_ENTRIES}.</p>
     *
     * @return A new instance of {@link OAuth2TokenCache}.
     * @see #DEFAULT_MAX_ENTRIES
     */
    public static OAuth2TokenCache ofDefault() {
        return new OAuth2TokenCache(DEFAULT_MAX_ENTRIES);
    }

	/**
     * Creates a new instance of the cache with the given max size.
     *
     * @param maxEntries The maximum number of entries to keep in the cache.
     * @return A new instance of {@link OAuth2TokenCache}.
     */
    public static OAuth2TokenCache of(int maxEntries) {
        return new OAuth2TokenCache(maxEntries);
    }

	/**
	 * Retrieves a token from the cache.
	 *
	 * @param key The cache key.
	 * @return The {@link TokenResponse} if a valid token is found, otherwise {@code null}.
	 */
	public TokenResponse get(String key) {
		TokenResponse token = _cache.get(key);

		if ((token != null) && token.isValid()) {
			return token;
		}

		return null;
	}

	/**
	 * Adds a token to the cache.
	 *
	 * @param key The cache key.
	 * @param token The {@link TokenResponse} to cache.
	 */
	public void put(String key, TokenResponse token) {
		_cache.put(key, token);
	}

}