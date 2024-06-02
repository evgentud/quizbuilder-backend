package dev.tudos.quizbuilder.core.config;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@ConditionalOnProperty(value = "javax.net.ssl.trust-store")
@Configuration
public class TrustStoreConfiguration {
    private final Environment env;

    public TrustStoreConfiguration(Environment env) {
        this.env = env;
    }

    @PostConstruct
    private void configureTrustStore() throws IOException {
        String trustStorePath = new ClassPathResource(env.getProperty("javax.net.ssl.trust-store")).getFile().getAbsolutePath();
        System.setProperty("javax.net.ssl.trustStore", trustStorePath);
        System.setProperty("javax.net.ssl.trustStorePassword",env.getProperty("javax.net.ssl.trust-store-password"));
    }

}
