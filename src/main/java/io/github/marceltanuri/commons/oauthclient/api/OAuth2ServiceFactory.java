package io.github.marceltanuri.commons.oauthclient.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

@Component(service = OAuth2ServiceFactory.class)
public class OAuth2ServiceFactory {

	public OAuth2Service getOAuth2Service(String clientName) {
		return _services.get(clientName);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		target = "(clientName=*)"
	)
	protected void addOAuth2Service(OAuth2Service service, Map<String, Object> properties) {
		String clientName = (String) properties.get("clientName");
		_services.put(clientName, service);
	}

	protected void removeOAuth2Service(OAuth2Service service, Map<String, Object> properties) {
		String clientName = (String) properties.get("clientName");
		_services.remove(clientName, service);
	}

	private final Map<String, OAuth2Service> _services = new ConcurrentHashMap<>();

}