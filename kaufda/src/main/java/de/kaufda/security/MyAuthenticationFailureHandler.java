package de.kaufda.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * Handler for the login authentication. When authentication fails, it sets an object error=message in the session
 *
 * @author Javier
 */
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

	/**
	 * Authentication handler required method, to redirect the user to login with an error when failing.
	 * Sets the error message on the session when failure
	 */
	@Override
	public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
			final AuthenticationException authException) throws IOException, ServletException {

		final String errorMsg;
		if (authException.getMessage() == null) {
			errorMsg = "Unknown User";
		} else {
			errorMsg = authException.getMessage();
		}
		request.getSession().setAttribute("error", errorMsg);
		response.sendRedirect("login");
	}

}
