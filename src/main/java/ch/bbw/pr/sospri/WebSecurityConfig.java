package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberservice;

//    @Autowired
//    public void globalSecurityConfiguration(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("user").password("{noop}1234").roles("USER");
//        auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("USER", "ADMIN");
//    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider () {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(this.memberservice);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("Using default configure(HttpSecurity). If subclassed this will potentially override subclass configure(HttpSecurity).");
        http

                .authorizeRequests()
                .antMatchers("/noSecurity").permitAll()
                .antMatchers("/get-channel").hasAnyAuthority("admin", "supervisor", "member")
                .antMatchers("/get-members").hasAuthority("admin")
                .antMatchers("/register-member").permitAll()
                .antMatchers("/get-register").permitAll()
                .antMatchers("/home").permitAll()
                .antMatchers("/index.html").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/fragments/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and().logout().permitAll()
                .and()
                .httpBasic()
                .and().exceptionHandling().accessDeniedPage("/403.html");

        http.csrf().ignoringAntMatchers("/h2-console/**")
                .and().headers().frameOptions().sameOrigin();
    }
@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
}



}