package hr.fer.zemris.java.webserver.rest;

/**
 * Class models image information, providing id, description and tags
 * attributes.
 * 
 * @author Frano Rajič
 */
public class ImageInfoModel {

	/**
	 * The id of the image (aka the unique identifier)
	 */
	public int id;

	/**
	 * The description of the image
	 */
	public String description;

	/**
	 * The tags associated with the image
	 */
	public String[] tags;

	/**
	 * Construct a new {@link ImageInfoModel} with all necessary information given.
	 * 
	 * @param id          the identifier
	 * @param description the description of the image
	 * @param tags        the tags associated with the image
	 */
	public ImageInfoModel(int id, String description, String[] tags) {
		this.id = id;
		this.description = description;
		this.tags = tags;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the tags
	 */
	public String[] getTags() {
		return tags;
	}

}
