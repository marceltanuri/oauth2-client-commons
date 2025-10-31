package io.github.marceltanuri.commons.oauthclient.internal;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.metatype.annotations.Designate;

import io.github.marceltanuri.commons.oauthclient.api.*;

/**
 * @author Marcel Tanuri
 */
@Component(
	configurationPid = "io.github.marceltanuri.commons.oauthclient.api.OAuth2ClientSettings",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	service = OAuth2Service.class,
	property = {
		"clientName={clientName}"
	}
)
@Designate(ocd = OAuth2ClientSettings.class, factory = true)
public class OAuth2ClientManager implements OAuth2Service {

	@Activate
	public void activate(OAuth2ClientSettings settings) {
		_settings = settings;
		_service = new OAuth2ClientService(settings, new OAuth2TokenCache());
	}

	@Override
	public String getAccessToken() {
		return _service.getAccessToken();
	}

	private OAuth2ClientSettings _settings;
	private OAuth2Service _service;
}