package br.com.hfs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import br.com.hfs.base.security.BaseAccessDeniedHandler;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private HfsUserDetailsService userDetailsService;

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	// authentication config
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}

	// authorization config
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/bootstrap-5.0.1/**", "/popper-2.9.2/**", 
				"/fontawesome-free/**", "/jquery-3.6.0/**", "/dataTables-1.10.24/**", 
				"/css/**", "/img/**", "/js/**").permitAll()       
		.antMatchers("/public/**").permitAll()
		.antMatchers("/private/**").access("hasRole('USER') or hasRole('ADMIN')")
		//.antMatchers("/anonymous*").anonymous()
		.antMatchers("/login*").permitAll()		
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login.html")
		//.loginProcessingUrl("/login.html")
		.defaultSuccessUrl("/index.html", true)
		.failureUrl("/login-error.html")
		.and()
		.logout()
		//.deleteCookies(CookieUtil.JSESSIONID)
		.logoutSuccessUrl("/index.html")
        .and()
        //.exceptionHandling().accessDeniedPage("/private/accessDenied");
        .exceptionHandling().accessDeniedHandler(accessDeniedHandler());
		//.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	//static resources config(js, css, images, etc.)
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/bootstrap-5.0.1/**", "/popper-2.9.2/**", 
				"/fontawesome-free/**", "/jquery-3.6.0/**", "/dataTables-1.10.24/**", 
				"/css/**", "/img/**", "/js/**");
	}

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new BaseAccessDeniedHandler();
    }
}
