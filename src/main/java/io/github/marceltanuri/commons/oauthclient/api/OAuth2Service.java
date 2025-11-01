package io.github.marceltanuri.commons.oauthclient.api;

/**
 * A service for obtaining OAuth2 access tokens.
 * Implementations of this interface are responsible for handling the client credentials flow
 * and caching tokens.
 *
 * @author Marcel Tanuri
 */
public interface OAuth2Service {

	/**
	 * Retrieves a valid OAuth2 access token.
	 * <p>
	 * This method will first attempt to retrieve a token from the cache. If a valid token
	 * is not available, it will fetch a new one from the configured token endpoint.
	 *
	 * @return A valid access token as a String.
	 */
	public String getAccessToken();

}