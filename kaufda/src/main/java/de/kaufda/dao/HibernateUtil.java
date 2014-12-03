package de.kaufda.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Hibernate Class that defines the way of connecting to the database. It statically initializes a sessionFactory
 * and will try to get the currentsession (opens a new one if fails) when getSession and commit it when commitSession.
 * In this implementation is very important that we call getSession, do our operations, and commit it.
 * When getSession we also begin a Transaction that will be closed when commitSession.
 *
 * @author Javier
 */
public class HibernateUtil {

	/**
	 * Common logger initialization
	 */
//	private static final Logger logger = Logger.getLogger(SecurityWebInitializer.class.getName());
	/**
	 * Statically builds the sessionFactory when loading the class
	 */
	private static final SessionFactory sessionFactory = buildSessionFactory();

	/**
	 * Method that builds a new Session Factory
	 * @return the sessionFactory
	 */
	private static SessionFactory buildSessionFactory() {
		try {
			return new AnnotationConfiguration().configure().buildSessionFactory();
		} catch (Throwable ex) {
//			logger.log(Level.SEVERE, "Initial SessionFactory creation failed", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Method to get the currentSession, opens a new one if cannot access it, and begins a Transaction
	 * @return an open session to access the DB
	 */
	public static Session getSession() {
		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (org.hibernate.HibernateException he) {
			session = sessionFactory.openSession();
//			logger.log(Level.SEVERE, "could not retrieve the session, opening new one", he);
		}
		session.beginTransaction();
		return session;
	}

	/**
	 * Method to commit the open transaction for the given session
	 * @param session currently open session with an open transaction
	 */
	public static void commitSession(Session session) {
		session.getTransaction().commit();
	}

}
