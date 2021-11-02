package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * An singleton class that knows who to return as the provider of {@link DAO}
 * provider. The chosen provider is actually hardcoded in here.
 * 
 * @author Frano Rajič
 */
public class DAOProvider {

	/**
	 * The concrete DAO
	 */
	private static DAO dao = new JPADAOImpl();

	/**
	 * Get the dao
	 * 
	 * @return the dao
	 */
	public static DAO getDAO() {
		return dao;
	}

}