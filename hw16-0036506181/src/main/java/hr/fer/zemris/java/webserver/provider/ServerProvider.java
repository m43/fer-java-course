package hr.fer.zemris.java.webserver.provider;

import java.nio.file.Path;

/**
 * Class provides other servlets with some server configuration information. It
 * should be initialized on server startup by calling the
 * {@link #initialize(Path, Path, Path)} method.
 * 
 * @author Frano Rajič
 */
public class ServerProvider {

	/**
	 * Relative path to the repository containing original size gallery images
	 */
	private static final String relativeOriginalImageRepository = "WEB-INF/slike/";

	/**
	 * Relative path to the repository containing temporary gallery images that are
	 * of various sizes
	 */
	private static final String relativeTemporaryImageRepository = "WEB-INF/thumbnails/";

	/**
	 * Relative path to the text file containing information about gallery images.
	 */
	private static final String relativeImageInformationPath = "WEB-INF/opisnik.txt";

	/**
	 * The absolute path to the temporary gallery image repository folder.
	 */
	private static Path temporary;

	/**
	 * The absolute path to the original gallery image repository folder.
	 */
	private static Path original;

	/**
	 * The absolute path to the gallery image information text file.
	 */
	private static Path information;

	/**
	 * Boolean telling whether all provider information has been initialized.
	 */
	private static boolean initialized;

	/**
	 * Method used to initialize provider.
	 * 
	 * @param temp    the temporary image repository path
	 * @param origigi the original image repository path
	 * @param info    the image information path
	 */
	public static void initialize(Path temp, Path origigi, Path info) {
		temporary = temp;
		original = origigi;
		information = info;

		initialized = true;
	}

	/**
	 * @return the temporary
	 */
	public static Path getTemporary() {
		if (!initialized) {
			throw new IllegalStateException("Cannot return uninitialized parameter");
		}

		return temporary;
	}

	/**
	 * @param temporary the temporary to set
	 */
	public static void setTemporary(Path temporary) {
		ServerProvider.temporary = temporary;
	}

	/**
	 * @return the original
	 */
	public static Path getOriginal() {
		if (!initialized) {
			throw new IllegalStateException("Cannot return uninitialized parameter");
		}

		return original;
	}

	/**
	 * @param original the original to set
	 */
	public static void setOriginal(Path original) {
		ServerProvider.original = original;
	}

	/**
	 * @return the information
	 */
	public static Path getInformation() {
		if (!initialized) {
			throw new IllegalStateException("Cannot return uninitialized parameter");
		}

		return information;
	}

	/**
	 * @param information the information to set
	 */
	public static void setInformation(Path information) {
		ServerProvider.information = information;
	}

	/**
	 * @return the initialized
	 */
	public static boolean isInitialized() {
		return initialized;
	}

	/**
	 * @param initialized the initialized to set
	 */
	public static void setInitialized(boolean initialized) {
		ServerProvider.initialized = initialized;
	}

	/**
	 * @return the relativeoriginalimagerepository
	 */
	public static String getRelativeoriginalimagerepository() {
		return relativeOriginalImageRepository;
	}

	/**
	 * @return the relativetemporaryimagerepositorystring
	 */
	public static String getRelativetemporaryimagerepository() {
		return relativeTemporaryImageRepository;
	}

	/**
	 * @return the relativeimageinformationpath
	 */
	public static String getRelativeimageinformationpath() {
		return relativeImageInformationPath;
	}

}
