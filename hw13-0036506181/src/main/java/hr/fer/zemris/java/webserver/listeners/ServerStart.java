package hr.fer.zemris.java.webserver.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener of server context that tracks when an server is created and saves
 * the creation time to servlet's context attributes
 * 
 * @author Frano Rajič
 */
@WebListener
public class ServerStart implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Long time = System.currentTimeMillis();
		sce.getServletContext().setAttribute("creationTime", time);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
