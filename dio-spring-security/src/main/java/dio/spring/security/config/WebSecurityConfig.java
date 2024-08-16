package dio.spring.security.config;

import java.net.Authenticator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private SecurityDataBaseService securityService;
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(securityService).passwordEncoder(NoOpPasswordEncoder.getInstance());
}

    /*@Bean
    protected UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
            .username("user")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .build();
        
        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("adminpassword"))
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
    */
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/").permitAll() // Permite acesso a todos na raiz "/"
                .requestMatchers("/users").hasRole("USER") // Exige o papel USER para acessar "/users"
                .requestMatchers("/managers").hasRole("ADMIN") // Exige o papel ADMIN para acessar "/managers"
                .anyRequest().authenticated()// Exige autenticação para qualquer outra rota
            )
            .httpBasic()
            .and()
            .formLogin(formLogin -> formLogin
            .loginPage("/login") // Página de login customizada (opcional)
            .permitAll() // Permite acesso à página de login para todos
            );
        return http.build(); 
    }


        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
    }
}