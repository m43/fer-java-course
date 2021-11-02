package hr.fer.zemris.java.webserver.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

/**
 * Class provides functionality of receiving JSON information about images being
 * served in the gallery. It is designed in RESTful spirit. To get information
 * about a specific image, "/images/{index}" should be called, and to get
 * information about all images in gallery "/images" should be called.
 * 
 * @author Frano Rajič
 */
@Path("/images")
public class ImageInfoJSON {

	/**
	 * Get information of all images
	 * 
	 * @return JSON response with an array of all image informations
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getImageInfoList() {
		Gson gson = new Gson();
		String jsonText = gson.toJson(ImageInfoGetter.getAllImageInfoModels());

		return Response.ok(jsonText).build();
		//
		// TODO volio bih da ovo radi automatski preko ImageInfoWriter, ali nisam uspio
		// napraviti da tako bude.. Dakle da array ImageInfoModel[] bude automatski
		// preuzet od tog writera i pretvoren u JSON
		// return ImageInfoGetter.getAllImageInfoModels();
	}

	/**
	 * Get the image information of image with identifier given as index.
	 * 
	 * @param index the index of the image information to return
	 * @return JSON response with image information of image with given index or null
	 *         if no image was found
	 */
	@Path("/id/{index}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ImageInfoModel getImageInfo(@PathParam("index") int index) {
		return ImageInfoGetter.getImageInfoModelById(index);
	}

	/**
	 * Get the image information of all images with tag form url
	 * 
	 * @param tag the tag of the images to get
	 * @return json response with an array of image information of images with given
	 *         tag or null if no image was found with given tag
	 */
	@Path("/tag/{tag}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getImageInfoByTag(@PathParam("tag") String tag) {
		ImageInfoModel[] models = ImageInfoGetter.getImageInfoModelsByTag(tag);
		if (models == null || models.length == 0) {
			return Response.status(Status.NOT_FOUND).build();
		}

		Gson gson = new Gson();
		String jsonText = gson.toJson(models);

		return Response.ok(jsonText).build();
	}

	/**
	 * Get all tags associated to gallery images
	 * 
	 * @return JSON response with all tags
	 */
	@Path("tags")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getImageInfoByTag() {
		Gson gson = new Gson();
		String jsonText = gson.toJson(ImageInfoGetter.getImageInfoModelTags());

		return Response.ok(jsonText).build();
	}

}
