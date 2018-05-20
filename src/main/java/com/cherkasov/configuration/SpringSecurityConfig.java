package com.cherkasov.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private AccessDeniedHandler accessDeniedHandler;

  // roles admin allow to access /admin/**
  // roles user allow to access /user/**
  // custom 403 access denied handler
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/", "/home", "/about", "/api/v1/**", "/swagger**").permitAll() //, "/swagger**"
        .antMatchers("/admin/**").hasAnyRole("ADMIN")
        .antMatchers("/user/**").hasAnyRole("USER")
//        .antMatchers("/api/v1/**").hasAnyRole("USER")
//        .anyRequest().authenticated()
        .and()
        .httpBasic()
        .and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .and()
        .logout()
        .permitAll()
        .and()
        .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
  }

  // create two users, admin and user and set credential
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

    auth.inMemoryAuthentication()
        .withUser("user").password("password").roles("USER")
        .and()
        .withUser("admin").password("password").roles("ADMIN");
  }

/*
  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public ShaPasswordEncoder getShaPasswordEncoder(){
    return new ShaPasswordEncoder();
  }
*/

/*  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(userDetailsServiceImpl)
        .passwordEncoder(getShaPasswordEncoder());
  }*/

}
