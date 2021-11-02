package hr.fer.zemris.java.webserver.init;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.webserver.provider.ServerProvider;

/**
 * Class that initializes the DAOProvider and prepares the persistence layer for
 * later use.
 * 
 * @author Frano Rajič
 */
@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		String tempRelative = ServerProvider.getRelativetemporaryimagerepository();
		Path temp = Paths.get(sce.getServletContext().getRealPath(tempRelative));

		String origRelative = ServerProvider.getRelativeoriginalimagerepository();
		Path orig = Paths.get(sce.getServletContext().getRealPath(origRelative));

		String infoRelative = ServerProvider.getRelativeimageinformationpath();
		Path info = Paths.get(sce.getServletContext().getRealPath(infoRelative));

		ServerProvider.initialize(temp, orig, info);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}