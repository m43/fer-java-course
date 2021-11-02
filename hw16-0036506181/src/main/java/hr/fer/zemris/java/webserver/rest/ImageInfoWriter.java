package hr.fer.zemris.java.webserver.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;

/**
 * Class models an provider that knows how to serialize an
 * {@link ImageInfoModel} into JSON.
 * 
 * @author Frano Rajič
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ImageInfoWriter implements MessageBodyWriter<ImageInfoModel> {

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return type == ImageInfoModel.class;
	}

	@Override
	public long getSize(ImageInfoModel t, Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType) {
		return toData(t).length;
	}

	@Override
	public void writeTo(ImageInfoModel t, Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {
		entityStream.write(toData(t));
	}

	/**
	 * Help class that does the actual serialization of a {@link ImageInfoModel} and
	 * returns the JSON data in bytes. JSON text is encoded in
	 * {@link StandardCharsets#UTF_8}.
	 * 
	 * @param t the object to turn into bytes of JSON text
	 * @return the serialized JSON bytes
	 */
	private byte[] toData(ImageInfoModel t) {
		Gson gson = new Gson();
		String jsonText = gson.toJson(t);
		System.out.println(jsonText);

		return jsonText.getBytes(StandardCharsets.UTF_8);
	}

}
