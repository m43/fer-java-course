package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class models an worker by implementing {@link IWebWorker} and is
 * supposed to produce an PNG image with dimensions 200x200 and with a single
 * filled circle
 * 
 * @author Frano Rajič
 */
public class CircleWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);

		Graphics2D g2d = bim.createGraphics();

		g2d.setColor(Color.decode("#e74c3c"));
		g2d.fillRect(0, 0, bim.getWidth(), bim.getHeight());
		g2d.setColor(Color.decode("#f1c40f"));
		g2d.fillOval(0, 0, bim.getWidth(), bim.getHeight());
		g2d.dispose();

		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(bim, "png", bos);
			byte[] data = bos.toByteArray();
			context.setMimeType("image/png");
			context.setContentLength((long) data.length);
			context.write(data);
		} catch (IOException e) {
			// TODO how to handle this
			e.printStackTrace();
		}

	}

}
