package com.epam.company.config;

import com.epam.company.model.types.UserProfileType;
import com.epam.company.util.resolvers.SecureResolver;
import com.epam.company.util.resolvers.WebResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by @belrbeZ
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/",
                        WebResolver.WELCOME,
                        WebResolver.LOGIN,
                        WebResolver.REGISTER, "/static/css/**", "/static/css/**" ,
                        "/static/js/**", "/webjars/bootstrap/**").permitAll()
                .antMatchers(WebResolver.SECURED + "/**")
                    .hasAnyAuthority(SecureResolver.convert(UserProfileType.ADMIN), SecureResolver.convert(UserProfileType.USER))
//                    .hasAnyRole(SecureResolver.convert(UserProfileType.ADMIN), SecureResolver.convert(UserProfileType.USER))
                    .anyRequest().authenticated()
                .antMatchers("/admin/**")
                    .hasAuthority(SecureResolver.convert(UserProfileType.ADMIN))
//                    .hasRole(SecureResolver.convert(UserProfileType.ADMIN))
                .and()
                    .csrf()
                .and()
                    .formLogin()
                    .loginPage(WebResolver.LOGIN)
                    .failureUrl(WebResolver.LOGIN + "?error=true")
                    .successForwardUrl(WebResolver.FEED)
                    .defaultSuccessUrl(WebResolver.FEED, true)
                    .usernameParameter("email")
                    .passwordParameter("password")
                .and()
                    .exceptionHandling()
                    .accessDeniedPage(WebResolver.DENIED);;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).getUserDetailsService();
//            .passwordEncoder(new BCryptPasswordEncoder());
    }

/*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
*/


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/js/**", "/static/css/**", "/static/css/**" , "/static/images/**", "/webjars/bootstrap/**");
    }
}
