package fr.gillouard.tdvelo.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataSource {

	/** LOGGER. **/
	private static final Log LOG = LogFactory.getLog(DataSource.class);
	private Properties prop = new Properties();

	private static DataSource datasource = null;

	private DataSource() {
	}

	public static DataSource getInstance() {
		if (datasource == null) {
			datasource = new DataSource();
			datasource.init();
		}
		return datasource;
	}

	/**
	 * Initialisation
	 */
	private void init() {

		try {
			prop.load(this.getClass().getClassLoader()
					.getResourceAsStream("TDVelo.properties"));

			Class.forName(prop.getProperty("db.driver")).newInstance();

		} catch (final Exception e) {
			LOG.error(
					"Erreur lors de l'initialisation de la connection a la base !",
					e);
		}

	}

	/**
	 * @return the connection
	 */
	public Connection getConnection() {

		Connection conn = null;

		try {

			conn = DriverManager.getConnection(prop.getProperty("db.url"),
					prop.getProperty("db.user"),
					prop.getProperty("db.password"));

		} catch (final Exception e) {
			LOG.error(
					"Erreur lors de la recuperation de la connection a la base !",
					e);
		}
		return conn;

	}
}
