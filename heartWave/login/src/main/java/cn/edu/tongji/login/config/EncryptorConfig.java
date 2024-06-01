package cn.edu.tongji.login.config;

import lombok.Setter;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 加密配置
 */
@Configuration
@ConfigurationProperties(prefix = "encryptor", ignoreUnknownFields = false)
@Setter
public class EncryptorConfig {
    private String password;   //解密所需密码
    private String algorithm;  //加密算法

    /**
     * 配置加密器
     * @return 加密器
     */
    @Bean
    public StandardPBEStringEncryptor standardPBEStringEncryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm(algorithm);
        encryptor.setPassword(password);
        return encryptor;
    }
}
