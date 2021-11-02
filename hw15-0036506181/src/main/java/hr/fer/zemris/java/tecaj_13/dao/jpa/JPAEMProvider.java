package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;

/**
 * Class models an provider of {@link EntityManager} objects
 * 
 * @author Frano Rajič
 */
public class JPAEMProvider {

	/**
	 * Thread local map to remember which entity manager belongs to which thread.
	 */
	private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

	/**
	 * Get the {@link EntityManager}.
	 * 
	 * @return the entity manager
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = locals.get();
		if (em == null) {
			em = JPAEMFProvider.getEmf().createEntityManager();
			em.getTransaction().begin();
			locals.set(em);
		}
		return em;
	}

	/**
	 * Close the entity manager
	 * 
	 * @throws DAOException if something goes wrong at persistence layer.
	 */
	public static void close() throws DAOException {
		EntityManager em = locals.get();
		if (em == null) {
			return;
		}
		DAOException dex = null;
		try {
			em.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			em.close();
		} catch (Exception ex) {
			if (dex == null) { // NOTE! ovo nije !=
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if (dex != null)
			throw dex;
	}

}