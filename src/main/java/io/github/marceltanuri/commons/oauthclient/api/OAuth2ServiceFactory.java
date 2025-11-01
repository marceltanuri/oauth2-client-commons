package io.github.marceltanuri.commons.oauthclient.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * A factory for obtaining {@link OAuth2Service} instances.
 * This service tracks all available {@link OAuth2Service} instances and provides a method
 * to retrieve a specific service by its client name.
 */
@Component(service = OAuth2ServiceFactory.class)
public class OAuth2ServiceFactory {

	/**
	 * Retrieves an {@link OAuth2Service} instance by its client name.
	 *
	 * @param clientName The unique name of the OAuth2 client configuration.
	 * @return The {@link OAuth2Service} instance, or {@code null} if no service is found with the given name.
	 */
	public OAuth2Service getOAuth2Service(String clientName) {
		return _services.get(clientName);
	}

	/**
	 * Binds an {@link OAuth2Service} instance to this factory.
	 * This method is called by the OSGi framework when a new {@link OAuth2Service} is available.
	 *
	 * @param service The {@link OAuth2Service} instance.
	 * @param properties The service properties, including the clientName.
	 */
	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		target = "(clientName=*)"
	)
	protected void addOAuth2Service(OAuth2Service service, Map<String, Object> properties) {
		String clientName = (String) properties.get("clientName");
		_services.put(clientName, service);
	}

	/**
	 * Unbinds an {@link OAuth2Service} instance from this factory.
	 * This method is called by the OSGi framework when an {@link OAuth2Service} is no longer available.
	 *
	 * @param service The {@link OAuth2Service} instance.
	 * @param properties The service properties, including the clientName.
	 */
	protected void removeOAuth2Service(OAuth2Service service, Map<String, Object> properties) {
		String clientName = (String) properties.get("clientName");
		_services.remove(clientName, service);
	}

	private final Map<String, OAuth2Service> _services = new ConcurrentHashMap<>();

}