package cn.edu.tongji.login.config;

import lombok.Setter;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "encryptor", ignoreUnknownFields = false)
@PropertySource("classpath:config/secret.properties")
@Setter
public class EncryptorConfig {
    private String password;
    private String algorithm;

    @Bean
    public StandardPBEStringEncryptor standardPBEStringEncryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm(algorithm);
        encryptor.setPassword(password);
        return encryptor;
    }
}
