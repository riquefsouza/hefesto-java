package br.com.hfs.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.hfs.admin.repository.AdmProfileRepository;
import br.com.hfs.admin.repository.AdmUserRepository;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private HfsUserDetailsService userDetailsService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AdmUserRepository userRepository;
	
	@Autowired
	private AdmProfileRepository profileRepository;

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
		http.cors().configurationSource(corsConfigurationSource()).and()
		.authorizeRequests()
		//.antMatchers(HttpMethod.GET, "/api/v1").permitAll()
		//.antMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
		.antMatchers(HttpMethod.POST, "/auth").permitAll()
		.antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
		.anyRequest().authenticated()
		.and().csrf()
		.disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().addFilterBefore(new TokenFilter(tokenService, userRepository, profileRepository), UsernamePasswordAuthenticationFilter.class);
	}

	//static resources config(js, css, images, etc.)
	@Override
	public void configure(WebSecurity web) {
		web.ignoring()
        .antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
	}
	
	private CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:*"));
	    configuration.setAllowedMethods(Arrays.asList("*"));
	    configuration.setAllowedHeaders(Arrays.asList("*"));
	    //in case authentication is enabled this flag MUST be set, otherwise CORS requests will fail
	    configuration.setAllowCredentials(true);
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}	
}
