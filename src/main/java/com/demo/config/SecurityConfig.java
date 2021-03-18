package com.demo.config;

import com.demo.security.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Jonsy
 *
 */
@Configuration
@EnableWebSecurity //注释掉可以既能享受到springboot的自动配置又能覆盖某些配置
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected UserDetailService userDetailService;


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().maximumSessions(4)
                .and()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                .and()
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                        AuthenticationException exception) throws IOException, ServletException {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    }
                })
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {
                        response.setStatus(HttpServletResponse.SC_OK);
                    }
                })
                .loginProcessingUrl("/user/login")
                .and()
                .rememberMe().userDetailsService(userDetailService).alwaysRemember(true).rememberMeCookieName("token").tokenValiditySeconds(3600 * 24 * 20).useSecureCookie(false)
                .and()
                .logout().logoutUrl("/user/logout").deleteCookies().invalidateHttpSession(true).logoutSuccessHandler(new LogoutSuccessHandler(){
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                Authentication authentication) throws IOException,
                            ServletException {
                        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                    }
        });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        auth.authenticationProvider(provider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




}
