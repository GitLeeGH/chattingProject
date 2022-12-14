package com.example.chattproject.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();



        http.authorizeRequests()
                .antMatchers("/", "/account/register", "/board", "/css/**", "/fonts/**", "/img/**", "/js/**", "/scss/**", "/vender/**").permitAll()
                .antMatchers("/account/admin/**", "/idiot/list", "/idiot/detail/**").access("hasRole('ROLE_ADMIN')")

                .anyRequest().authenticated()
                .and();

        http.formLogin()
                .loginPage("/account/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
                .and();

        http.logout()
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID", "remember-me")
                .permitAll();


        http.sessionManagement()
                .sessionFixation().changeSessionId()
                .invalidSessionUrl("/account/loginPage")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false);

    }




    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                // ????????????(user ?????????)
                .usersByUsernameQuery("select email, password, enabled "
                        + "from user " // ????????? ??????
                        + "where email = ?")
                // ????????????(user_role ?????????)
                .authoritiesByUsernameQuery("select u.email, r.name "
                        + "from user_role ur inner join user u on ur.user_id = u.id "
                        + "inner join role r on ur.role_id = r.id "
                        + "where u.email = ?");
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }





}