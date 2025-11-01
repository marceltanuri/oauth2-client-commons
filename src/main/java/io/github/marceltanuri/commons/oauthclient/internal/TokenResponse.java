package io.github.marceltanuri.commons.oauthclient.internal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the response from an OAuth2 token endpoint.
 *
 * @author Marcel Tanuri
 */
public class TokenResponse {

	/**
	 * Constructs a new {@link TokenResponse} and sets the creation timestamp to the current time.
	 */
	public TokenResponse() {
		_createdAt = System.currentTimeMillis();
	}

	/**
	 * @return The access token.
	 */
	public String getAccessToken() {
		return _accessToken;
	}

	/**
	 * @return The lifetime in seconds of the access token.
	 */
	public long getExpiresIn() {
		return _expiresIn;
	}

	/**
	 * @return The type of token this is, typically "Bearer".
	 */
	public String getTokenType() {
		return _tokenType;
	}

	/**
	 * Checks if the token is still valid, with a 60-second buffer.
	 *
	 * @return {@code true} if the token is valid, otherwise {@code false}.
	 */
	public boolean isValid() {
		long expiryTime = _createdAt + (_expiresIn * 1000) - 60000; 

		if (expiryTime > System.currentTimeMillis()) {
			return true;
		}

		return false;
	}

	public void setAccessToken(String accessToken) {
		_accessToken = accessToken;
	}

	public void setExpiresIn(long expiresIn) {
		_expiresIn = expiresIn;
	}

	public void setTokenType(String tokenType) {
		_tokenType = tokenType;
	}

	@JsonProperty("access_token")
	private String _accessToken;

	private final long _createdAt;

	@JsonProperty("expires_in")
	private long _expiresIn;

	@JsonProperty("token_type")
	private String _tokenType;

}