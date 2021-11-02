package hr.fer.zemris.java.webserver.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.java.webserver.provider.ServerProvider;

/**
 * Class provides the functionality of loading {@link ImageInfoModel} objects
 * from text file located at {@link ServerProvider#getInformation()}.
 * Information about every image is structured like in three lines, and all the
 * information together is in the previously mentioned text file. First line has
 * image name, for example "(1).jpg". The second line contains image
 * information, for example "This is an image desc...". The third line contains
 * tags seperated with commas, like this "first tag, second, third, etc,
 * blabla". The tags are converted to lower case, trimmed and can contain
 * multiple words.
 * 
 * @author Frano Rajič
 */
public class ImageInfoGetter {

	/**
	 * Method returns an array of all image information found in
	 * {@link ServerProvider#getInformation()}.
	 * 
	 * @return an array of all image information found or null if nothing found
	 */
	public static ImageInfoModel[] getAllImageInfoModels() {
		String[] allInfoLines;
		try {
			List<String> allInfoLinesList = Files.readAllLines(ServerProvider.getInformation());
			allInfoLines = new String[allInfoLinesList.size()];
			allInfoLinesList.toArray(allInfoLines);
		} catch (IOException e) {
			return null;
		}

		ImageInfoModel[] all = new ImageInfoModel[allInfoLines.length / 3];
		for (int i = 0; i < allInfoLines.length / 3; i++) {
			int id;
			try {
				String idString = allInfoLines[i * 3 + 0];
				id = Integer.valueOf(idString.substring(idString.indexOf('(') + 1, idString.lastIndexOf(')')));
			} catch (NumberFormatException e) {
				id = -1;
			}

			String[] tags = allInfoLines[i * 3 + 2].split(",");
			for (int j = 0; j < tags.length; j++) {
				tags[j] = tags[j].toLowerCase().trim();
			}

			all[i] = new ImageInfoModel(id, allInfoLines[i * 3 + 1], tags);
		}

		return all;
	};

	/**
	 * Get the information about image with given id.
	 * 
	 * @param id the id of the image
	 * @return the {@link ImageInfoModel} of the image or null if not found
	 */
	public static ImageInfoModel getImageInfoModelById(int id) {
		for (ImageInfoModel i : getAllImageInfoModels()) {
			// not very efficient, but yea...
			if (i.id == id) {
				return i;
			}
		}
		return null;
	}

	/**
	 * Get the information about all images with given tag
	 * 
	 * @param tagString the tag of the images to get
	 * @return an array of found images or an empty array if none were found
	 */
	public static ImageInfoModel[] getImageInfoModelsByTag(String tagString) {
		String tag = tagString.toLowerCase().trim();

		List<ImageInfoModel> models = new ArrayList<>();
		for (ImageInfoModel i : getAllImageInfoModels()) {
			for (String iTag : i.getTags()) {
				if (iTag.equals(tag)) {
					models.add(i);
				}
			}
		}

		ImageInfoModel[] modelsArray = new ImageInfoModel[models.size()];
		models.toArray(modelsArray);

		return modelsArray;
	}

	/**
	 * Return an list of all tags associated to images and in a sorted order
	 * 
	 * @return sorted array of image tags
	 */
	public static String[] getImageInfoModelTags() {
		Set<String> tags = new HashSet<>();
		for (ImageInfoModel i : getAllImageInfoModels()) {
			for (String tag : i.getTags()) {
				tags.add(tag);
			}
		}

		return tags.stream().sorted().toArray(String[]::new);
	}
}
