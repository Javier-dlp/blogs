package de.kaufda.model;

// Generated 27-nov-2014 20:32:00 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entity Class to access the DB object. From the original definition, it only changes the PK definition strategy to
 * strategy = GenerationType.IDENTITY, so the database will insert it with a serial.
 *
 * User generated by hbm2java
 */
@Entity
@Table(name = "user", schema = "javier_test", uniqueConstraints = @UniqueConstraint(columnNames = "user_name"))
public class User implements java.io.Serializable {

	/**
	 * Auto-generated serialVersionUID
	 */
	private static final long serialVersionUID = -4444542543145454373L;
	/**
	 * Identifier of the table
	 */
	private Integer userId;
	/**
	 * Name of the user, not null and unique
	 */
	private String userName;
	/**
	 * Password of the user, can be null
	 */
	private String userPassword;
	/**
	 * Role of the user according to the the spring specification "ROLE_ADMIN", "ROLE_USER"
	 */
	private String userRole;
	/**
	 * Set of the entries for this user
	 */
	private Set<Entry> entries = new HashSet<Entry>(0);

	public User() {
	}

	public User(Integer userId) {
		this.userId = userId;
	}

	public User(Integer userId, String userName, String userPassword, Set<Entry> entries) {
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.entries = entries;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "user_name", unique = true, nullable = false)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "user_password")
	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Column(name = "user_role")
	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Entry> getEntries() {
		return this.entries;
	}

	public void setEntries(Set<Entry> entries) {
		this.entries = entries;
	}

}
