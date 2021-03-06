package softuni.boardgames.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import softuni.boardgames.service.impl.AppUserServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AppUserServiceImpl appUserService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfiguration(AppUserServiceImpl appUserService, PasswordEncoder passwordEncoder) {
        this.appUserService = appUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests().
                requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
                antMatchers("/", "/users/login", "/games/all", "/users/register", "/links").permitAll().
                antMatchers("/games/add", "/games/edit/select", "/games/*/edit", "/links/add").hasRole("EDITOR").
                antMatchers("/users/change-role", "/games/stats/visits").hasRole("ADMIN").
                antMatchers("/**").authenticated().
                and().
                formLogin().
                        loginPage("/users/login").
                        defaultSuccessUrl("/home", true).
                        failureUrl("/users/login?error=true").
                and().
                logout().
                        logoutUrl("/logout").
                        logoutSuccessUrl("/").
                        invalidateHttpSession(true).
                        deleteCookies("JSESSIONID");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(appUserService)
                .passwordEncoder(passwordEncoder);
    }
}
