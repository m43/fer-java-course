package hr.fer.zemris.java.webserver.servlets;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.webserver.provider.ServerProvider;

/**
 * Servlet used to get images specified by parameter id that tells the id of the
 * image to get, and optionally by parameter dimensions which tells the servlet
 * to return an resized image of dimensions (DIMENSION)x(DIMENSION). The
 * original unresized images are located in {@link ServerProvider#getOriginal()}
 * and the resized reside in {@link ServerProvider#getTemporary()}.
 * 
 * @author Frano Rajič
 */
@WebServlet(urlPatterns = { "/image" })
public class GetImage extends HttpServlet {

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 5373316386230254224L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// TODO should I throw away parameters and make it RESTful? If yes than I should
		// map this servlet to /image/* and always check the parameters given something
		// like "/image/3" for original 3 and "/image/3/150" for resized original 3 of
		// 150x150 dimensions

		String idString = req.getParameter("id");
		String dimensionsString = req.getParameter("dimensions");

		BufferedImage image;
		try {
			image = (dimensionsString == null) ? getOriginalImage(idString)
					: getResizedImage(idString, dimensionsString);
		} catch (Exception e) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		if (image == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		resp.setContentType("image/jpg");
		ImageIO.write(image, "jpg", resp.getOutputStream());
	}

	/**
	 * Help method used to get an original image specified by the given id string.
	 * Images are identified only by a integer, and that integer is deduced by
	 * parsing the given idString.
	 * 
	 * @param idString the string containing the id integer of the original image to
	 *                 load
	 * @return the loaded image or null if anything goes wrong (invalid arguments,
	 *         file does not exist etc.)
	 * @throws IOException           if any IO error occurs
	 * @throws NumberFormatException if given idString is unparseable
	 */
	public BufferedImage getOriginalImage(String idString) throws IOException {
		int id = Integer.valueOf(idString);
		String imagePath = ServerProvider.getOriginal().toString() + "/(" + id + ")" + ".jpg";
		//System.out.println(ServerProvider.getOriginal().toString());

		InputStream imageIS = Files.newInputStream(Paths.get(imagePath));

		return ImageIO.read(imageIS);
	}

	/**
	 * Help method used for loading resized images. The original images are loaded
	 * from {@link ServerProvider#getOriginal()} and the resized (temporary) images
	 * are loaded from and saved to the same folder, namely
	 * {@link ServerProvider#getTemporary()}. In the parameters the id of the image
	 * should be specified and the dimensions of the resized images. The dimensions
	 * are the same for the width and for the height.
	 * 
	 * @param idString         string telling the id of the image to resize
	 * @param dimensionsString string that should contain the dimension integer
	 * @return the buffered image
	 * @throws IOException if any input output error occurs
	 */
	public BufferedImage getResizedImage(String idString, String dimensionsString) throws IOException {
		int id = Integer.valueOf(idString);
		int dimensions = Integer.valueOf(dimensionsString);

		if (!Files.exists(ServerProvider.getTemporary())) {
			Files.createDirectories(ServerProvider.getTemporary());
		}

		String originalImagePathString = ServerProvider.getOriginal().toString() + "/(" + id + ")" + ".jpg";
		Path originalImagePath = Paths.get(originalImagePathString);

		if (!fileExists(originalImagePath)) {
			return null;
		}

		String requestedImagePathString = ServerProvider.getTemporary().toString() + "/(" + id + ")" + "_" + dimensions
				+ "x" + dimensions + ".jpg";
		Path requestedImagePath = Paths.get(requestedImagePathString);

		BufferedImage resized;
		if (!fileExists(requestedImagePath)) {
			InputStream imageIS = Files.newInputStream(originalImagePath);
			BufferedImage image = ImageIO.read(imageIS);
			int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();

			resized = resizeImage(image, dimensions, dimensions, type);
			ImageIO.write(resized, "jpg", Files.newOutputStream(requestedImagePath));
		} else {
			resized = ImageIO.read(Files.newInputStream(requestedImagePath));
		}

		return resized;
	}

	/**
	 * Help method that checks if given path is a regular and readable file
	 * 
	 * @param path the path to the file
	 * @return true if the path exists, is a regular and readable file
	 */
	private boolean fileExists(Path path) {
		return Files.exists(path) & Files.isReadable(path) & Files.isRegularFile(path);
	}

	/**
	 * Help method to return an resized image of given dimensions and type. The
	 * resized image is of JPG format.
	 * 
	 * @param image  the image to resize
	 * @param width  the width of the image
	 * @param height the height of the image
	 * @param typee  the type of the image
	 * @return the resized image as {@link BufferedImage}
	 */
	private static BufferedImage resizeImage(BufferedImage image, int width, int height, int typee) {
		Image temporary = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

		BufferedImage resizedImage = new BufferedImage(width, height, typee);
		Graphics2D g2d = resizedImage.createGraphics();
		g2d.drawImage(temporary, 0, 0, width, height, null);
		g2d.dispose();

		return resizedImage;
	}

}
