package com.pray.config;

import com.pray.security.filter.JwtAuthenticationFilter;
import com.pray.security.handler.LogOutHandler;
import com.pray.security.handler.LoginFailureHandler;
import com.pray.security.handler.LoginSuccessHandler;
import com.pray.service.AccountService;
import com.pray.utils.JwtUtils;
import com.pray.utils.PrayConstants;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * <p>
 * SecurityConfig
 * <p>
 *
 * @author 春江花朝秋月夜
 * @since 2023/9/3 0:43
 */
@Configuration
@EnableWebSecurity//开启之后会默认注册大量的过滤器链条
public class SecurityConfig{
    @Resource
    private AccountService accountService;
    @Resource
    private JwtUtils jwtUtils;//注入jwt的token生成工具
    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;



    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Resource
    PersistentTokenRepository persistentTokenRepository;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(conf->conf
                        .requestMatchers("/**").permitAll()
                )
                .formLogin(conf -> conf
                        .loginProcessingUrl("/auth/login").permitAll()
                        .successHandler(new LoginSuccessHandler(accountService,jwtUtils,stringRedisTemplate))
                        .failureHandler(new LoginFailureHandler())
                )
                //配置记住我
                .rememberMe(conf->conf
                        .rememberMeParameter("rememberMe")
                        .tokenRepository(persistentTokenRepository)
                        .tokenValiditySeconds(PrayConstants.USER_REMEMBER_SEC)//记住我的时间
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)//关闭跨域漏洞防御配置
                .logout(conf->conf
                        .logoutUrl("/auth/logout")
                        .logoutSuccessHandler(new LogOutHandler(redisTemplate,jwtUtils)))
                .exceptionHandling(conf->conf.accessDeniedHandler((request, response, accessDeniedException)
                        -> System.out.println("当前异常的登录信息："+accessDeniedException)))
                .build();
    }
}
