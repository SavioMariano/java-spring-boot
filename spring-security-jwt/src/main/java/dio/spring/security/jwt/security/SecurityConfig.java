package dio.spring.security.jwt.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.config")
public class SecurityConfig {
    public static String PREFIX;
    public static String KEY;
    public static long EXPIRATION;

    public static void setPrefix(String prefix) {
        PREFIX = prefix;
    }
    public static void setKey(String key) {
        KEY = key;
    }
    public static void setExpiration(long expiration) {
        EXPIRATION = expiration;
    }


}
