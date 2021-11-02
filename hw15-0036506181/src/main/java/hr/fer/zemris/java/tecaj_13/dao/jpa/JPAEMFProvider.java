package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * The provider that enables getting an {@link EntityManagerFactory}.
 * 
 * @author Frano Rajič
 */
public class JPAEMFProvider {

	/**
	 * The {@link EntityManagerFactory}
	 */
	public static EntityManagerFactory emf;

	/**
	 * Get the {@link EntityManagerFactory}
	 * 
	 * @return the {@link EntityManagerFactory}
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * @param emf the {@link EntityManagerFactory} to set
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}