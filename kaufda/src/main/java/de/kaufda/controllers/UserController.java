package de.kaufda.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import de.kaufda.common.Utils;
import de.kaufda.dao.UserDao;
import de.kaufda.model.User;

/**
 * Controller for the actions of users. The Login action is handled by Spring security, here we only prenent the form.
 * Actions are "/login", "edit" and "/create" to present the forms, and "/save" to update the user or create a new one.
 *
 * @author Javier
 */
@Controller
@RequestMapping("/user")
public class UserController {

	/**
	 * We inject the AuthenticationManager Bean defined in de.kaufda.security.SecurityConfig
	 */
	@Autowired
	@Qualifier("myAuthenticationManager")
	private AuthenticationManager authenticationManager;
	/**
	 * Common DAO for the User type
	 */
	final UserDao userDao = new UserDao();

	/**
	 * After spring login configuration we render the for login here. The custom AuthenticationHandler
	 * de.kaufda.security.MyAuthenticationFailureHandler injects the error (if any) as a session attribute.
	 * We will read it, send it as a parameter and remove it from the session 
	 * @param request for accessing the session and the possible error and clean it
	 * @return main page
	 */
	@RequestMapping("/login")
	public ModelAndView login(final HttpServletRequest request) {
		
		final ModelAndView result = new ModelAndView("/login", "user", new User());
		final Object error = request.getSession().getAttribute("error");
		if (null != error) {
			result.addObject("error", error);
			request.getSession().removeAttribute("error");
		}
		return result;
	}

	/**
	 * Presents the login form for creating and user, sends the parameter creation=true
	 * @return login page
	 */
	@RequestMapping("/create")
	public ModelAndView createUser() {
		final ModelAndView result = new ModelAndView("/login", "user", new User());
		result.addObject("creation", true);
		return result;
	}

	/**
	 * Presents the edit page for the custom user
	 * TODO: Allow administrator to edit users
	 * @return the login page with the user loaded and the parmeter edition=true
	 */
	@RequestMapping("/edit")
	public ModelAndView editUser() {
		final ModelAndView result = new ModelAndView("/login", "user", userDao.find(Utils.getMyUserId()));
		result.addObject("edition", true);
		return result;
	}

	/**
	 * Saves the received user and authenticates it (adds it to the spring security context)
	 * @param user to be saved
	 * @return to the main page when successful, user page when error with a param error
	 */
	@RequestMapping("/save")
	public ModelAndView addUser(final @ModelAttribute("user") User user) {

		final ModelAndView result;
		final User existentUser = userDao.findUserByName(user.getUserName());
		// If is a new User or we have permissions to edit it
		if (existentUser == null || hasPermission(existentUser)) {
			if (user.getUserRole() == null) {
				user.setUserRole("ROLE_USER");
			}
			userDao.saveOrUpdate(user);
			// Adds the user to the spring security context
			authenticateUser(user.getUserName(), user.getUserPassword());
			result = new MainController().main();
		// If not return to the createuser page and add error (if any)
		} else {
			result = createUser();
			if (existentUser != null && user.getUserId() != existentUser.getUserId()) {
				result.addObject("error", "unavailable username");
			}
		}
		return result;
	}

	/**
	 * Authenticates the user and adds it to the spring security context. It uses the authenticatinManager
	 * injected in the class and defined in de.kaufda.security.SecurityConfig
	 * @param username User Name
	 * @param password Password
	 */
	private void authenticateUser(final String username, final String password) {

		final Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		final Authentication authentication = authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	/**
	 * Checks if we are administrator or the user matches our spring security authenticated user.
	 * @param user to whom check if we have permissions over
	 * @return true if is our user or we are administrator
	 */
	private boolean hasPermission(final User user) {

		final boolean result;
		if (Utils.isAdmin()) {
			result = true;
		} else if (null != user) {
			final Integer myUserId = Utils.getMyUserId();
			result = myUserId != null && myUserId.equals(user.getUserId());
		} else {
			result = false;
		}
		return result;
	}
}
