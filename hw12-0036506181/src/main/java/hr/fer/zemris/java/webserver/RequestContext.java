package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Class that models an requested context. It provides the functionality of
 * storing cookies, parameters, persistent and temporary parameters, supports
 * encoding and finally writing the context to a given output stream
 * 
 * @author Frano Rajič
 */
public class RequestContext {

	/**
	 * The output stream used to write the context to
	 */
	private OutputStream outputStream;

	/**
	 * The charset used when creating context
	 */
	private Charset charset;

	/**
	 * The encoding used for context creation. It's a write-only property
	 */
	private String encoding = "UTF-8";

	/**
	 * The status code used for header creation. It's a write-only property
	 */
	private int statusCode = 200;

	/**
	 * The status text used for header creation. It's a write-only property
	 */
	private String statusText = "OK";

	/**
	 * The type of the context document that is specified when creating header. It's
	 * a write-only property.
	 */
	private String mimeType = "text/html";

	/**
	 * The length of the content. It's a write-only property
	 */
	private Long contentLength = null;

	/**
	 * A read only map that is mapping the parameter name to its value
	 */
	private Map<String, String> parameters;

	/**
	 * A read-write map that is mapping the temporary parameter name to its value
	 */
	private Map<String, String> temporaryParameters = new HashMap<>();

	/**
	 * A read-write map that is mapping the persistent parameter name to its value
	 */
	private Map<String, String> persistentParameters;

	/**
	 * A private list storing all the created {@link RCCookie} objects
	 */
	private List<RCCookie> outputCookies;

	/**
	 * An private property telling if the header has been generated.
	 */
	private boolean headerGenerated = false;

	/**
	 * A read only property storing the reference to the instance of
	 * {@link IDispatcher}
	 */
	private IDispatcher dispatcher;

	/**
	 * The session ID of this request
	 */
	private String SID = null;

	/**
	 * Create a new instance of {@link RequestContext}.
	 * 
	 * @param outputStream         An reference to the output stream. Cannot be
	 *                             null.
	 * @param parameters           An reference to parameters. If null then treated
	 *                             as empty.
	 * @param persistentParameters An reference to persistent parameters. If null
	 *                             then treated as empty.
	 * @param outputCookies        An reference to output cookies. If null then
	 *                             treated as empty.
	 * @throws NullPointerException if given outputStream is null
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		this.outputStream = Objects.requireNonNull(outputStream);
		this.parameters = (parameters != null ? parameters : new HashMap<>());
		this.persistentParameters = (persistentParameters != null ? persistentParameters : new HashMap<>());
		this.outputCookies = (outputCookies != null ? outputCookies : new LinkedList<>());
	}

	/**
	 * Create a new instance of {@link RequestContext}
	 * 
	 * @param outputStream         An reference to the output stream. Cannot be
	 *                             null.
	 * @param parameters           An reference to parameters. If null then treated
	 *                             as empty.
	 * @param persistentParameters An reference to persistent parameters. If null
	 *                             then treated as empty.
	 * @param outputCookies        An reference to output cookies. If null then
	 *                             treated as empty.
	 * @param temporaryParameters  the temporary parameters reference
	 * @param dispatcher           the dispatcher
	 * @param sessionID            the session id of this request
	 * @throws NullPointerException if given outputStream is null
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher, String sessionID) {
		this(outputStream, parameters, persistentParameters, outputCookies);

		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;

		this.SID = sessionID;
	}

	/**
	 * Class models an cookie that is used in the {@link RequestContext} when
	 * creating context.
	 * 
	 * @author Frano Rajič
	 */
	public static class RCCookie {

		/**
		 * The name of what the cookie is storing
		 */
		private final String name;

		/**
		 * The value of what the cookie is storing
		 */
		private final String value;

		/**
		 * The domain associated with cookie
		 */
		private final String domain;

		/**
		 * The path associated with cookie
		 */
		private final String path;

		/**
		 * The maximal age
		 */
		private final Integer maxAge;

		/**
		 * Is the cookie a httpOnly cookie
		 */
		private boolean httpOnly = false;

		/**
		 * Create an instance of a non-httpOnly {@link RCCookie}.
		 * 
		 * @param name     the name of the cookie. must be given.
		 * @param value    the value of the cookie. must be given.
		 * @param domain   the associated domain. can be null.
		 * @param path     the associated path. can be null.
		 * @param maxAge   the associated maximal age. can be null.
		 * @throws NullPointerException if given name and value are null pointers.
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this(name, value, maxAge, domain, path, false);
		}

		/**
		 * Create an instance of {@link RCCookie} with all necessary information given
		 * (including if it should be a httpOnly cookie).
		 * 
		 * @param name     the name of the cookie. must be given.
		 * @param value    the value of the cookie. must be given.
		 * @param domain   the associated domain. can be null.
		 * @param path     the associated path. can be null.
		 * @param maxAge   the associated maximal age. can be null.
		 * @param httpOnly true if the cookie a httpOnly cookie
		 * @throws NullPointerException if given name and value are null pointers.
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path, boolean httpOnly) {
			this.name = Objects.requireNonNull(name);
			this.value = Objects.requireNonNull(value);
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
			this.httpOnly = httpOnly;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @return the domain
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * @return the path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * @return the maxAge
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

		/**
		 * @return the httpOnly
		 */
		public boolean isHttpOnly() {
			return httpOnly;
		}

	}

	/**
	 * @param encoding the encoding to set
	 * @throws RuntimeException if header has already been created
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated)
			throw new RuntimeException("Header was already created.");

		this.encoding = encoding;
	}

	/**
	 * @param statusCode the statusCode to set
	 * @throws RuntimeException if header has already been created
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated)
			throw new RuntimeException("Header was already created.");

		this.statusCode = statusCode;
	}

	/**
	 * @param statusText the statusText to set
	 * @throws RuntimeException if header has already been created
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated)
			throw new RuntimeException("Header was already created.");

		this.statusText = statusText;
	}

	/**
	 * @param mimeType the mimeType to set
	 * @throws RuntimeException if header has already been created
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated)
			throw new RuntimeException("Header was already created!");

		this.mimeType = mimeType;
	}

	/**
	 * @param contentLength the contentLength to set
	 * @throws RuntimeException if header has already been created
	 */
	public void setContentLength(Long contentLength) {
		if (headerGenerated)
			throw new RuntimeException("Header was already created!");

		this.contentLength = contentLength;
	}

	/**
	 * Method that adds the given cookie to output cookies of this context.
	 * 
	 * @param cookie the cookie to add
	 * @throws NullPointerException if given cookie is null
	 * @throws RuntimeException     if header has already been created
	 */
	public void addRCCookie(RCCookie cookie) {
		if (headerGenerated)
			throw new RuntimeException("Header was already created!");

		outputCookies.add(Objects.requireNonNull(cookie));
	}

	/**
	 * Get the dispatcher
	 * 
	 * @return the dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * Method that retrieves value from parameters map (or null if no association
	 * exists):
	 * 
	 * @param name the name of the parameter
	 * @return the value of the parameter
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in parameters map. The returned
	 * set is read-only.
	 * 
	 * @return a read-only set of parameter names
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Method that retrieves value from persistentParameters map or returns null if
	 * no association exists
	 * 
	 * @param name the name of the parameter to look for
	 * @return the associated value of the given name
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in the persistent parameters
	 * map
	 * 
	 * @return a read-only set of persistent parameter names
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * method that stores a value to persistentParameters map:
	 * 
	 * @param name  the name of the parameter to set
	 * @param value the value of the parameter
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Method that removes a value from persistentParameters map. If no mapping with
	 * given name as key exists, than nothing is done.
	 * 
	 * @param name the name of the persistent parameter to remove
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Method that retrieves the value from temporaryParameters map or returns null
	 * if no association exists.
	 * 
	 * @param name the name of the temporary parameter
	 * @return the associated value
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in temporary parameters map.
	 * 
	 * @return a read-only set of temporary parameter names
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * Method that retrieves an identifier which is unique for current user session
	 * 
	 * @return the session id
	 */
	public String getSessionID() {
		return SID;
	}

	/**
	 * Method that stores a value to temporaryParameters map.
	 * 
	 * @param name  the name of the parameter
	 * @param value the value of the parameter
	 * @throws NullPointerException if given name is null
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * Method that removes a value from temporaryParameters map. If no parameter of
	 * that name exists, nothing is done.
	 * 
	 * @param name the name of the temporary parameter to remove
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Method to write the given data to outputStream that was given to
	 * {@link RequestContext} in constructor.
	 * 
	 * @param data the data to write
	 * @return this
	 * @throws IOException if exception occurs while writing to output
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated)
			writeHeader();

		outputStream.write(data);
		return this;
	}

	/**
	 * Method to write the given data to outputStream that was given to
	 * {@link RequestContext} in constructor.
	 * 
	 * @param data   the data to write
	 * @param offset from where to start writing
	 * @param len    how many bytes should be written
	 * @return this
	 * @throws IOException if exception occurs while writing to output
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if (!headerGenerated)
			writeHeader();

		outputStream.write(data, offset, len);
		return this;
	}

	/**
	 * Method to write the text string to outputStream that was given to
	 * {@link RequestContext} in constructor. The charset used is the same that has
	 * been to used to write when creating header.
	 * 
	 * @param text the text to write
	 * @return this
	 * @throws IOException if exception occurs while writing to output
	 */
	public RequestContext write(String text) throws IOException {
		if (!headerGenerated)
			writeHeader();

		write(text.getBytes(charset));
		return this;
	}

	/**
	 * Help method to write the header
	 * 
	 * @throws IOException if exception occurs while writing to output
	 */
	private void writeHeader() throws IOException {
		charset = Charset.forName(encoding);
		StringBuilder sb = new StringBuilder();

		sb.append("HTTP/1.1 " + statusCode + " " + statusText);
		sb.append("\r\n");

		sb.append("Content-Type: " + mimeType);
		if (mimeType.startsWith("text")) {
			sb.append("; charset=" + encoding);
		}
		sb.append("\r\n");

		if (contentLength != null) {
			sb.append("Content-Length: " + contentLength + "\r\n");
		}

		outputCookies.forEach(x -> {
			sb.append("Set-Cookie: ");
			sb.append(x.getName() + "=\"" + x.getValue() + "\"");
			if (x.getDomain() != null) {
				sb.append("; Domain=" + x.getDomain());
			}
			if (x.getPath() != null) {
				sb.append("; Path=" + x.getPath());
			}
			if (x.getMaxAge() != null) {
				sb.append("; Max-age=" + x.getMaxAge());
			}
			if (x.isHttpOnly()) {
				sb.append("; HttpOnly");
			}
			sb.append("\r\n");
		});

		sb.append("\r\n");

		outputStream.write(sb.toString().getBytes(StandardCharsets.ISO_8859_1));
		outputStream.flush();

		headerGenerated = true;
	}

}
