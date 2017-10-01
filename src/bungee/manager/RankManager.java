package bungee.manager;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import bungee.Main.main;
import bungee.MySQL.MySQL;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;

public class RankManager
{

	
	public static boolean hasPermission(ProxiedPlayer p, String permission) {
		ResultSet set = MySQL.onQuery("SELECT * FROM permissions WHERE rank = '" + getRank(p) + "'");
		
		try {
			while(set.next()) {
				String perm = set.getString("permission");
				if(permission.equalsIgnoreCase(perm) || perm.equals("*")) {
					return true;
				}
			}
		}catch(SQLException ex) { }
		
		return false;
	}
	
	public static void addPermission(String rank, String permission) {
		MySQL.onUpdate("INSERT INTO permissions(rank, permission) VALUES('" + rank + "', '" + permission + "')");
	}
	
	public static void removePermission(String rank, String permission) {
		MySQL.onUpdate("DELETE FROM permissions WHERE rank = '" + rank + "' AND permission = '" + permission + "')");
	}
	
	public static void checkTimeRanks() {
		BungeeCord.getInstance().getScheduler().runAsync(main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				ResultSet set = MySQL.onQuery("SELECT * FROM timeranks WHERE TIMEDIFF(CURRENT_TIMESTAMP, until) > 0");
				
				try {
					while(set.next()) {
						String uuid = set.getString("uuid");
						MySQL.onUpdate("DELETE FROM timeranks WHERE uuid = '" + uuid + "'");
						setRank(BungeeCord.getInstance().getPlayer(UUID.fromString(uuid)), "Spieler");
					}
				}catch(SQLException ex) { }
			}
		});
	}
	
	public static void setTimedRank(String uuid, String rank, int time) {
		
		if(time == -1) {
			MySQL.onUpdate("DELETE FROM timeranks WHERE uuid = '" + uuid + "'");
		}
		else {
			ResultSet set = MySQL.onQuery("SELECT * FROM timeranks WHERE uuid = '" + uuid + "'");
			
			try {
				if(set.next()) {
					MySQL.onUpdate("UPDATE timeranks SET until = DATE_ADD(until, INTERVAL " + time + " MONTH) WHERE uuid = '" + uuid + "'");
				}
				else {
					MySQL.onUpdate("INSERT INTO timeranks(uuid, rank, until) VALUES('" + uuid + "', '" + rank + "', DATE_ADD(now(), INTERVAL " + time + " MONTH))");
				}
			}catch(SQLException ex) { }
		}
		
		setRank(BungeeCord.getInstance().getPlayer(UUID.fromString(uuid)), rank);
	}
	
	public static String getRankwithColor(String p) {
		
		String rank = getRank(p);
		
		if(rank == null) {
			rank = "Spieler";
		}
			
		Configuration players = main.getConfiguration();
		
		if(players.getSection("RankColors").getString(rank) != null) {
			rank = players.getString("RankColors." + rank) + rank;
		}
		else {
			players.set("RankColors." + rank, "&8");
			main.saveConfig();
			
			rank = "&8" + rank;
		}
		
		return rank;
	}
	
	public static String getColor(String p) {
		
		String rank = getRank(p);
		String color = "§a";
		
		if(rank == null) {
			rank = "Spieler";
		}
			
		/*
		   RankColors:
			  Spieler: &a
			  Premium: &6
			  Youtuber: &5
			  Builder: &2
			  Moderator: &9
			  Admin: &c
			  Developer: &b
			  Owner: &4
		*/
			  
			  
		if(rank.equalsIgnoreCase("Spieler")) {
			color = "§a";
		}
		else if(rank.equalsIgnoreCase("Premium")) {
			color = "§6";
		}
		else if(rank.equalsIgnoreCase("Titan")) {
			color = "§e";
		}
		else if(rank.equalsIgnoreCase("Champion")) {
			color = "§2";
		}
		else if(rank.equalsIgnoreCase("Premium+")) {
			color = "§6§o";
		}
		else if(rank.equalsIgnoreCase("Youtuber")) {
			color = "§5";
		}
		else if (rank.equalsIgnoreCase("Legendary")) {
			color = "§e§l";
		}
		else if(rank.equalsIgnoreCase("Miri")) {
			color = "§3";
		}
		else if(rank.equalsIgnoreCase("Builder")) {
			color = "§2";
		}
		else if(rank.equalsIgnoreCase("Supporter")) {
			color = "§d";
		}
		else if(rank.equalsIgnoreCase("Moderator")) {
			color = "§9";
		}
		else if(rank.equalsIgnoreCase("SrMod")) {
			color = "§9§o";
		}
		else if(rank.equalsIgnoreCase("TeamLeitung")) {
			color = "§c";
		}
		else if(rank.equalsIgnoreCase("Admin")) {
			color = "§c";
		}
		else if(rank.equalsIgnoreCase("Developer")) {
			color = "§b";
		}
		else if(rank.equalsIgnoreCase("Ownor")) {
			color = "§4";
		}
		else if(rank.equalsIgnoreCase("Owner")) {
			color = "§4";
		}
		else {
			color = "§a";
		}
		
		return color;
	}
	
	
	public static String getRank(String p) {
		
		String rank = "Spieler";
		
		  ResultSet inDataBase = MySQL.onQuery("SELECT * FROM CMS WHERE name = '" + p + "'");
		  
		  try {
			  if(inDataBase.next())
			  {
				  rank = inDataBase.getString("rank");
			  }
		  }
		  catch(SQLException ex) {
			  
		  }
		
		  if(rank.equalsIgnoreCase("default")) {
			  rank = "Spieler";
		  }
		  
		return rank;
	}
	
	public static void createPlayer(ProxiedPlayer p){
		  ResultSet inDataBase = MySQL.onQuery("SELECT * FROM CMS WHERE UUID = '" + p.getUniqueId() + "'");
		  
		  try {
			  if(!inDataBase.next())
			  {
				  MySQL.onUpdate("INSERT INTO CMS(UUID, name, disname) VALUES('" + p.getUniqueId() + "', '" + p.getName() + "', '" + p.getName() + "')");
			  }
			  else {
				  if(!inDataBase.getString("name").equals(p.getName())) {
					  MySQL.onUpdate("UPDATE CMS SET name = '" + p.getName() + "', disname = '" + p.getDisplayName() + "' WHERE UUID = '" + p.getUniqueId() + "'");
					  p.sendMessage(new TextComponent(" §7§oDein Name wurde geupdated. Deine Ränge bleiben erhalten."));
				  }
			  }
		  }
		  catch(SQLException ex) { }  
	}
	
	public static void setRank(String p, String rank) {		
		MySQL.onUpdate("UPDATE CMS SET rank = '" + rank + "' WHERE name = '" + p + "'");
	}

	public static boolean isRegistered(String p) {
		ResultSet inDataBase = MySQL.onQuery("SELECT * FROM CMS WHERE name = '" + p + "'");

		try {
			if (inDataBase.next()) {
				if(inDataBase.getInt("isRegistered") == 1) {
					if(inDataBase.getString("password").equalsIgnoreCase("none") || inDataBase.getString("password") == null) {
						setIsRegistered(p, false);
						return false;
					}
					else {
						return true;
					}
				}
			}
		} catch (SQLException ex) { }

		return false;
	}
	
	public static void setPassword(String p, String password) {
		MySQL.onUpdate("UPDATE CMS SET password = '" + PasswordManager.encrype(password) + "' WHERE name LIKE '" + p + "'");
	}
	
	public static String getPasswordHash(ProxiedPlayer p) {
		ResultSet inDataBase = MySQL.onQuery("SELECT * FROM CMS WHERE name = '" + p.getName() + "'");
		String password = "blabla#102323332";
		
		try {
			if (inDataBase.next()) {
				password = inDataBase.getString("password");
			}
		} catch (SQLException ex) { }

		return password;
	}
	
	public static void setIsRegistered(String p, boolean is) {
		MySQL.onUpdate("UPDATE CMS SET isRegistered = '" + (is ? 1 : 0) + "' WHERE name LIKE '" + p + "'");
	}
	
	public static void setCanRegister(String p, boolean is) {
		MySQL.onUpdate("UPDATE CMS SET canRegister = '" + (is ? 1 : 0) + "' WHERE name LIKE '" + p + "'");
	}
	
	public static boolean canRegister(String player) {
		ResultSet inDataBase = MySQL.onQuery("SELECT * FROM CMS WHERE name = '" + player + "'");

		try {
			if (inDataBase.next()) {
				if(inDataBase.getInt("canRegister") == 1) {
					return true;
				}
			}
		} catch (SQLException ex) { }

		return false;
	}
	
	public static void setPremiumAuth(String p, boolean is) {
		MySQL.onUpdate("UPDATE CMS SET premiumAuth = '" + (is ? 1 : 0) + "' WHERE name LIKE '" + p + "'");
	}
	
	public static boolean isPremiumAuth(ProxiedPlayer p) {
		ResultSet inDataBase = MySQL.onQuery("SELECT * FROM CMS WHERE name = '" + p.getName() + "'");

		try {
			if (inDataBase.next()) {
				if(inDataBase.getInt("premiumAuth") == 1) {
					return true;
				}
			}
		} catch (SQLException ex) { }

		return false;
	}
	
	
	public static boolean playerExists(ProxiedPlayer p) {
		ResultSet inDataBase = MySQL.onQuery("SELECT * FROM CMS WHERE name = '" + p.getName() + "'");

		try {
			if (inDataBase.next()) {
				return true;
			}
		} catch (SQLException ex) { }

		return false;
	}
	
	public static String getRank(ProxiedPlayer p) {
		String rang = "Spieler";
		ResultSet inDataBase = MySQL.onQuery("SELECT * FROM CMS WHERE name = '" + p.getName() + "'");
		try {
			if (inDataBase.next()) {
				rang = inDataBase.getString("rank");
			}
		} catch (SQLException e1) { }
		return rang;
	}

	public static void setRank(ProxiedPlayer p, String rank) {
		MySQL.onUpdate("UPDATE CMS SET rank = '" + rank + "' WHERE name = '" + p.getName() + "'");
	}
	
	public static String getColor(ProxiedPlayer p) {
		return getColor(p.getName());
	}
	
	public static int getCoins(ProxiedPlayer p) {
		int coins = 0;
		ResultSet inDataBase = MySQL.onQuery("SELECT * FROM CMS WHERE name = '" + p.getName() + "'");
		try {
			if (inDataBase.next()) {
				coins = inDataBase.getInt("money");
			}
		} catch (SQLException e1) { }
		return coins;
	}

	public static void addCoins(ProxiedPlayer p, int coins) {
		MySQL.onUpdate(
				"UPDATE CMS SET money = money + '" + coins + "' WHERE name = '" + p.getName() + "'");
	}
	
	public static void addAchievement(ProxiedPlayer p, String achievement) {
//		MySQL.onUpdate("INSERT INTO achievements(name, achievement) VALUES('"+ p.getName() + "', '" + achievement + "')");
//		
//		String aString = achievement;
//		if(aString.contains(".")) {
//			int ind = aString.indexOf(".");
//			aString = aString.substring(ind+1);
//		}
//		
//		p.sendMessage("");
//		p.sendMessage("§8■■■■■■■■ §2§khh§r §a§lACHIEVEMENT GET! §2§khh§r §8■■■■■■■■");
//		p.sendMessage(" §b§lName");
//		p.sendMessage("  §7§o" + aString.replace("_", " "));
//		p.sendMessage(" §b§lBeschreibung");
//		p.sendMessage("  §7§o" + getDescription(achievement));
//		p.sendMessage("§8■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
//		p.sendMessage("");
//		
//		addCoins(p, 50);	
		
	}
	
	public static List<String> getAchievements(ProxiedPlayer p) {
//		ResultSet set = MySQL.onQuery("SELECT main.name, ach.achievement FROM CMS as main INNER JOIN achievements as ach on main.name = ach.name WHERE main.name = '" + p.getName() + "'");
		
		List<String> achievements = new ArrayList<String>();
		
//		try {
//			
//			while(set.next()) {
//				achievements.add(set.getString("achievement"));
//			}
//			
//		} catch (SQLException e) { }
		
		return achievements;
	}
	
	public static boolean hasAchievement(ProxiedPlayer p, String achievement) {
		return true;
//		return getAchievements(p).contains(achievement);
	}
	
	public static String getDescription(String ach) {
		if(ach.equalsIgnoreCase("Global.GoodGame")) {
			return "Schreibe 'GG' in den Chat";
		}
		else if(ach.equalsIgnoreCase("Global.Provokateur")) {
			return "Schreibe 'eZ' in den Chat";
		}
		else if(ach.equalsIgnoreCase("Global.nicht_so_frech")) {
			return "Schreibe ein verbotenes Wort in den Chat";
		}
		else if(ach.equalsIgnoreCase("Global.Nachaktiv")) {
			return "Seie um Mitternacht auf CMS";
		}
		else if(ach.equalsIgnoreCase("Global.schlechtes_Internet")) {
			return "Habe einen Ping über 40";
		}
		else if(ach.equalsIgnoreCase("Global.Stammspieler")) {
			return "Du hast 24 Spielstunden erreicht";
		}
		else if(ach.equalsIgnoreCase("Global.Fortschritt")) {
			return "Erreiche Netzwerk Level 2";
		}
		else if(ach.equalsIgnoreCase("Global.Immer_Weiter")) {
			return "Erreiche Netzwerk Level 5";
		}
		else if(ach.equalsIgnoreCase("Global.Hoch_Hinaus")) {
			return "Erreiche Netzwerk Level 10";
		}
		else if(ach.equalsIgnoreCase("Global.Master_Of_Disaster")) {
			return "Erreiche Netzwerk Level 15";
		}
		else if(ach.equalsIgnoreCase("Global.König_von_CMS")) {
			return "Erreiche Netzwerk Level 20";
		}
		else if(ach.equalsIgnoreCase("Global.Gottheit")) {
			return "Erreiche Netzwerk Level 50";
		}
		
		
		return "Keine Beschreibung vorhanden";
	}
	
	public static void scheduler() {
		BungeeCord.getInstance().getScheduler().schedule(main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				 Calendar cal = Calendar.getInstance();
			     SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			        
			     checkTimeRanks();
			     
			        if(sdf.format(cal.getTime()) == "00:00") {
			        	for(ProxiedPlayer p : BungeeCord.getInstance().getPlayers()) {
			        		if(!RankManager.hasAchievement(p, "Global.Nachaktiv")) {
			        			RankManager.addAchievement(p, "Global.Nachaktiv");
			        		}
			        		
			        		int ping = p.getPing();
			        		
			        		if(ping >= 40) {
			        			if(!RankManager.hasAchievement(p, "Global.schlechtes_Internet")) {
				        			RankManager.addAchievement(p, "Global.schlechtes_Internet");
				        		}
			        		}
			        	}
			        }
			}
		}, 1, 1, TimeUnit.MINUTES);
	}
	
	public static int getNetLevel(ProxiedPlayer p) {
		int level = 1;
		ResultSet inDataBase = MySQL.onQuery("SELECT * FROM netlevels WHERE name = '" + p.getName() + "'");
		try {
			if (inDataBase.next()) {
				level = inDataBase.getInt("level");
			}
			else {
				MySQL.onUpdate("INSERT INTO netlevels(name, level, xp) VALUES('" + p.getName() + "', '1', '0')");
				return getNetLevel(p);
			}
		} catch (SQLException e1) { }
		return level;
	}
	
	public static int getNetXP(ProxiedPlayer p) {
		int xp = 0;
		ResultSet inDataBase = MySQL.onQuery("SELECT * FROM netlevels WHERE name = '" + p.getName() + "'");
		try {
			if (inDataBase.next()) {
				xp = inDataBase.getInt("xp");
			}
			else {
				MySQL.onUpdate("INSERT INTO netlevels(name, level, xp) VALUES('" + p.getName() + "', '1', '0')");
				return getNetXP(p);
			}
		} catch (SQLException e1) { }
		return xp;
	}
	
	public static void setNetLevel(ProxiedPlayer p, int level) {
		MySQL.onUpdate("UPDATE netlevels SET level = '" + level + "' WHERE name = '" + p.getName() + "'");
	}
	
	public static void setNetXP(ProxiedPlayer p, int xp) {
		MySQL.onUpdate("UPDATE netlevels SET xp = '" + xp + "' WHERE name = '" + p.getName() + "'");
	}
	
	@SuppressWarnings("deprecation")
	public static void addXP(ProxiedPlayer p, int xp) {
		int level = getNetLevel(p);
		int nxp = getNetXP(p);
		int exp = nxp + xp;
		
		while(exp >= (level*1000)) {
			exp = exp - (level * 1000);
			level++;
			//LEVEL UP
			
			addCoins(p, (level * 100));
			
			if(level >= 2) {
				if(!hasAchievement(p, "Global.Fortschritt")) {
					addAchievement(p, "Global.Fortschritt");
				}
			}
			if(level >= 5) {
				if(!hasAchievement(p, "Global.Immer_Weiter")) {
					addAchievement(p, "Global.Immer_Weiter");
				}
			}
			if(level >= 10) {
				if(!hasAchievement(p, "Global.Hoch_Hinaus")) {
					addAchievement(p, "Global.Hoch_Hinaus");
				}
			}
			if(level >= 15) {
				if(!hasAchievement(p, "Global.Master_Of_Disaster")) {
					addAchievement(p, "Global.Master_Of_Disaster");
				}
			}
			if(level >= 20) {
				if(!hasAchievement(p, "Global.König_von_CMS")) {
					addAchievement(p, "Global.König_von_CMS");
				}
			}
			if(level >= 50) {
				if(!hasAchievement(p, "Global.Gottheit")) {
					addAchievement(p, "Global.Gottheit");
				}
			}
			
			p.sendMessage("");
			p.sendMessage("§8■■■■■■■■ §2§khh§r §a§lLEVEL UP! §2§khh§r §8■■■■■■■■");
			p.sendMessage("");
			p.sendMessage(" §b§lLevel §6" + level);
			p.sendMessage(" §e§l+" + (level * 100) + " Coins");
			p.sendMessage("");
			p.sendMessage("§8■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			p.sendMessage("");
		}

		setNetLevel(p, level);
		setNetXP(p, exp);
	}
}
