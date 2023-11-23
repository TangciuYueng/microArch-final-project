package cn.edu.tongji.login.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "encryptor", ignoreUnknownFields = false)
@PropertySource("classpath:config/secret.properties")
@Configuration
@Data
@Component
public class EncryptorConfig {
    private String password;
    private String algorithm;
}
