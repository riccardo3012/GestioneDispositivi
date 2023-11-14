package security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
@EnableMethodSecurity//dichiara su ogni endpoint i permessi di accesso in base ai ruoli tramite annotazione @preAuthorize nel endpoint che desideriamo (IN CONTROLLER)
public class SecurityConfig {
    @Autowired
    private AuthFilter authFilter;

    @Bean
    SecurityFilterChain securityFilterChainConfig(HttpSecurity http) throws Exception {
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        http.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable());
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(authFilter,ExceptionFilter.class);
        http.authorizeHttpRequests(request->request.requestMatchers("/**").permitAll());
        return http.build();
    }
}