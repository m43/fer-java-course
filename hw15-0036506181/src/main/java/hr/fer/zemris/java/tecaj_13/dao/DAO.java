package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Interface models the functionality that an DAO provider needs to have.
 * 
 * @author Frano Rajič
 */
public interface DAO {

	/**
	 * Get the {@link BlogEntry} with given <code>id</code>. If none was found,
	 * <code>null</code> is returned.
	 * 
	 * @param id the ID of the {@link BlogEntry}
	 * @return the associated {@link BlogEntry} or null if none was found
	 * @throws DAOException if any error occurs during fetching of the data
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Get the {@link BlogUser} with given <code>id</code>. If none was found,
	 * <code>null</code> is returned.
	 * 
	 * @param id the ID of the {@link BlogUser}
	 * @return the associated {@link BlogUser} or null if none was found
	 * @throws DAOException if any error occurs during fetching of the data
	 */
	public BlogUser getBlogUserById(Long id) throws DAOException;

	/**
	 * Get the {@link BlogUser} with given <code>nick</code>. If none was found,
	 * <code>null</code> is returned.
	 * 
	 * @param nick the nick of the {@link BlogUser}
	 * @return the associated {@link BlogUser} or null if none was found
	 * @throws DAOException if any error occurs during fetching of the data
	 */
	public BlogUser getBlogUserByNick(String nick) throws DAOException;

	/**
	 * Get the {@link BlogUser} with given <code>email</code>. If none was found,
	 * <code>null</code> is returned.
	 * 
	 * @param email the email of the {@link BlogUser}
	 * @return the associated {@link BlogUser} or null if none was found
	 * @throws DAOException if any error occurs during fetching of the data
	 */
	public BlogUser getBlogUserByEmail(String email) throws DAOException;

	/**
	 * Get all {@link BlogUser} stored in persistance layer.
	 * 
	 * @return the list of blog users
	 * @throws DAOException if any error occurs during fetching of the data
	 */
	public List<BlogUser> getBlogUsers() throws DAOException;

	/**
	 * Get all {@link BlogEntry} objects associated to given {@link BlogUser}
	 * 
	 * @param nick the nick of the author
	 * @return the list of blog entries
	 * @throws DAOException if any error occurs during fetching of the data
	 */
	public List<BlogEntry> getBlogUserEntries(String nick) throws DAOException;

	/**
	 * Get {@link BlogEntry} associated to given nick and given blog entry id
	 * 
	 * @param nick the nickname of the author
	 * @param id   the id the of the blog entry
	 * @return the blog entry or null if none found
	 * @throws DAOException if any error occurs during fetching of the data
	 */
	public BlogEntry getBlogUserEntryById(String nick, Long id) throws DAOException;

	/**
	 * Get the comments of the blog entry given by id.
	 * 
	 * @param id the id of the blog entry
	 * @return the list of comments
	 * @throws DAOException thrown if any error occurs during data retrieval
	 */
	public List<BlogComment> getBlogEntryComments(Long id) throws DAOException;

	/**
	 * Make the given blog entry persistent.
	 * 
	 * @param be the blog entry
	 */
	public void saveBlogEntry(BlogEntry be);

	/**
	 * Make the given blog comment persistent.
	 * 
	 * @param bc the blog entry
	 */
	public void saveBlogComment(BlogComment bc);
	
	/**
	 * Make the given blog user persistent.
	 * 
	 * @param user the blog user
	 */
	public void saveBlogUser(BlogUser user);
}