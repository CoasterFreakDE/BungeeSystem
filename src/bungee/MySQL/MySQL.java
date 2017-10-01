package bungee.MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {
	public static Connection connection;

	public static void onConnect() {
		String prefix = "[BungeeSystem] ";

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + MySQLData.database + "?autoReconnect=true",
					MySQLData.username, MySQLData.password);
			System.out.println(prefix + "MySQL-Verbindung hergestellt!");

			// MySQL.onQuery("CREATE TABLE IF NOT EXISTS SPvP(UUID VARCHAR(50),
			// name VARCHAR(50), disname VARCHAR(50), nick INT(1), kills
			// INT(11), deaths INT(11), money INT(11), booster INT(11), Stein
			// INT(1), Schmied INT(1), Tank INT(1), Custom INT(1), BoosterSign
			// INT(1))");

			System.out.println(prefix + "MySQL aktiviert");
			onCreate();
		}

		catch (SQLException e) {
			e.printStackTrace();
			System.err.println(prefix + "MySQL-Verbindung konnte nicht hergestellt werden!");
			System.err.println(prefix + "MySQL wurde deaktiviert");
		}
	}

	public static void onCreate() {
		MySQL.onUpdate("CREATE TABLE IF NOT EXISTS CMS(UUID VARCHAR(255), name VARCHAR(16), disname VARCHAR(24), rank VARCHAR(40) DEFAULT 'Spieler', money INT DEFAULT 0, PremiumAuth INT(1) DEFAULT 1, time INT DEFAULT 0)");
		MySQL.onUpdate("CREATE TABLE IF NOT EXISTS bans(name VARCHAR(16), grund VARCHAR(255), zeit VARCHAR(255))");
		MySQL.onUpdate("CREATE TABLE IF NOT EXISTS ipbans(ip VARCHAR(255), grund VARCHAR(255), zeit VARCHAR(255))");
		MySQL.onUpdate("CREATE TABLE IF NOT EXISTS mute(name VARCHAR(16), grund VARCHAR(255), zeit VARCHAR(255))");
		MySQL.onUpdate("CREATE TABLE IF NOT EXISTS testrank(uuid VARCHAR(255), rank VARCHAR(40), until DATE, complete INT(1))");
		MySQL.onUpdate("CREATE TABLE IF NOT EXISTS Umfragen(id MEDIUMINT NOT NULL AUTO_INCREMENT, frage VARCHAR(255), ja INT(10) DEFAULT 0, nein INT(10) DEFAULT 0, PRIMARY KEY (id))");
		
		MySQL.onUpdate("CREATE TABLE IF NOT EXISTS reports(id INT AUTO_INCREMENT, reporter VARCHAR(16), reported VARCHAR(16), server VARCHAR(50), grund VARCHAR(255) DEFAULT 'Hacking', time datetime NOT NULL, moderator VARCHAR(25) DEFAULT 'none', status INT(1) DEFAULT 0, secret VARCHAR(16), PRIMARY KEY (id))");
	
		
		//V2
		MySQL.onUpdate("CREATE TABLE IF NOT EXISTS permissions(rank VARCHAR(40), permission VARCHAR(200))");
		MySQL.onUpdate("CREATE TABLE IF NOT EXISTS timeranks(uuid VARCHAR(255), rank VARCHAR(40), until TIMESTAMP)");
		MySQL.onUpdate("CREATE TABLE IF NOT EXISTS chatlog(time TIMESTAMP, uuid VARCHAR(64), name VARCHAR(32), server VARCHAR(32), nachricht VARCHAR(256))");
	
	}
	
	public static void onDisconect() {
		String prefix = "[BungeeSystem] ";

		if (connection != null) {
			try {
				connection.close();

				System.out.println(prefix + " MySQL-Verbindung beendet!");
			}

			catch (SQLException e) {
				e.printStackTrace();

				System.err.println(prefix + " MySQL-Verbindung konnte nicht getrennt werden!");
			}
		}
	}

	public static void onUpdate(String qry) {
		try {
			Statement stmt = connection.createStatement();

			stmt.executeUpdate(qry);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ResultSet onQuery(String qry) {
		ResultSet rs = null;

		try {
			Statement stmt = connection.createStatement();

			rs = stmt.executeQuery(qry);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}