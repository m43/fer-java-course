package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Class models an blog user that has the following properties: id, first name,
 * last name, nick, email and an password hash. Nick is unique for every user.
 * For example, some user can have firstName=”Pero”, lastName=”Perić”,
 * nick=”perica”, email=”pp@some.com” and
 * passwordHash=”22ffc727b1648e4ac073589d2659dec991918ec8”. Property
 * passwordHash is used for storing storing a hex-encoded hash value (calculated
 * as SHA-1 hash) obtained from users password.
 * 
 * @author Frano Rajič
 */
@Entity
@Table(name = "users")
public class BlogUser {

	/**
	 * The identifier of the user
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * The first name of the user
	 */
	@Column(length = 50, nullable = false)
	private String firstName;

	/**
	 * The last name of the user
	 */
	@Column(length = 50, nullable = false)
	private String lastName;

	/**
	 * The nickname of the user
	 */
	@Column(length = 20, nullable = false, unique = true)
	private String nick;

	/**
	 * The email address of the user
	 */
	@Column(length = 80, nullable = false, unique = true)
	private String email;

	/**
	 * The hashed value of user's password
	 */
	@Column(nullable = false)
	private String passwordHash;

	/**
	 * The blog entries that this user wrote
	 */
	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<BlogEntry> blogEntries = new ArrayList<>();

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Create an empty blog user.
	 */
	public BlogUser() {

	}

	/**
	 * Construct a blog user.
	 * 
	 * @param firstName    the first name of the user
	 * @param lastName     the last name of the user
	 * @param nick         the unique nick of the user
	 * @param email        the unique email of the user
	 * @param passwordHash the hashed password of the user
	 */
	public BlogUser(String firstName, String lastName, String nick, String email, String passwordHash) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.nick = nick;
		this.email = email;
		this.passwordHash = passwordHash;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the passwordHash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * @param passwordHash the passwordHash to set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * @return the blogEntries
	 */
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}

	/**
	 * @param blogEntries the blogEntries to set
	 */
	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
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
		BlogUser other = (BlogUser) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nick == null) {
			if (other.nick != null)
				return false;
		} else if (!nick.equals(other.nick))
			return false;
		return true;
	}

}
