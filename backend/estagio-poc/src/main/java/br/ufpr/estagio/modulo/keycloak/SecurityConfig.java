package br.ufpr.estagio.modulo.keycloak;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // http.oauth2Login(withDefaults());
        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        // disable csrf for /auth/login para permitir acesso a rota sem token de
        // autenticação
        http.csrf().disable()
                .cors()
                .and()
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/auth/login")
                        .permitAll()
                        .requestMatchers("/aluno/**")
                        .authenticated()
                        .anyRequest()
                        .permitAll());

        // http.authorizeHttpRequests(requests -> requests
        // .requestMatchers("/siga*")
        // .hasRole("USER")
        // .anyRequest()
        // .permitAll());

        // http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        // http.authorizeRequests(requests -> requests
        // .antMatchers("/aluno/**").authenticated()
        // .anyRequest().permitAll());

        return http.build();
    }
}