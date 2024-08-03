package dev.tudos.quizbuilder.core.config;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;

import java.io.IOException;

/**
 * Trust store override configuration from application properties.
 * This configuration is used to override the default trust store configuration
 * with the one provided in the application properties file.
 * This is useful when the application needs to connect to a service that uses a self-signed certificate.
 * In this case, the trust store file and password should be provided in the application properties file.
 * The trust store file should be placed in the resources folder.
 * The trust store password should be provided in the application properties file.
 */
@ConditionalOnProperty(value = "javax.net.ssl.trust-store")
@Configuration
public class TrustStoreConfiguration {
    private final Environment environment;

    public TrustStoreConfiguration(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    private void configureTrustStore() throws IOException {
        String trustStorePath = ResourceUtils.getURL(environment.getProperty("javax.net.ssl.trust-store")).getPath();
        System.setProperty("javax.net.ssl.trustStore", trustStorePath);
        System.setProperty("javax.net.ssl.trustStorePassword",
                environment.getProperty("javax.net.ssl.trust-store-password"));
    }
}
