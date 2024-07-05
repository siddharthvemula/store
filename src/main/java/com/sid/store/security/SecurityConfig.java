package com.sid.store.security;

import com.sid.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserService userService;
    @Autowired
    private DataSource dataSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService);
        authenticationManagerBuilder.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder()).dataSource(dataSource);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        http
            .authenticationManager(authenticationManager)
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/items/**").hasRole("ADMIN")
                    .requestMatchers("/item/**").hasRole("ADMIN")
                    .requestMatchers("/home/**").permitAll()
                    .requestMatchers("/**").permitAll()
                    .requestMatchers("/temps/**").permitAll()
                    .requestMatchers("/temp/**").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                    .permitAll()
            )
            .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .passwordEncoder(passwordEncoder())
//                .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?")
//                .authoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username = ?");
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

/**
 https://docs.spring.io/spring-security/reference/5.7/servlet/authentication/passwords/form.html
 https://medium.com/@abhinavv.singh/implementing-user-authentication-in-a-spring-boot-application-a-detailed-step-by-step-guide-b15a9877569b
 https://www.initgrep.com/posts/java/spring/Spring-Security-jpa-authprovider
 https://spring.io/guides/gs/securing-web
 https://github.com/spring-guides/gs-securing-web/tree/main/complete/src/main/java/com/example/securingweb
 https://www.baeldung.com/spring-security-jdbc-authentication
 https://medium.com/java-epic/spring-security-jdbc-authentication-vs-in-memory-authentication-48bc82d927e2
 https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html#servlet-authentication-unpwd
 https://www.baeldung.com/spring-security-jdbc-authentication
 https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/jdbc.html#servlet-authentication-jdbc
 https://medium.com/@hakeembaseem/custom-authentication-in-springboot-3-x-and-java-17-using-database-58ae446a4065
 https://www.baeldung.com/spring-security-authentication-with-a-database
 https://www.baeldung.com/spring-deprecated-websecurityconfigureradapter
 https://stackoverflow.com/questions/72381114/spring-security-upgrading-the-deprecated-websecurityconfigureradapter-in-spring
 https://www.baeldung.com/registration-with-spring-mvc-and-spring-security
 https://stackoverflow.com/questions/63739817/spring-security-role-based-authentication-403-forbidden-although-user-has-role
 https://www.google.com/search?q=implementing+username+password+authentication+in+spring+boot&sca_esv=4bb6b1564136f20c&sca_upv=1&sxsrf=ADLYWIJ91GG_W09pOSUfokhiQjhFjXnHqw%3A1719969125767&ei=ZaWEZqO_Lp-s5NoPqoSq4AY&ved=0ahUKEwjjhZCm2ImHAxUfFlkFHSqCCmwQ4dUDCA8&uact=5&oq=implementing+username+password+authentication+in+spring+boot&gs_lp=Egxnd3Mtd2l6LXNlcnAiPGltcGxlbWVudGluZyB1c2VybmFtZSBwYXNzd29yZCBhdXRoZW50aWNhdGlvbiBpbiBzcHJpbmcgYm9vdDIFECEYoAEyBRAhGKABMgUQIRigATIFECEYoAEyBRAhGKABMgUQIRirAjIFECEYnwUyBRAhGJ8FMgUQIRifBTIFECEYnwVIiBJQiQdYww9wAngBkAEAmAHUAaABxQSqAQU0LjAuMbgBA8gBAPgBAZgCB6AC1wTCAgoQABiwAxjWBBhHmAMAiAYBkAYIkgcFNi4wLjGgB9gz&sclient=gws-wiz-serp
  */

//    public static void main(String[] args) {
//// insert into store.users (username, password, first_name, last_name, role, enabled) values ('admin', '$2a$10$xnfAGHcjx3qoyXZG36qHqe43r0aGcmaCt8QtXdVbGvsyczWyi43jS', 'Admin', 'Admin', "ADMIN", true);
//// password = $2a$10$xnfAGHcjx3qoyXZG36qHqe43r0aGcmaCt8QtXdVbGvsyczWyi43jS
//        System.err.println(new BCryptPasswordEncoder().encode("password"));
//    }
}