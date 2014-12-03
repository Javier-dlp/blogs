package de.kaufda.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

/**
 * Base Data Access Object class to be extended by all other custom DAOs.
 * It contains the main metods find(PK), findAll, delete(object), deleteById(PK), saveOrUpdate(object).
 * All of the methods will access the HibernateUtil methods to mandatory get and commit the session.
 *
 * @author Javier
 *
 * @param <T> Entity Class connected to a table in the database
 * @param <PK> Serializable PK type of the table in the database
 */
@SuppressWarnings("unchecked")
public class BaseDao<T, PK extends Serializable> {

	/**
	 * Entity Class over which we are consulting
	 */
	private Class<T> entityClass;

	/**
	 * Constructor to initialize the subclasses to connect the consultations with an entity
	 * @param entityClass Entity over which we are accessing the DB
	 */
	public BaseDao(final Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * Retrieves the element of the entity for a given PK
	 * @param pk Identifier of the registry on the table
	 * @return the entity for that PK, null when none
	 */
	public T find(PK pk) {
		final Session session = HibernateUtil.getSession();
		final T result = (T) session.get(entityClass, pk);
		HibernateUtil.commitSession(session);
		return result;
	}

	/**
	 * Finds all registries for the established Entity
	 * @return a list of all the registries
	 */
	public List<T> findAll() {
		final Session session = HibernateUtil.getSession();
		final List<T> result = (List<T>) session.createCriteria(entityClass).list();
		HibernateUtil.commitSession(session);
		return result;
	}

	/**
	 * Deletes the given object from the database
	 * @param object with the PK
	 */
	public void delete(T object) {
		final Session session = HibernateUtil.getSession();
		session.delete(object);
		HibernateUtil.commitSession(session);
	}

	/**
	 * Deletes the object from the database for the given identifier. It will look for the object first
	 * @param pk Primary Key of the object we want to delete
	 */
	public void deleteById(PK pk) {
		final Session session = HibernateUtil.getSession();
		session.delete(find(pk));
		HibernateUtil.commitSession(session);
	}

	/**
	 * Saves or updates the object in tehe database
	 * @param object We want to save
	 */
	public void saveOrUpdate(T object) {
		final Session session = HibernateUtil.getSession();
		session.saveOrUpdate(object);
		HibernateUtil.commitSession(session);
	}

}
