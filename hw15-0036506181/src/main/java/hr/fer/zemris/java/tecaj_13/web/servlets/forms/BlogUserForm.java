package hr.fer.zemris.java.tecaj_13.web.servlets.forms;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.servlets.utilities.ServletUtilities;

/**
 * Class models an form that is used for temporal storage of new registered user
 * data. It has all the same properties as {@link BlogUser}, having password
 * instead of password hash (but the password should not be given back in the
 * returned form to the user when creating response, so that the user enters the
 * password again). The class provides the functionality of reading form data
 * given in HTTP request {@link HttpServletRequest}, validating entered form
 * data and storing the found inconsistencies in given form data in an error
 * list.
 * 
 * @author Frano Rajič
 */
public class BlogUserForm {

	/**
	 * The ID of the user.
	 */
	private String id;

	/**
	 * The first name of the user
	 */
	private String firstName;

	/**
	 * The last name of the user
	 */
	private String lastName;

	/**
	 * The nickname of the user
	 */
	private String nick;

	/**
	 * The email address of the user
	 */
	private String email;

	/**
	 * The plain text password (no hashing).
	 */
	private String password;

	/**
	 * Boolean changed if the form gets validated.
	 */
	private boolean validated;

	/**
	 * Inconsistencies map that maps a property name of the form to the respective
	 * error message string, if the form field contains an inconsistencies.
	 */
	Map<String, String> inconsistencies = new HashMap<>();

	/**
	 * Check if the given form has inconsistencies in the error map
	 * 
	 * @return true if there are inconsistencies that have been validated
	 */
	public boolean hasInconsistencies() {
		return !inconsistencies.isEmpty();
	}

	/**
	 * Check if the given form has an inconsistency associated with given property
	 * name.
	 * 
	 * @param property the name of the property that is being checked
	 * @return true if there is an inconsistency associated to given property
	 */
	public boolean hasInconsistency(String property) {
		return inconsistencies.containsKey(property);
	}

	/**
	 * Get the inconsistency mapped to given property
	 * 
	 * @param property the property
	 * @return the message describing the inconsistency
	 */
	public String getInconsistency(String property) {
		return inconsistencies.get(property);
	}

	/**
	 * Load the properties of this form with given
	 * 
	 * @param req the HTTP request
	 */
	public void loadFromHttpRequest(HttpServletRequest req) {
		this.id = prepare(req.getParameter("id"));
		this.firstName = prepare(req.getParameter("firstname"));
		this.lastName = prepare(req.getParameter("lastname"));
		this.nick = prepare(req.getParameter("nick"));
		this.email = prepare(req.getParameter("email"));
		this.password = prepare(req.getParameter("password"));
	}

	/**
	 * Load the form with given {@link BlogUser}.
	 * 
	 * @param user the user to load the form from
	 */
	public void loadFromBlogUser(BlogUser user) {
		this.id = String.valueOf(user.getId());
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.nick = user.getNick();
		this.email = user.getEmail();
	}

	/**
	 * 
	 * Before calling this method, the form should be validated and checked for no
	 * inconsistencies. Otherwise null is returned.
	 * 
	 * @return the {@link BlogUser} created from this valid form or null if invalid
	 *         form
	 */
	public BlogUser createBlogUser() {
		if (!validated || !inconsistencies.isEmpty()) {
			return null;
		}

		BlogUser bu = new BlogUser();

		bu.setId(Long.getLong(id));
		bu.setFirstName(firstName);
		bu.setLastName(lastName);
		bu.setNick(nick);
		bu.setEmail(email);

		try {
			bu.setPasswordHash(ServletUtilities.generateSHA(password));
		} catch (NoSuchAlgorithmException ignoreable) {
		}

		return bu;
	}

	/**
	 * Help method to validate the data that the user entered.
	 * 
	 * @param dao the DAO provider used to lookup the uniqueness of given nick and
	 *            email
	 */
	public void validate(DAO dao) {
		validated = true;
		inconsistencies.clear();

		// ID is not checked

		if (this.firstName.isEmpty()) {
			inconsistencies.put("firstname", "First name must be given");
		} else if (this.firstName.length() > 50) {
			inconsistencies.put("firstname", "First name cannot contain more than 50 characters");
		}

		if (this.lastName.isEmpty()) {
			inconsistencies.put("lastname", "Last name must be given");
		} else if (this.lastName.length() > 50) {
			inconsistencies.put("lastname", "Last name cannot contain more than 50 characters");
		}

		if (this.email.isEmpty()) {
			inconsistencies.put("email", "Email must be given");
		} else if (this.email.length() > 50) {
			inconsistencies.put("email", "Email cannot contain more than 80 characters");
		} else if (ServletUtilities.isEmailInvalid(email)) {
			inconsistencies.put("email", "Invalid email given");
		} else if (dao.getBlogUserByEmail(email) != null) {
			inconsistencies.put("email", "User with given mail exists");
		}

		if (this.nick.isEmpty()) {
			inconsistencies.put("nick", "Nick must be given");
		} else if (this.nick.length() > 20) {
			inconsistencies.put("nick", "Email cannot contain more than 20 characters long");
		} else if (dao.getBlogUserByNick(nick) != null) {
			inconsistencies.put("nick", "User with given nick exists");
		}

		if (this.password.isEmpty()) {
			inconsistencies.put("password", "Password must be given");
		} else if (this.password.length() < 10) {
			inconsistencies.put("password", "Password must contain at least 10 characters");
		} else
			try {
				if (ServletUtilities.generateSHA(password) == null) {
					inconsistencies.put("password", "Password is invalid");
				}
			} catch (NoSuchAlgorithmException e) {
				inconsistencies.put("password", "Invalid password");
			}
	}

	/**
	 * Help method that will take a <code>null</code> String and convert it into an
	 * empty string for the ease of use. If the given string is not a null string,
	 * then it is trimmed.
	 * 
	 * @param string string
	 * @return "" if null, the given string trimmed otherwise
	 */
	private String prepare(String string) {
		if (string == null)
			return "";

		return string.trim();
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
		validated = false;

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
		validated = false;

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
		validated = false;

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
		validated = false;

		this.email = email;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		validated = false;

		this.id = id;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		validated = false;

		this.password = password;
	}

}
