package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Concrete {@link DAO} implementation that uses JPA for persistence.
 * 
 * @author Frano Rajič
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
	}

	@Override
	public BlogUser getBlogUserById(Long id) throws DAOException {
		return JPAEMProvider.getEntityManager().find(BlogUser.class, id);
	}

	@Override
	public BlogUser getBlogUserByNick(String nick) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		@SuppressWarnings("unchecked")
		List<BlogUser> users = (List<BlogUser>) em.createQuery("select b from BlogUser as b where b.nick=:n")
				.setParameter("n", nick).getResultList();

		if (users.isEmpty()) {
			return null;
		}

		return users.get(0);
	}

	@Override
	public BlogUser getBlogUserByEmail(String email) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		@SuppressWarnings("unchecked")
		List<BlogUser> users = (List<BlogUser>) em.createQuery("select b from BlogUser as b where b.email=:email")
				.setParameter("email", email).getResultList();

		if (users.isEmpty()) {
			return null;
		}

		return users.get(0);
	}

	@Override
	public List<BlogUser> getBlogUsers() throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		@SuppressWarnings("unchecked")
		List<BlogUser> users = (List<BlogUser>) em.createQuery("select b from BlogUser as b").getResultList();

		return users;
	}

	@Override
	public List<BlogEntry> getBlogUserEntries(String nick) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		@SuppressWarnings("unchecked")
		List<BlogEntry> entries = (List<BlogEntry>) em
				.createQuery("select u.blogEntries from BlogUser as u where u.nick =: nickname")
				.setParameter("nickname", nick).getResultList();

		return entries;
	}

	@Override
	public BlogEntry getBlogUserEntryById(String nick, Long id) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		BlogUser user = getBlogUserByNick(nick);

		@SuppressWarnings("unchecked")
		List<BlogEntry> be = (List<BlogEntry>) em
				.createQuery("select be from BlogEntry as be where be.creator =: user and be.id =: id")
				.setParameter("user", user).setParameter("id", id).getResultList();

		if (be.isEmpty())
			return null;

		return be.get(0);
	}

	@Override
	public List<BlogComment> getBlogEntryComments(Long id) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		@SuppressWarnings("unchecked")
		List<BlogComment> comments = (List<BlogComment>) em
				.createQuery("select be.comments from BlogEntry as be where be.id =: id").setParameter("id", id)
				.getResultList();

		return comments;
	}

	@Override
	public void saveBlogEntry(BlogEntry be) {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(be);
	}

	@Override
	public void saveBlogComment(BlogComment bc) {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(bc);
	}

	@Override
	public void saveBlogUser(BlogUser user) {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(user);
	}

}