package hr.fer.zemris.java.hw05.db;

/**
 * Class providing concrete strategies that implement {@link IFieldValueGetter}.
 * 
 * @author Frano Rajič
 *
 */
public class FieldValueGetters {

	/**
	 * Concrete strategy of {@link IFieldValueGetter} that gets the first name of
	 * {@link StudentRecord}
	 */
	public static final IFieldValueGetter FIRST_NAME = s -> s.getFirstName();

	/**
	 * Concrete strategy of {@link IFieldValueGetter} that gets the last name of
	 * {@link StudentRecord}
	 */
	public static final IFieldValueGetter LAST_NAME = s -> s.getLastName();

	/**
	 * Concrete strategy of {@link IFieldValueGetter} that gets the JMBAG of
	 * {@link StudentRecord}
	 */
	public static final IFieldValueGetter JMBAG = s -> s.getJmbag();
}
