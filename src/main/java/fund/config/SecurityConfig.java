package fund.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import fund.service.MyAuthenticationProvider;
import fund.service.MyAuthenticationResultHandler;
import fund.service.MyLogoutResultHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired MyAuthenticationResultHandler myAuthenticationResultHandler;
    @Autowired MyLogoutResultHandler mylogoutResultHandler;
    @Autowired MyAuthenticationProvider myAuthenticationProvider;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/res/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .antMatchers("/guest/**").permitAll()
            .antMatchers("/**").authenticated();

        http.csrf().disable();

        http.formLogin()
            .loginPage("/guest/login")
            .loginProcessingUrl("/guest/login_processing")
            .defaultSuccessUrl("/")
            .usernameParameter("loginName")
            .passwordParameter("password")
            .successHandler(myAuthenticationResultHandler)
            .failureHandler(myAuthenticationResultHandler);

        http.logout()
            .logoutUrl("/home/logout")
            .logoutSuccessHandler(mylogoutResultHandler)
            .logoutSuccessUrl("/guest/login")
            .invalidateHttpSession(true);

        http.authenticationProvider(myAuthenticationProvider);
    }

}
