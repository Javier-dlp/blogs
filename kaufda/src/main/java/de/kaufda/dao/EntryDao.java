package de.kaufda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import de.kaufda.model.Entry;

/**
 * DAO for the Entity Entry in the database. It defines the handy method getAllEntries(userId)
 * which will retrieve the entries sorted by date (desc)
 *
 * @author Javier
 */
public class EntryDao extends BaseDao<Entry, Integer>{

	/**
	 * Constructor that will initialize the BaseDao for the class Entry
	 */
	public EntryDao() {

		this(Entry.class);
	}

	public EntryDao(Class<Entry> entityClass) {
		super(entityClass);
	}

	/**
	 * Method that will retrieve all of the entries in the table
	 * @return List of the entries sorted by date(desc)
	 */
	public List<Entry> getAllEntries() {

		return getAllEntries(null);
	}

	/**
	 * Metjod that will collect all of the entries for a given user (if specified), for all users if not
	 * @param userId Identifier of the user, null if we want all of the entries
	 * @return List of the entries sorted by date(desc)
	 */
	public List<Entry> getAllEntries(final Integer userId) {

		final Session session = HibernateUtil.getSession();
		final Criteria criteria = session.createCriteria(Entry.class);
		if (null != userId) {
			criteria.add(Restrictions.eq("user.userId", userId));
		}
		@SuppressWarnings("unchecked")
		final List<Entry> entries = criteria.addOrder(Order.desc("entryDate")).list();
		HibernateUtil.commitSession(session);
		return entries;
	}
}
