package de.kaufda.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.kaufda.dao.UserDao;

/**
 * Spring Security Service Implementation to retrieve the user from the database and authenticate it.
 * It uses the de.kaufda.security.MyUser implementation which allows us to access the userId from the
 * SecurityContextHolder. 
 *
 * @author Javier
 */
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

	/**
	 * Method implementation to load a MyUser from the database instead of the Spring default User.
	 */
	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		final UserDao userDao = new UserDao();
		final de.kaufda.model.User user = userDao.findUserByName(username);
		final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getUserRole()));

		return new MyUser(user.getUserName(), user.getUserPassword(), authorities, user.getUserId());

	}
}
