package io.github.marceltanuri.commons.oauthclient.api;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * A Metatype interface for configuring OAuth2 clients via OSGi's Configuration Admin.
 * Implementations of this interface define the settings for a single OAuth2 client.
 *
 * @author Marcel Tanuri
 */
@ObjectClassDefinition(name = "OAuth2 Client Settings")
public @interface OAuth2ClientSettings {

	/**
	 * The unique name of the OAuth2 client configuration.
	 * This name is used to identify and retrieve the correct client service.
	 */
	@AttributeDefinition(
		name = "Client Name",
		description = "The unique name of the OAuth2 client configuration."
	)
	String clientName();

	/**
	 * The token endpoint URL of the OAuth2 server.
	 */
	@AttributeDefinition(
		name = "Token Endpoint",
		description = "The token endpoint URL of the OAuth2 server."
	)
	String tokenEndpoint();

	/**
	 * The client ID for the OAuth2 client.
	 */
	@AttributeDefinition(
		name = "Client ID", description = "The client ID for the OAuth2 client."
	)
	String clientId();

	/**
	 * The client secret for the OAuth2 client.
	 */
	@AttributeDefinition(
		name = "Client Secret",
		description = "The client secret for the OAuth2 client."
	)
	String clientSecret();

	/**
	 * The audience for the OAuth2 client.
	 */
	@AttributeDefinition(
		name = "Audience",
		description = "The audience for the OAuth2 client."
	)
	String audience() default "";

	/**
	 * The scope for the OAuth2 client.
	 */
	@AttributeDefinition(
		name = "Scope", description = "The scope for the OAuth2 client."
	)
	String scope() default "";

	/**
	 * The maximum number of entries to keep in the token cache.
	 */
	@AttributeDefinition(
		name = "Cache Max Entries",
		description = "The maximum number of entries to keep in the token cache."
	)
	int cacheMaxEntries() default 100;
}