package de.kaufda.config;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Starts the web application, loads the AppConfig, the ServletContext and defaults the Logs to a file in the specified
 * folder.
 *
 * @author Javier
 */
public class AppWebInitializer implements WebApplicationInitializer {

	/**
	 * Loads the servletCotext the AppConfig and the Logs configuration.
	 * @param servletContext that will be loaded
	 */
	@Order
	@Override
	public void onStartup(final ServletContext servletContext) throws ServletException {

		final AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(AppConfig.class);
		ctx.setServletContext(servletContext);

		final Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
		servlet.addMapping("/");
		servlet.setLoadOnStartup(1);

		final Logger logger = Logger.getLogger("");
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmm");
			final String logFileName = "log-".concat(sdf.format(new Date())).concat(".txt");
			final FileHandler fileHandler = new FileHandler("/logs/".concat(logFileName));
			fileHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fileHandler);
		} catch (final SecurityException e) {
			logger.log(Level.CONFIG, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.CONFIG, e.getMessage(), e);
		}

		logger.log(Level.INFO, "App Initialized");
	}

}
