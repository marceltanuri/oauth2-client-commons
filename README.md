# OSGi Module for OAuth2 Client

This project provides a generic OAuth2 client as an OSGi module, allowing OSGi applications to easily and configurably obtain access tokens.

## Features

- **Generic OAuth2 Client:** Support for the *Client Credentials* flow.
- **Dynamic Configuration:** Configure multiple OAuth2 clients through the OSGi Configuration Admin, without needing to restart bundles.
- **Token Caching:** Automatic token caching to avoid unnecessary requests.

## How to Use

### 1. Add the Maven Dependency

Add the following dependency to your project's `pom.xml`. Please check for the latest version on Maven Central.

```xml
<dependency>
    <groupId>io.github.marceltanuri.commons</groupId>
    <artifactId>oauth-client-commons-module</artifactId>
    <version>1.0.7</version>
</dependency>
```

### 2. Configure an OAuth2 Client

Create a `.cfg` configuration file in your OSGi container's configuration directory (e.g., `etc/` or `load/`).

The filename should be `io.github.marceltanuri.commons.oauthclient.api.OAuth2ClientSettings-<client-name>.cfg`.

**Configuration Example (`io.github.marceltanuri.commons.oauthclient.api.OAuth2ClientSettings-myapi.cfg`):**

```properties
# Unique name for this client configuration
clientName="myapi"

# URL of the OAuth2 server's token endpoint
tokenEndpoint="https://auth.example.com/oauth/token"

# The client ID provided by the OAuth2 server
clientId="your-client-id"

# The client secret provided by the OAuth2 server
clientSecret="your-client-secret"

# (Optional) The audience for the token
audience="urn:my-api"

# (Optional) The scope of the requested permissions
scope="read:data"

# (Optional) The maximum number of entries to keep in the token cache.
cacheMaxEntries=100

```

**Security Note:** For enhanced security, it is strongly recommended to configure the `clientSecret` using an environment variable instead of placing it directly in the configuration file. The `clientId` can also be configured this way.

The application will automatically look for environment variables with the following naming convention:

-   `IO_GITHUB_MARCELTANURI_COMMONS_OAUTHCLIENT_API_[NORMALIZED_CLIENT_NAME]_CLIENT_ID`
-   `IO_GITHUB_MARCELTANURI_COMMONS_OAUTHCLIENT_API_[NORMALIZED_CLIENT_NAME]_CLIENT_SECRET`

Where `[NORMALIZED_CLIENT_NAME]` is the `clientName` from your configuration file, converted to uppercase, with any non-alphanumeric characters replaced by underscores.

For the example above (`clientName="myapi"`), the environment variables would be:

-   `IO_GITHUB_MARCELTANURI_COMMONS_OAUTHCLIENT_API_MYAPI_CLIENT_ID`
-   `IO_GITHUB_MARCELTANURI_COMMONS_OAUTHCLIENT_API_MYAPI_CLIENT_SECRET`

If an environment variable is set, its value will be used, overriding any value present in the `.config` file. You can leave the `clientSecret` property empty or omit it from the file when using an environment variable.

### 3. Get the Service in Your Code

In your bundle, you can get the `OAuth2Service` instance by injecting the `OAuth2ServiceFactory` and requesting the service by its `clientName`.

```java
import io.github.marceltanuri.commons.oauthclient.OAuth2Service;
import io.github.marceltanuri.commons.oauthclient.OAuth2ServiceFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class MyComponent {

    private OAuth2ServiceFactory _oauth2ServiceFactory;

    @Reference
    protected void setOAuth2ServiceFactory(OAuth2ServiceFactory factory) {
        _oauth2ServiceFactory = factory;
    }

    public String getToken() {
        OAuth2Service oauth2Service = _oauth2ServiceFactory.getOAuth2Service("myapi");

        if (oauth2Service != null) {
            return oauth2Service.getAccessToken();
        }

        // Handle the case where the service is not available
        return null;
    }
}
```

## Build

To build the project, run the following Maven command:

```bash
mvn clean install
```