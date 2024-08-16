package dio.spring.security.jwt.security;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.h2.server.web.WebServlet;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    public static final String[] SWAGGER_WHITELIST = {
        "/v2/api-docs",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**"
    };

    protected void configure(HttpSecurity http) throws Exception{
        http
            .csrf(csrf -> csrf
                .disable()
            )
            .headers(headers -> headers
            .frameOptions(frameOptions -> frameOptions
            .sameOrigin()
            ))
            .addFilterAfter(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(SWAGGER_WHITELIST).permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/login" ).permitAll()
                .requestMatchers(HttpMethod.POST,"/users").permitAll()
                .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("USERS", "MANAGERS")
                .requestMatchers("/managers").hasAnyRole("MANAGERS")
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );
    }
    @Bean // HABILITANDO ACESSAR O H2-DATABASE NA
    public ServletRegistrationBean h2ServletRegistration() {
    ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
    registrationBean.addUrlMappings("/h2-console/*");
    return registrationBean;
}

}
