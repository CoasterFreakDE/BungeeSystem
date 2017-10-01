package bungee.MySQL;

import bungee.Main.main;
import net.md_5.bungee.config.Configuration;

public class MySQLData {

	public static String database;
	public static String username;
	public static String password;

	public MySQLData() {
		Configuration mysql = main.getMysql();

		if (mysql.getString("database") == null) {
			mysql.set("database", "YourDataBase");
			main.saveMySQLConfig();
		}

		if (mysql.getString("username") == null) {
			mysql.set("username", "YourUsername");
			main.saveMySQLConfig();
		}

		if (mysql.getString("password") == null) {
			mysql.set("password", "YourPassword");
			main.saveMySQLConfig();
		}

		database = mysql.getString("database");
		username = mysql.getString("username");
		password = mysql.getString("password");

		if (mysql.getString("password").equalsIgnoreCase("none")) {
			password = "";
		}

	}

}
