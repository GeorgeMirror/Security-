package app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@ComponentScan("app")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final LoginSuccessHandler userSuccessHandler;

    @Autowired
    public SecurityConfig(@Qualifier("userServiceImpl") UserDetailsService userDetailsService, LoginSuccessHandler userSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.userSuccessHandler = userSuccessHandler;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.formLogin().successHandler(userSuccessHandler).loginProcessingUrl("/login").permitAll();
        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll();
        http.authorizeRequests().antMatchers("/login").anonymous().
                antMatchers("/admin").access("hasAnyRole('ADMIN')").
                antMatchers("/admin/**").access("hasAnyRole('ADMIN')").
                antMatchers("/user").access("hasAnyRole('USER', 'ADMIN')").
                antMatchers("/user/**").access("hasAnyRole('USER', 'ADMIN')");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
