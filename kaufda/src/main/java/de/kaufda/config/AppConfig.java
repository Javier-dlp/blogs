package de.kaufda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * Configuration class that specifies the ubication and extension of files, allows the tratment of files and registers
 * the ubication of the javascript files.
 *
 * @author Javier
 */
@Configuration
@ComponentScan({ "de.kaufda.controllers" })
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {

	/**
	 * Sets up the way of looking for the views, it will be in the folder /views/ with the extension jsp
	 *
	 * @return
	 */
	@Bean
	public UrlBasedViewResolver setupViewResolver() {

		final UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix("/views/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}

	/**
	 * Adds Multipart functionality to the model and view, which allows us to send files
	 *
	 * @return the multipart resolver
	 */
	@Bean
	public CommonsMultipartResolver multipartResolver() {

		final CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		return resolver;
	}

	/**
	 * Sets up the location of the javascript and css files
	 *
	 * @param registry in which we load the resources handler and locations of the javascript and css files
	 */
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
	}
}
