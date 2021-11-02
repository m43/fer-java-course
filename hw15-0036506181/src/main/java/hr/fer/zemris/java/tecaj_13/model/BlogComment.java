package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class models an blog comment that has the following properties: id,
 * blogEntry, usersEmail, message, postedOn. Comments are in a many to one
 * relationship with {@link BlogEntry} objects.
 * 
 * @author Frano Rajič
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

	/**
	 * The id of the comment
	 */
	private Long id;

	/**
	 * The blog entry to which the comment belongs to
	 */
	private BlogEntry blogEntry;

	/**
	 * The email of the user that wrote the blog comment
	 */
	private String usersEmail;

	/**
	 * The message of the comment
	 */
	private String message;

	/**
	 * When was the comment posted
	 */
	private Date postedOn;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * @param blogEntry the associated blog entery
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * @return the user email
	 */
	@Column(length = 100, nullable = false)
	public String getUsersEmail() {
		return usersEmail;
	}

	/**
	 * 
	 * @param usersEmail the user email to set
	 */
	public void setUsersEmail(String usersEmail) {
		this.usersEmail = usersEmail;
	}

	/**
	 * @return the comment message
	 */
	@Column(length = 4096, nullable = false)
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the comment message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the date the comment was posted on
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * @param postedOn the date when the comment was posted on
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}