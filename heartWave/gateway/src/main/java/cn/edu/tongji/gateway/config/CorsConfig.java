package cn.edu.tongji.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    private static final List<String> ALLOWED_ORIGINS = Arrays.asList(
            "http://localhost:8881",
            "http://localhost:8880",
            "http://localhost:8888",
            "http://localhost:8887",
            "http://120.26.0.232"
    );
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 允许的HTTP方法，"*"表示允许所有方法
        configuration.setAllowedMethods(Arrays.asList("*"));
        // 允许的请求来源
        configuration.setAllowedOrigins(ALLOWED_ORIGINS);
        // 允许的请求头，"*"表示允许所有请求头
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // 是否允许发送Cookie信息
        configuration.setAllowCredentials(true);

        // 创建基于URL路径模式的CORS配置源
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        // 注册CORS配置到全局
        source.registerCorsConfiguration("/**", configuration);

        return new CorsWebFilter(source);
    }

}
