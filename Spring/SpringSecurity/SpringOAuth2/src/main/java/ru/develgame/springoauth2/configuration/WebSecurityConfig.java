package ru.develgame.springoauth2.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.DispatcherTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private static final String ZUL_FILES = "/zkau/web/**/*.zul";
    private static final String ZK_RESOURCES = "/zkres/**";
    // allow desktop cleanup after logout or when reloading login page
    private static final String REMOVE_DESKTOP_REGEX = "/zkau\\?dtid=.*&cmd_0=rmDesktop&.*";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // you need to disable spring CSRF to make ZK AU pass security filter
        // ZK already sends a AJAX request with a built-in CSRF token,
        // please refer to https://www.zkoss.org/wiki/ZK%20Developer's%20Reference/Security%20Tips/Cross-site%20Request%20Forgery
        http.csrf().disable();
        http.authorizeHttpRequests()
            .requestMatchers(new AndRequestMatcher(new DispatcherTypeRequestMatcher(DispatcherType.ERROR), AntPathRequestMatcher.antMatcher("/error"))).permitAll() // allow default error dispatcher
            .requestMatchers(new AndRequestMatcher(new DispatcherTypeRequestMatcher(DispatcherType.FORWARD), AntPathRequestMatcher.antMatcher(ZUL_FILES))).permitAll() // allow forwarded access to zul under class path web resource folder
            .requestMatchers(AntPathRequestMatcher.antMatcher(ZUL_FILES)).denyAll() // block direct access to zul under class path web resource folder
            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, ZK_RESOURCES)).permitAll() // allow zk resources
            .requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET, REMOVE_DESKTOP_REGEX)).permitAll() // allow desktop cleanup
            .requestMatchers(req -> "rmDesktop".equals(req.getParameter("cmd_0"))).permitAll() // allow desktop cleanup from ZATS
            .requestMatchers(AntPathRequestMatcher.antMatcher("/login"), AntPathRequestMatcher.antMatcher("/logout")).permitAll() //permit the URL for login and logout
            .requestMatchers(AntPathRequestMatcher.antMatcher("/secure")).hasRole("USER")
            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/favicon.ico")).permitAll() // allow favicon.ico
            .anyRequest().authenticated() //enforce all requests to be authenticated
            .and()
                .logout()
                .logoutSuccessUrl("/").permitAll()
            .and()
                .oauth2Login()
                    .loginPage("/login")
                    .defaultSuccessUrl("/callback", true)
                ;

        return http.build();
    }
}