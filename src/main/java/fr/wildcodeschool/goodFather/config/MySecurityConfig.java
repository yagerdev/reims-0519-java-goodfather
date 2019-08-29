package fr.wildcodeschool.goodFather.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import fr.wildcodeschool.goodFather.services.MyUserDetailsService;

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/admin").hasRole("ADMIN")
            .antMatchers("/categories").hasRole("ADMIN")
            .antMatchers("/typologies").hasRole("ADMIN")
            .antMatchers("/materials").hasRole("ADMIN")
            .antMatchers("/works").hasRole("ADMIN")
            .antMatchers("/tasks").hasRole("ADMIN")
            .antMatchers("/users").hasRole("ADMIN")
            .antMatchers("/projects/**").access("hasRole('ADMIN') and hasRole('USER')")
            .antMatchers("/rooms/**").access("hasRole('ADMIN') and hasRole('USER')") 
            .antMatchers("/home/**").access("hasRole('ADMIN') and hasRole('USER')")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .and()
            .httpBasic();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myUserDetailsService);
        authProvider.setPasswordEncoder(encoder);
        return authProvider;
	}
}