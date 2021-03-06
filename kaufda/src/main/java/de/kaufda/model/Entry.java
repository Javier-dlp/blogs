package de.kaufda.model;

// Generated 27-nov-2014 20:32:00 by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.kaufda.model.User;

/**
 * Entity class to access the DB object. From the original definition, it only changes the PK definition strategy to
 * strategy = GenerationType.IDENTITY, so the database will insert it with a serial.
 *
 * Entry generated by hbm2java
 */
@Entity
@Table(name = "entry", schema = "javier_test")
public class Entry implements java.io.Serializable {

	/**
	 *  Auto-generated serialVersionUID
	 */
	private static final long serialVersionUID = 6990201788276034732L;
	/**
	 * Unique identifier of the entry, auto-generated by the database
	 */
	private Integer entryId;
	/**
	 * User to whom the entry belongs, at least a user with userId is indispensable
	 */
	private User user;
	/**
	 * Title of the entry, not null, indispensable
	 */
	private String entryTitle;
	/**
	 * Content text of the entry
	 */
	private String entryText;
	/**
	 * Image for the entry
	 */
	private byte[] entryImage;
	/**
	 * Date of last modification
	 */
	private Date entryDate;
	/**
	 * TODO: Not implemented yet, an entry can be an answer to another entry
	 */
	private Integer parentEntryId;

	public Entry() {
	}

	public Entry(Integer entryId) {
		this.entryId = entryId;
	}

	public Entry(Integer entryId, User user, String entryTitle, String entryText,
			byte[] entryImage, Integer parentEntryId) {
		this.entryId = entryId;
		this.user = user;
		this.entryTitle = entryTitle;
		this.entryText = entryText;
		this.entryImage = entryImage;
		this.parentEntryId = parentEntryId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "entry_id", unique = true, nullable = false)
	public Integer getEntryId() {
		return this.entryId;
	}

	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "entry_title")
	public String getEntryTitle() {
		return this.entryTitle;
	}

	public void setEntryTitle(String entryTitle) {
		this.entryTitle = entryTitle;
	}

	@Column(name = "entry_text")
	public String getEntryText() {
		return this.entryText;
	}

	public void setEntryText(String entryText) {
		this.entryText = entryText;
	}

	@Column(name = "entry_image")
	public byte[] getEntryImage() {
		return this.entryImage;
	}

	public void setEntryImage(byte[] entryImage) {
		this.entryImage = entryImage;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entry_date", length = 35)
	public Date getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	@Column(name = "parent_entry_id")
	public Integer getParentEntryId() {
		return this.parentEntryId;
	}

	public void setParentEntryId(Integer parentEntryId) {
		this.parentEntryId = parentEntryId;
	}

}
