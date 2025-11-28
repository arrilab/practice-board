package org.example.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1) 권한 설정 (전체 허용)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )

                // 2) 폼 로그인
                .formLogin(Customizer.withDefaults());

        // 3) 마지막 build()
        return http.build();
    }
}
