package cn.edu.tongji.login.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 阿里云sms短信验证配置
 */
@Configuration
@ConfigurationProperties(prefix = "aliyun.sms", ignoreUnknownFields = false)
@PropertySource("classpath:config/authentication.properties")
@Data
public class AliyunSmsConfig {
    private String regionId;        //地区id
    private String accessKeyId;     //阿里云密钥id
    private String accessKeyValue;  //阿里云密钥值
    private String signName;        //签名（验证短信头）
    private String templateCode;    //模板编号

    /**
     * 配置执行发送函数的Client对象
     * @return Client对象
     */
    @Bean
    public IAcsClient iAcsClient() {
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeyValue);
        return new DefaultAcsClient(profile);
    }
}