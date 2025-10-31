package io.github.marceltanuri.commons.oauthclient.internal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Marcel Tanuri
 */
public class TokenResponse {

	public TokenResponse() {
		_createdAt = System.currentTimeMillis();
	}

	public String getAccessToken() {
		return _accessToken;
	}

	public long getExpiresIn() {
		return _expiresIn;
	}

	public String getTokenType() {
		return _tokenType;
	}

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