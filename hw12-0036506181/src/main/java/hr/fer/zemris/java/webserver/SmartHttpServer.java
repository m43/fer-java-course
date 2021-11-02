package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Simple server that handles HTTP version 1.0 and 1.1 requests. The server is
 * configured by the given server.properties file, as well as mimeConfig and
 * workers configuration files. Servers uses {@link SmartScriptEngine} to render
 * documents like HTML files using specific scripts. Servers runs a thread pool
 * defined in properties, on a port, address, domain, document root and with a
 * session - all defined in the server.properties. For parsing requests, workers
 * can be used that implement {@link IWebWorker} and that are by convention
 * located in workers subpackage and accessed by /ext/WorkerName. Workers can be
 * accessed by configuration as defined in the workers.properties configuration
 * file. Server uses the the model of requests as implemented by
 * {@link RequestContext}. Example of configurations: <br>
 * <br>
 * <br>
 * ---> server.propertis:<br>
 * # On which address server listens?<br>
 * server.address = 127.0.0.1<br>
 * # What is the domain name of our web server?<br>
 * server.domainName = www.localhost.com<br>
 * # On which port server listens?<br>
 * server.port = 5721<br>
 * # How many threads should we use for thread pool?<br>
 * server.workerThreads = 8<br>
 * # What is the path to root directory from which we serve files?<br>
 * server.documentRoot = ./webroot<br>
 * # What is the path to configuration file for extension to mime-type
 * mappings?<br>
 * server.mimeConfig = ./config/mime.properties<br>
 * # What is the duration of user sessions in seconds? As configured, it is 10
 * minutes.<br>
 * session.timeout = 600<br>
 * # What is the path to configuration file for url to worker mappings?<br>
 * server.workers = ./config/workers.properties<br>
 * <br>
 * <br>
 * ---> mimeConfig.properties:<br>
 * html = text/html<br>
 * htm = text/html<br>
 * txt = text/plain<br>
 * gif = image/gif<br>
 * png = image/png<br>
 * jpg = image/jpg<br>
 * <br>
 * <br>
 * ---> mimeConfig.properties:<br>
 * /hello = hr.fer.zemris.java.webserver.workers.HelloWorker<br>
 * /cw = hr.fer.zemris.java.webserver.workers.CircleWorker<br>
 * /calc = hr.fer.zemris.java.webserver.workers.SumWorker<br>
 * /index2.html = hr.fer.zemris.java.webserver.workers.Home<br>
 * /setbgcolor = hr.fer.zemris.java.webserver.workers.BgColorWorker * <br>
 * <br>
 * <br>
 * Use the server with care.
 * 
 * @author Frano Rajič
 */
public class SmartHttpServer {

	/**
	 * Timeout of socket when accpeting new requests
	 */
	private static final int SOCKET_TIMEOUT = 10000;

	/**
	 * The address of the server
	 */
	private String address;

	/**
	 * The ip in bytes
	 */
	private byte[] ip = new byte[4];

	/**
	 * The domain name of the server
	 */
	private String domainName;

	/**
	 * The port to which the server listens to
	 */
	private int port;

	/**
	 * How many threads should be used for pool
	 */
	private int workerThreads;

	/**
	 * What is the duration of user sessions in seconds
	 */
	private int sessionTimeout;

	/**
	 * Map used for mapping workers
	 */
	private Map<String, IWebWorker> workersMap = new HashMap<>();

	/**
	 * Extension to mime-type mapping
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();

	/**
	 * The thread used for running the server inside
	 */
	private ServerThread serverThread = new ServerThread();

	/**
	 * Thread used to remove expired sessions
	 */
	private SessionSweeper sweeperThread = new SessionSweeper();

	/**
	 * The pool that the servers uses to run multiple workers in parallel. Every
	 * worker works on a single request.
	 */
	private ExecutorService threadPool;

	/**
	 * Is the server currently running
	 */
	private boolean running = false;

	/**
	 * What is the path to the root directory from which to serve files
	 */
	private Path documentRoot;

	/**
	 * Mapping of session ID's to respective session entries
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();

	/**
	 * Random number generator that is used to generate random session ID's
	 */
	private Random sessionRandom = new Random();

	/**
	 * Create a new {@link SmartHttpServer} with given configuration file path.
	 * 
	 * @param configFileName the path to the main server configuration file
	 * @throws IllegalArgumentException if invalid configuration is given
	 */
	public SmartHttpServer(String configFileName) {
		try {
			Properties configFile = new Properties();
			configFile.load(Files.newInputStream(Paths.get(configFileName)));

			this.address = configFile.getProperty("server.address");
			int i = 0;
			for (String s : address.split(".")) {

				int number = Integer.valueOf(s);
				if (i == 4 || number < 0 || number > 255) {
					throw new IllegalArgumentException("Invalid ip given in config file.");
				}
				ip[i++] = (byte) number;
			}

			this.domainName = configFile.getProperty("server.domainName");
			this.port = Integer.valueOf(configFile.getProperty("server.port"));
			this.workerThreads = Integer.valueOf(configFile.getProperty("server.workerThreads"));
			this.sessionTimeout = Integer.valueOf(configFile.getProperty("session.timeout"));
			this.documentRoot = Paths.get(configFile.getProperty("server.documentRoot")).normalize();

			Properties mimeConfig = new Properties();
			mimeConfig.load(Files.newInputStream(Paths.get(configFile.getProperty("server.mimeConfig"))));
			for (String mime : mimeConfig.stringPropertyNames()) {
				mimeTypes.put(mime, mimeConfig.getProperty(mime));
			}

			Properties workerConfig = new Properties();
			workerConfig.load(Files.newInputStream(Paths.get(configFile.getProperty("server.workers"))));
			if ((workerConfig.values().stream().map(x -> String.valueOf(x))).collect(Collectors.toSet())
					.size() != workerConfig.size()) {
				throw new IllegalArgumentException("Worker config file invalid!");
			}

			try {
				for (String workerKey : workerConfig.stringPropertyNames()) {
					String fqcn = workerConfig.getProperty(workerKey);

					Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
					@SuppressWarnings("deprecation")
					Object newObject = referenceToClass.newInstance();
					IWebWorker iww = (IWebWorker) newObject;

					// substring works cause key cannot be empty if properties loaded successfully
					workersMap.put(workerKey.substring(1), iww);
				}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException
					| InvalidPathException e) {
				throw new IllegalArgumentException("Worker config file invalid!");
			}

		} catch (NumberFormatException | IOException e) {
			throw new IllegalArgumentException(
					"Problem with given configuration file, consider the following message: " + e.getMessage());
		}
	}

	/**
	 * Start the server.
	 */
	protected synchronized void start() {
		if (running) {
			return;
		}

		serverThread.start();
		sweeperThread.start();
		threadPool = Executors.newFixedThreadPool(workerThreads);

		running = true;
	}

	/**
	 * Stop the server.
	 */
	protected synchronized void stop() {
		if (!running) {
			throw new IllegalStateException("Cannot stop server that is not running!");
		}

		serverThread.interrupt();
		sweeperThread.interrupt();
		try {
			serverThread.join();
		} catch (InterruptedException ignoreable) {
			throw new IllegalArgumentException("Unexpected interrupt!");
		}
		threadPool.shutdown();

		running = false;
	}

	/**
	 * Thread that is run when server starts.
	 * 
	 * @author Frano Rajič
	 */
	protected class ServerThread extends Thread {
		@Override
		public void run() {

			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket();
				serverSocket.setSoTimeout(SOCKET_TIMEOUT);
				serverSocket.bind(new InetSocketAddress(InetAddress.getByAddress(domainName, ip), port));

				while (!isInterrupted()) {
					try {
						Socket client = serverSocket.accept();
						ClientWorker cw = new ClientWorker(client);
						threadPool.submit(cw);
					} catch (java.io.InterruptedIOException ignoreable) {
						// accept timed out..
					}
				}
			} catch (IOException e) {
				throw new IllegalStateException(
						"Problem while running server occured. consider the following message: " + e.getMessage());
			}

			if (serverSocket != null && serverSocket.isClosed()) {
				try {
					serverSocket.close();
				} catch (IOException ignoreable) {
				}
			}
		}

	}

	/**
	 * Help method containing an simple automata that takes an input stream and
	 * reads the request from it.
	 * 
	 * @param is the input stream
	 * @return the request as an byte array
	 * @throws IOException thrown if any error occurs while reading from given input
	 *                     stream
	 */
	private static byte[] readRequest(InputStream is) throws IOException {
		// @formatter:off

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int state = 0;
l:		while(true) {
			int b = is.read();
			if(b==-1) return null;
			if(b!=13) {
				bos.write(b);
			}
			switch(state) {
			case 0: 
				if(b==13) { state=1; } else if(b==10) state=4;
				break;
			case 1: 
				if(b==10) { state=2; } else state=0;
				break;
			case 2: 
				if(b==13) { state=3; } else state=0;
				break;
			case 3: 
				if(b==10) { break l; } else state=0;
				break;
			case 4: 
				if(b==10) { break l; } else state=0;
				break;
			}
		}
		return bos.toByteArray();
		// @formatter:on
	}

	/**
	 * Method used to extract the requests headers from given request header string
	 * 
	 * @param requestHeader the request headers
	 * @return an list of found headers
	 */
	private static List<String> extractHeaders(String requestHeader) {
		List<String> headers = new ArrayList<String>();
		String currentLine = null;
		for (String s : requestHeader.split("\n")) {
			if (s.isEmpty())
				break;
			char c = s.charAt(0);
			if (c == 9 || c == 32) {
				currentLine += s;
			} else {
				if (currentLine != null) {
					headers.add(currentLine);
				}
				currentLine = s;
			}
		}
		if (!currentLine.isEmpty()) {
			headers.add(currentLine);
		}
		return headers;
	}

	/**
	 * Method used to send response without body
	 * 
	 * @param cos        the output stream to which the response needs to be sent
	 * @param statusCode the status code of the response
	 * @param statusText the status text of the response
	 * @throws IOException thrown if any error while writing to output stream occurs
	 */
	private static void sendError(OutputStream cos, int statusCode, String statusText) throws IOException {
		// @formatter:off
		cos.write(
			("HTTP/1.1 "+statusCode+" "+statusText+"\r\n"+
			"Content-Type: text/plain;charset=UTF-8\r\n"+
			"Content-Length: 0\r\n"+
			"Connection: close\r\n"+
			"\r\n").getBytes(StandardCharsets.US_ASCII)
		);
		cos.flush();
		// @formatter:on
	}

	/**
	 * Help class that models an session with client by storing relevant session
	 * information.
	 * 
	 * @author Frano Rajič
	 */
	private static class SessionMapEntry {
		/**
		 * An identifier of the session - the session ID
		 */
		@SuppressWarnings("unused") // a init. je dolje, ali doista se nigdi ne koristi. ostavit cu ga zbog upute
		String sid;
		/**
		 * To which server host is this session linked
		 */
		String host;
		/**
		 * Until when is this session entry valid
		 */
		long validUntil;
		/**
		 * Mapping of key value pairs that are stored inside the session (aka inside the
		 * cookie)
		 */
		Map<String, String> map;
	}

	/**
	 * Help class that sweeps expired sessions from sessions map
	 * 
	 * @author Frano Rajič
	 */
	private class SessionSweeper extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					long now = new Date().getTime();
					sessions.entrySet().removeIf(e -> e.getValue().validUntil < now);
					Thread.sleep(5 * 60 * 1000);
				} catch (InterruptedException e) {
					break;
				}
			}
		}
	}

	/**
	 * Help class that models an worker that handles requests. Inside
	 * {@link SmartHttpServer} it is used to handle all the received requests in new
	 * threads (that are contained inside servers thread pool).
	 * 
	 * @author Frano Rajič
	 */
	private class ClientWorker implements Runnable, IDispatcher {

		/**
		 * The prefix of the requested path that determines that a conventional worker
		 * is asked for
		 */
		private static final String CONVENTION_WORKER = "ext/";

		/**
		 * If no mime could be recognized by server, this is the default one
		 */
		private static final String DEFAULT_MIME = "application/octet-stream";

		/**
		 * The socket which contains the request and awaits for response
		 */
		private Socket csocket;

		/**
		 * The input stream of the given socket
		 */
		private PushbackInputStream istream;

		/**
		 * The output stream of the given socket
		 */
		private OutputStream ostream;

		/**
		 * The HTTPS protocol version
		 */
		private String version;

		/**
		 * The method of the request
		 */
		private String method;

		/**
		 * The host that sent the request
		 */
		private String host;

		/**
		 * Mapping of parameters to respective values. The parameters is what is found
		 * at the end of request URL in format: key=value&key2=value&(...)&keyN=valueN,
		 * for example http://127.0.0.1:5721/abc/def?name=joe&country=usa
		 */
		private Map<String, String> params = new HashMap<String, String>();

		/**
		 * The temporary parameters. Used for temporary information exchange of workers
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();

		/**
		 * The persistent parameters store the cookie parameters, the parameters that
		 * belong to a specific session
		 */
		private Map<String, String> persParams = new HashMap<String, String>();

		/**
		 * The output cookies used in {@link RequestContext}
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();

		/**
		 * The session ID is a string of SID_LENGTH uppercase letters
		 */
		private String SID;

		/**
		 * The length of the generated session ID
		 */
		private static final int SID_LENGTH = 26;

		/**
		 * The context of the request
		 */
		private RequestContext context = null;

		/**
		 * The package in which workers are looked for if used conventional approach
		 */
		private String workerNamePackage = "hr.fer.zemris.java.webserver.workers";

		/**
		 * Create a new {@link ClientWorker} with given socket.
		 * 
		 * @param csocket the socket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				// obtain input stream from socket
				istream = new PushbackInputStream(csocket.getInputStream());

				// obtain output stream from socket
				ostream = new BufferedOutputStream(csocket.getOutputStream());

				// Then read complete request header from your client in separate method...
				byte[] request = readRequest(istream);
				if (request == null) {
					sendError(ostream, 400, "Bad request");
					return;
				}
				String requestStr = new String(request, StandardCharsets.US_ASCII);

				List<String> headers = extractHeaders(requestStr);
				String[] firstLine = headers.isEmpty() ? null : headers.get(0).split(" ");

				if (firstLine == null || firstLine.length != 3) {
					sendError(ostream, 400, "Bad request");
					return;
				}

				method = firstLine[0].toUpperCase();
				version = firstLine[2].toUpperCase();
				if (!method.equals("GET") || (!version.equals("HTTP/1.1") && !version.equals("HTTPS/1.0"))) {
					sendError(ostream, 400, "Bad request");
					return;
				}

				checkHost(headers);
				checkSession(headers);

				String[] requestPathParts = firstLine[1].split("\\?", 2);
				if (requestPathParts.length == 0) {
					sendError(ostream, 400, "Bad request");
					return;
				}
				String path = requestPathParts[0];
				if (requestPathParts.length == 2) {
					String paramString = requestPathParts[1];
					if (!parseParameters(paramString)) {
						sendError(ostream, 400, "Bad request");
						return;
					}
				}

				internalDispatchRequest(path, true);

			} catch (Exception ex) {
				try {
					sendError(ostream, 400, "Bad request");
				} catch (IOException ignoreable) {
				}
				// System.out.println("Worker error: " + ex.getClass());
				// ex.printStackTrace();
			} finally {
				try {
					ostream.flush();
					csocket.close();
				} catch (IOException ignoreable) {
				}
			}
		}

		/**
		 * Help method to check the given header lines and update the {@link #host}
		 * accordingly. If no host found in headers, the servers default
		 * {@link SmartHttpServer#domainName} is used instead.
		 * 
		 * @param headers headers with host.
		 */
		private void checkHost(List<String> headers) {
			// Go through headers, and if there is header “Host: xxx”, assign host property
			// Besides that, look for cookies header
			for (String header : headers) {
				if (host == null && header.startsWith("Host:")) {
					String hostString = header.split(":", 2)[1].trim();

					// If xxx is of form some-name:number, just remember “some-name”-part
					if (hostString.contains(":")) {
						hostString = hostString.split(":", 2)[0];
					}

					host = hostString;
				}
				if (SID == null && header.startsWith("Cookie:")) {
					String cookiesString = header.split(":", 2)[1].trim();
					String[] parts = cookiesString.split(";");

					// looking for sid=xxx
					for (String part : parts) {
						if (part.startsWith("sid=")) {
							SID = part.split("=")[1];
							if (SID.startsWith("\"")) {
								SID = SID.substring(1);
							}
							if (SID.endsWith("\"")) {
								SID = SID.substring(0, SID.length() - 1);
							}
							break;
						}
					}
				}
			}

			if (host == null) {
				host = domainName;
			}
		}

		/**
		 * Help method that checks given headers and updates the session id and sessions
		 * map accordingly
		 * 
		 * @param headers the headers that should (but not need to) contain the cookie
		 *                with session id. the header line could be like this <br>
		 *                Cookie: ...; sid="AAA..AAA"; ...
		 */
		private void checkSession(List<String> headers) {

			for (String header : headers) {
				if (header.startsWith("Cookie:")) {
					String cookiesString = header.split(":", 2)[1].trim();
					String[] parts = cookiesString.split(";");

					// looking for sid=xxx
					for (String part : parts) {
						if (part.trim().startsWith("sid=")) {
							SID = part.trim().split("=")[1];
							if (SID.startsWith("\"")) {
								SID = SID.substring(1);
							}
							if (SID.endsWith("\"")) {
								SID = SID.substring(0, SID.length() - 1);
							}
							break;
						}
					}
				}
			}

			synchronized (SmartHttpServer.this) {
				SessionMapEntry entry = sessions.get(SID);

				// generate an SID if this one has expired or is null or has invalid host
				if (entry == null || entry.validUntil < new Date().getTime() || !host.equals(entry.host)) {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < SID_LENGTH; i++) {
						sb.append("A" + sessionRandom.nextInt(26));
					}
					SID = sb.toString();

					// create an session map entry for created SID
					entry = new SessionMapEntry();
					entry.sid = SID;
					entry.host = host;
					entry.map = new ConcurrentHashMap<String, String>();

					// add created entry to sessions
					sessions.put(SID, entry);
				}

				// load the respective persistent parameters from session entry
				persParams = entry.map;

				// update validity of SID to now + sessionTimeout
				entry.validUntil = new Date().getTime() + sessionTimeout * 1000;

				// add cookie with sid so that the created RequestContext is aware of it and can
				// communicate it back to client
				outputCookies.add(new RCCookie("sid", SID, null, host, "/", true));
			}
		}

		/**
		 * Help method used to parse given parameters string and to put found parameters
		 * into {@link #params} map.
		 * 
		 * @param paramString the string containing the parameters
		 * @return true if all parameters are valid and were added successfully and
		 *         without any error
		 */
		private boolean parseParameters(String paramString) {
			if (paramString.isEmpty()) {
				return true;
			}
			String[] parts = paramString.split("&");

			for (String p : parts) {
				String[] paramParts = p.split("=", 2);
				if (paramParts.length != 2) {
					return false;
				}

				params.put(paramParts[0], paramParts[1]);
			}

			return true;
		}

		/**
		 * Internal method that handles the actual dispatching of the request given by
		 * URL path
		 * 
		 * @param urlPath    URL path of request
		 * @param directCall was this method called directly?
		 * @throws Exception thrown if any error occurs
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			urlPath = urlPath.startsWith("/") ? urlPath.substring(1) : urlPath; // get rid of the leading /
			Path requestedFile = documentRoot.resolve(urlPath);

			// is the path under the document root, or is it rather above it which is
			// unacceptable
			if (!requestedFile.normalize().startsWith(documentRoot)) {
				// who tried this? Write info about attacker to database...
				sendError(ostream, 403, "Forbidden");
				return;
			}

			// was the restricted private subfolder asked for
			if (directCall && (urlPath.toString().startsWith("private/") || urlPath.toString().startsWith("private"))) {
				sendError(ostream, 404, "Not found");
			}

			// is the requested path a call for an worker by convention
			if (urlPath.toString().startsWith(CONVENTION_WORKER)) {
				String requestedWorker = urlPath.substring(CONVENTION_WORKER.length());
				if (requestedWorker.isEmpty()) {
					sendError(ostream, 404, "Not found");
					return;
				}

				IWebWorker iww = workersMap.get(CONVENTION_WORKER + requestedWorker);
				if (iww == null) {
					try {
						String fqcn = workerNamePackage + "." + requestedWorker;

						Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
						@SuppressWarnings("deprecation")
						Object newObject = referenceToClass.newInstance();
						iww = (IWebWorker) newObject;

						// lazy - every worker gets loaded only once
						workersMap.put(CONVENTION_WORKER + requestedWorker, iww);
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| ClassCastException e) {
						sendError(ostream, 404, "Not found");
						return;
					}
				}

				if (context == null) {
					context = new RequestContext(ostream, params, persParams, outputCookies, tempParams, this, SID);
				}
				iww.processRequest(context);
				return;
			}

			// is the requested path an direct call to a worker configured on server start
			for (String worker : workersMap.keySet()) {
				if (requestedFile.normalize().startsWith(documentRoot.resolve(worker))) {
					if (context == null) {
						context = new RequestContext(ostream, params, persParams, outputCookies, tempParams, this, SID);
					}
					workersMap.get(worker).processRequest(context);
					return;
				}
			}

			// as no worker took the job, assume that a file needs to be served
			if (!Files.isReadable(requestedFile)) {
				sendError(ostream, 404, "File not found");
				return;
			}

			if (!Files.isRegularFile(requestedFile)) {
				sendError(ostream, 404, "File not found");
				return;
			}

			// configure mime or call #sendSms if a SMSCR script was found
			String[] fileNameParts = requestedFile.getFileName().toString().split("\\.");
			String mime = null;
			if (fileNameParts.length > 1) {
				if ("smscr".equals(fileNameParts[fileNameParts.length - 1])) {
					sendSms(requestedFile);
					return;
				}
				mime = mimeTypes.get(fileNameParts[fileNameParts.length - 1]);
			}

			// no mime defaults to DEFAULT_MIME
			if (mime == null) {
				mime = DEFAULT_MIME;
			}

			// create a context and prepare for file delivery
			if (context == null) {
				context = new RequestContext(ostream, params, persParams, outputCookies, tempParams, this, SID);
			}
			context.setMimeType(mime);
			context.setStatusCode(200);

			// deliver file by either using the SmartScriptEngine or by sending it as it is
			if ("text/html".equals(mime)) {
				String docBody = Files.readString(requestedFile);
				new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(), context).execute();
			} else {
				context.setContentLength((long) Files.size(requestedFile));
				BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(requestedFile));
				byte[] buf = new byte[1024];
				while (true) {
					int r = bis.read(buf);
					if (r < 1)
						break;
					context.write(buf, 0, r);
				}
			}
		}

		/**
		 * Help method to run engine on requested SMSRC script
		 * 
		 * @param requestedFile the path to the requested script
		 * @throws Exception if anything goes wrong
		 */
		private void sendSms(Path requestedFile) throws Exception {
			String smsBody = Files.readString(requestedFile);
			if (context == null) {
				context = new RequestContext(ostream, params, persParams, outputCookies, tempParams, this, SID);
			}

			new SmartScriptEngine(new SmartScriptParser(smsBody).getDocumentNode(), context).execute();
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

	}

	/**
	 * Entry point for server start.
	 * 
	 * @param args path to server.properties should be provided
	 * @throws IOException thrown if any error with creating server occurs. caused
	 *                     by invalid properties
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Provide path to server.properties!");
			return;
		}

		SmartHttpServer server = new SmartHttpServer(args[0]);

		server.start();
	}
}