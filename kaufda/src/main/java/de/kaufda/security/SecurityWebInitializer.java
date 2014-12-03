package de.kaufda.security;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
 
/**
 * Class that initializes the Spring Security with the de.kaufda.security.SecurityConfig
 *
 * @author Javier
 */
public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer {
 
	/**
	 * Calls the Initializer passing the de.kaufda.security.SecurityConfig class
	 */
    public SecurityWebInitializer() {
        super(SecurityConfig.class);

		final Logger logger = Logger.getLogger(SecurityWebInitializer.class.getName());
		logger.log(Level.INFO, "SecurityConfig Initialized");
    }
}