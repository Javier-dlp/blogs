package de.kaufda.common;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import de.kaufda.security.MyUser;

/**
 * Utilities class for common use through the app.
 *
 * @author Javier
 */
public class Utils {
	
	/**
	 * Method to retrieve the Logged User from the Security Context
	 * @return The Logged MyUser
	 */
	public static MyUser getMyUser() {
		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return principal instanceof MyUser ? (MyUser) principal : null;
	}

	/**
	 * Method to retrieve the User ID from the Security Context
	 * @return The Logged User ID
	 */
	public static Integer getMyUserId() {
		final MyUser myUser = getMyUser();
		return myUser == null ? null : myUser.getMyUserId();
	}

	/**
	 * Method to check if the Logged user has the role ROLE_ADMIN
	 * @return false when there is no logged user of it has not got the role ROLE_ADMIN
	 */
	public static boolean isAdmin() {
		final MyUser myUser = getMyUser();
		final boolean isAdmin;
		if (myUser == null) {
			isAdmin = false;
		} else {
			isAdmin = myUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		return isAdmin;
	}
}
