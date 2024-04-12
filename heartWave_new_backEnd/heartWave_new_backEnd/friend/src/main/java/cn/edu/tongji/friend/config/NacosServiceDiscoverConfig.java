package cn.edu.tongji.friend.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

/**
 * 微服务注册到nacos的配置
 */
@EnableDiscoveryClient
@Configuration
public class NacosServiceDiscoverConfig {
}
