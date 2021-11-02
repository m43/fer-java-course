package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class models an worker by implementing {@link IWebWorker} and is
 * supposed to produce an HTML file containing all the parameters that were
 * given in request
 * 
 * @author Frano Rajič
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append("<!DOCTYPE html>\n<html>\n<head>\n<title>Parameters</title><style>\r\n"
				+ "table {\n  font-family: arial, sans-serif;\n  border-collapse: collapse;\n  width: 100%;\n}\r\n"
				+ "td, th {\n  border: 1px solid #dddddd;\n  text-align: left;\n  padding: 8px;\n}\r\n"
				+ "tr:nth-child(even) {\n  background-color: #dddddd;\n}\r\n"
				+ "</style>\n</head>\n<body>\n<h2>Given parameters</h2>\r\n<table>");

		for (String parameter : context.getParameterNames()) {
			sb.append(String.format("<tr>\r\n<td>%s</td>\r\n<td>%s</td>\r\n</tr>\n\r", parameter, context.getParameter(parameter)));
		}

		sb.append("</table>\r\n</body>\r\n</html>");

		context.setMimeType("text/html");
		context.setContentLength((long) sb.length());
		context.write(sb.toString());
	}
}