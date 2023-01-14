package com.girlfriend.sunghun.global.config;

import com.girlfriend.sunghun.global.security.JwtFilter;
import com.girlfriend.sunghun.global.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtProvider jwtProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
                .csrf().disable() // rest api이므로 csrf 보안이 필요없으므로 disable처리.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증하므로 세션은 필요없으므로 생성안함.
                .and()
                .authorizeRequests()
                // quest
                .antMatchers(HttpMethod.POST, "/quest/**").authenticated()
                .antMatchers(HttpMethod.GET, "/quest/**").permitAll()
                // user
                .antMatchers(HttpMethod.POST, "/auth/**").permitAll()
                // swagger-ui
                .antMatchers("/swagger-ui/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
    }

    private static final String[] DOC_URLS = {
            "/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html","/swagger-ui/**"
    };

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .and().ignoring().antMatchers(DOC_URLS);
    }
}
