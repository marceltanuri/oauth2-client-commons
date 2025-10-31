package io.github.marceltanuri.commons.oauthclient.api;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * @author Marcel Tanuri
 */
@ObjectClassDefinition(name = "OAuth2 Client Settings")
public @interface OAuth2ClientSettings {

	@AttributeDefinition(
		name = "Client Name",
		description = "The unique name of the OAuth2 client configuration."
	)
	String clientName();

	@AttributeDefinition(
		name = "Token Endpoint",
		description = "The token endpoint URL of the OAuth2 server."
	)
	String tokenEndpoint();

	@AttributeDefinition(
		name = "Client ID", description = "The client ID for the OAuth2 client."
	)
	String clientId();

	@AttributeDefinition(
		name = "Client Secret",
		description = "The client secret for the OAuth2 client."
	)
	String clientSecret();

	@AttributeDefinition(
		name = "Audience",
		description = "The audience for the OAuth2 client."
	)
	String audience() default "";

	@AttributeDefinition(
		name = "Scope", description = "The scope for the OAuth2 client."
	)
	String scope() default "";
}