package de.kaufda.dao;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.kaufda.model.User;

/**
 * DAO for the Entity User in the database. It defines the method findByUserName (accessed from
 * de.kaufda.security.MyUserDetailsService as part of the security authentication).
 *
 * @author Javier
 */
public class UserDao extends BaseDao<User, Integer>{

	/**
	 * Constructor that will initialize the BaseDao for the class User
	 */
	public UserDao() {

		this(User.class);
	}

	public UserDao(Class<User> entityClass) {
		super(entityClass);
	}

	/**
	 * Method to find a User by a given user name
	 * @param userName Name of the user
	 * @return
	 */
	public User findUserByName(final String userName) {

		final Session session = HibernateUtil.getSession();
		final User user = (User) session.createCriteria(User.class)
				.add(Restrictions.eq("userName", userName)).uniqueResult();
		HibernateUtil.commitSession(session);
		return user;
	}
}
