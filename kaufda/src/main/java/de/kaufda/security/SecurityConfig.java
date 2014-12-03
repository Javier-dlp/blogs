package de.kaufda.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Configuration implementation for the spring security, it defines the AuthenticationManager to inject it if needed,
 * configures the user authentication service, the web ignore patterns and the http authorization urls.
 *
 * @author Javier
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * We create the myAuthenticationManager bean for accessing it through injecting it
	 *
	 * <pre>
	 * @Autowired
	 * @Qualifier("myAuthenticationManager")
	 * private AuthenticationManager authenticationManager;
	 * </pre>
	 */
	@Bean(name = "myAuthenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {

		return super.authenticationManagerBean();
	}

	/**
	 * Configuration of UserDetails to login against the DB and handling the response
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(new MyUserDetailsService());
	}

	/**
	 * Ignore .html files in spring security
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {

		web.ignoring().antMatchers("/*.html");
	}

	/**
	 * The roles are saved as ROLE_ADMIN, ROLE_USER... but the hasRole(..) ignores the suffix "ROLE_"
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests().antMatchers("/entry/blog**").hasAnyRole("ADMIN", "USER")
				.antMatchers("/entry/save**").hasAnyRole("ADMIN", "USER").and().formLogin().loginPage("/user/login")
				.permitAll().failureHandler(new MyAuthenticationFailureHandler()).and().logout().logoutSuccessUrl("/");
	}

}
