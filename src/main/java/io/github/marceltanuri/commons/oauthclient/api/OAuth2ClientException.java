package io.github.marceltanuri.commons.oauthclient.api;

/**
 * Custom exception for the OAuth2 client.
 * <p>
 * This exception is thrown for any errors that occur during the OAuth2 token retrieval process.
 *
 * @author Marcel Tanuri
 */
public class OAuth2ClientException extends RuntimeException {

	public OAuth2ClientException(String message) {
		super(message);
	}

	public OAuth2ClientException(String message, Throwable cause) {
		super(message, cause);
	}

}