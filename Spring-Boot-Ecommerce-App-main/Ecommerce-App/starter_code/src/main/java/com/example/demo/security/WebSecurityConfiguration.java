package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurityConfiguration(UserDetailsServiceImpl userDetailsService,
                                    BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        try{
            JWTAuthenticationFilter jwtAuthenticationFilter=new JWTAuthenticationFilter(authenticationManager());
            JWTAuthenticationVerficationFilter jwtAuthenticationVerficationFilter=new JWTAuthenticationVerficationFilter(authenticationManager());
            http.cors().and().csrf().disable().authorizeRequests()
                    .antMatchers(HttpMethod.POST, SecurityConstants.sign_up_url).permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .addFilter(jwtAuthenticationFilter)
                    .addFilter(jwtAuthenticationVerficationFilter)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        try{
            return super.authenticationManagerBean();


        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        try{
            auth.parentAuthenticationManager(authenticationManagerBean())
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(bCryptPasswordEncoder);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
