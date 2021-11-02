package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Random;
import java.util.ResourceBundle;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * Class models an listener by implementing {@link ServletContextListener} and
 * initializes the server on start. The initialization is done by creating a
 * connection pool and creating Polls and PollOption tables in database if not
 * already created and initialized.
 * 
 * @author Frano Rajič
 */
@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		ResourceBundle configuration = null;
		String user = null, password = null, connectionURL = null;
		try {

			File file = new File(sce.getServletContext().getRealPath("/"));
			System.out.println(sce.getServletContext().getRealPath("/"));
			System.out.println(sce.getServletContext().getRealPath(""));

			URL[] urls = { file.toURI().toURL() };
			ClassLoader loader = new URLClassLoader(urls);
			configuration = ResourceBundle.getBundle("WEB-INF//dbsettings", Locale.getDefault(), loader);

			String host = configuration.getString("host");
			String port = configuration.getString("port");
			String dbName = configuration.getString("name");

			connectionURL = "jdbc:derby://" + host + ":" + port + "/" + dbName;
			user = configuration.getString("user");
			password = configuration.getString("password");

		} catch (MissingResourceException | NullPointerException | MalformedURLException e) {
			throw new IllegalStateException(
					"Couldnt load resource bundle containing database properties. Consider this: " + e.getMessage());
		}

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);
		cpds.setUser(user);
		cpds.setPassword(password);
		cpds.setInitialPoolSize(5);
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);

		createPollTables(cpds);
		addDataToPollTables(cpds);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	/**
	 * Help method that initializes the polls with data if the Polls table was
	 * empty. The newly added data are two polls.
	 * 
	 * @param ds the datasource that is used to connect to database
	 */
	private void addDataToPollTables(DataSource ds) {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Statement pst = null;
		try {
			pst = con.createStatement();
			ResultSet rs = pst.executeQuery("SELECT * FROM Polls");
			if (!rs.next()) {
				//@formatter:off
				pst.executeUpdate("INSERT INTO Polls(title, message) VALUES('Glasanje za omiljeni bend:', 'Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!')", Statement.RETURN_GENERATED_KEYS);
				
				rs = pst.getGeneratedKeys();				
				try {
					if(rs != null && rs.next()) {
						long id1 = rs.getLong(1);
						pst.executeUpdate("INSERT INTO PollOptions(optionTitle, optionLink, pollID, votesCount) VALUES('The Mamas And The Papas', 'https://www.youtube.com/watch?v=N-aK6JnyFmk', "+id1+", 27)");
						pst.executeUpdate("INSERT INTO PollOptions(optionTitle, optionLink, pollID, votesCount) VALUES('The Everly Brothers', 'https://www.youtube.com/watch?v=tbU3zdAgiX8', "+id1+", 25)");
						pst.executeUpdate("INSERT INTO PollOptions(optionTitle, optionLink, pollID, votesCount) VALUES('The Beach Boys', 'https://www.youtube.com/watch?v=2s4slliAtQU', "+id1+", 150)");
						pst.executeUpdate("INSERT INTO PollOptions(optionTitle, optionLink, pollID, votesCount) VALUES('The Marcels', 'https://www.youtube.com/watch?v=qoi3TH59ZEs', "+id1+", 33)");
						pst.executeUpdate("INSERT INTO PollOptions(optionTitle, optionLink, pollID, votesCount) VALUES('The Four Seasons', 'https://www.youtube.com/watch?v=y8yvnqHmFds', "+id1+", 20)");
						pst.executeUpdate("INSERT INTO PollOptions(optionTitle, optionLink, pollID, votesCount) VALUES('The Platters', 'https://www.youtube.com/watch?v=H2di83WAOhU', "+id1+", 60)");
						pst.executeUpdate("INSERT INTO PollOptions(optionTitle, optionLink, pollID, votesCount) VALUES('The Beatles', 'https://www.youtube.com/watch?v=z9ypq6_5bsg', "+id1+", 150)");
					}
				} finally {
//					try {
//						rs.close();
//					} catch (Exception ignorable) {
//					}
					try { rs.close(); } catch(SQLException ex) {
						ex.printStackTrace();
					}
				}
				
				pst.executeUpdate("INSERT INTO Polls(title, message) VALUES('Favourite month od year:', 'What month of the year do you like the most? Refer to pics on link if unsure and pick one to vote!')", Statement.RETURN_GENERATED_KEYS);
				rs = pst.getGeneratedKeys();
				try {
					if(rs != null && rs.next()) {
						long id2 = rs.getLong(1);
						String[] months = new String[] {"january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december"};
						for(String m: months) {
							pst.executeUpdate("INSERT INTO PollOptions(optionTitle, optionLink, pollID, votesCount) VALUES('"+m+"', 'https://unsplash.com/search/photos/"+m+"', "+id2+", "+new Random().nextInt(90)+")");
						}	
					}
				} finally {
//					try {
//						rs.close();
//					} catch (Exception ignorable) {
//					}
					try { rs.close(); } catch(SQLException ex) {
						ex.printStackTrace();
					}
				}
				//@formatter:on
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		try {
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Private help method to create the Polls and PollOptions tables of database.
	 * The method will create the tables only if they do not exist.
	 * 
	 * @param ds the datasource that is used to connect to database
	 */
	private void createPollTables(DataSource ds) {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(
					"CREATE TABLE Polls\r\n" + "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n"
							+ "title VARCHAR(150) NOT NULL,\r\n" + "message CLOB(2048) NOT NULL\r\n" + ")");
			pst.execute();

		} catch (SQLException ex) {
			if (!ex.getSQLState().equals("X0Y32"))
				ex.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		try {
			pst = con.prepareStatement(
					"CREATE TABLE PollOptions\r\n" + "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n"
							+ "optionTitle VARCHAR(100) NOT NULL,\r\n" + "optionLink VARCHAR(150) NOT NULL,\r\n"
							+ "pollID BIGINT,\r\n" + "votesCount BIGINT,\r\n"
							+ "FOREIGN KEY (pollID) REFERENCES Polls(id)\r\n" + ")");
			pst.executeUpdate();

		} catch (SQLException ex) {
			if (!ex.getSQLState().equals("X0Y32"))
				ex.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		try {
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}