package de.kaufda.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Implementation of the spring security user to access his id from the Security Context Holder.
 *
 * @author Javier
 */
public class MyUser extends User {
	
	/**
	 * Auto-generated serialVersionUID
	 */
	private static final long serialVersionUID = 7511093116991853449L;
	/**
	 * Identifier of the User to access it from the SecurityContextHolder
	 */
	private Integer myUserId;

	/**
	 * Implementation of the User (by default is enabled, does not expire and is not locked.
	 * We just add to it the given authorities and add the userId to this Bean
	 * @param username
	 * @param password
	 * @param authorities
	 * @param userId
	 */
	public MyUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
			Integer userId) {
		super(username, password, true, true, true, true, authorities);
		this.setMyUserId(userId);
	}

	public Integer getMyUserId() {
		return myUserId;
	}

	public void setMyUserId(Integer myUserId) {
		this.myUserId = myUserId;
	}

}
