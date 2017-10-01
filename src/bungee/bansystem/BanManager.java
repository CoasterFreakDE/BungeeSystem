package bungee.bansystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import bungee.Main.main;
import bungee.MySQL.MySQL;
import net.md_5.bungee.BungeeCord;

public class BanManager {

	public String prefix = "§7[§c§oBanSystem§7] ";
	
	public BanManager() {
		MuteRunner();
	}
	
	public void ban(String p, String grund) {
		  ResultSet inDataBase = MySQL.onQuery("SELECT * FROM bans WHERE name LIKE '" + p + "'");
		  
		  try {
			  if(!inDataBase.next())
			  {
				  MySQL.onUpdate("INSERT INTO bans(name, grund, zeit) VALUES('" + p + "', '" + grund + "', 'perma')");
			  }
		  }
		  catch(SQLException ex) {
			  
		  }
	}
	
	public void ban(String p, String grund, String zeit) {
		  ResultSet inDataBase = MySQL.onQuery("SELECT * FROM bans WHERE name LIKE '" + p + "'");
		  
		  try {
			  if(!inDataBase.next())
			  {
				  MySQL.onUpdate("INSERT INTO bans(name, grund, zeit) VALUES('" + p + "', '" + grund + "', '" + zeit + "')");
			  }
		  }
		  catch(SQLException ex) {
			  
		  }
	}
	
	public List<String> bannedPlayers() {
		List<String> banned = new ArrayList<String>(); 
		ResultSet inDataBase = MySQL.onQuery("SELECT * FROM bans");
	
		try {
			while(inDataBase.next()) {
				banned.add(inDataBase.getString("name"));
			}
		} catch (SQLException e) {
		}
		
		return banned;
	}
	
	public void unban(String p) {
		  ResultSet inDataBase = MySQL.onQuery("SELECT * FROM bans WHERE name LIKE '" + p + "'");
		  
		  try {
			  if(inDataBase.next())
			  {
				  MySQL.onUpdate("DELETE FROM bans WHERE name LIKE '" + p + "' LIMIT 1");
			  }
		  }
		  catch(SQLException ex) {
			  
		  }
	}
	
	public void ipban(String ip, String grund) {
		  ResultSet inDataBase = MySQL.onQuery("SELECT * FROM ipbans WHERE ip LIKE '" + ip + "'");
		  
		  try {
			  if(!inDataBase.next())
			  {
				  MySQL.onUpdate("INSERT INTO ipbans(ip, grund, zeit) VALUES('" + ip + "', '" + grund + "', 'perma')");
			  }
		  }
		  catch(SQLException ex) {
			  
		  }
	}
	
	public void ipunban(String ip) {
		  ResultSet inDataBase = MySQL.onQuery("SELECT * FROM ipbans WHERE ip LIKE '" + ip + "'");
		  
		  try {
			  if(inDataBase.next())
			  {
				  MySQL.onUpdate("DELETE FROM ipbans WHERE ip LIKE '" + ip + "' LIMIT 1");
			  }
		  }
		  catch(SQLException ex) {
			  
		  }
	}
	
	public String getGrund(String p) {
		String grund = "";
		
		  ResultSet inDataBase = MySQL.onQuery("SELECT * FROM bans WHERE name LIKE '" + p + "'");
		  
		  try {
			  if(inDataBase.next())
			  {
				  grund = inDataBase.getString("grund");
			  }
		  }
		  catch(SQLException ex) {
			  
		  }
		
		
		return grund;
	}
	
	public String getTime(String p) {
		String grund = "";
		
		  ResultSet inDataBase = MySQL.onQuery("SELECT * FROM bans WHERE name LIKE '" + p + "'");
		  
		  try {
			  if(inDataBase.next())
			  {
				  grund = inDataBase.getString("zeit");
			  }
		  }
		  catch(SQLException ex) {
			  
		  }
		
		
		return grund;
	}
	
	public String getIPGrund(String ip) {
		String grund = "";
		
		  ResultSet inDataBase = MySQL.onQuery("SELECT * FROM ipbans WHERE ip LIKE '" + ip + "'");
		  
		  try {
			  if(inDataBase.next())
			  {
				  grund = inDataBase.getString("grund");
			  }
		  }
		  catch(SQLException ex) {
			  
		  }
		
		
		return grund;
	}
	
	
	public boolean isipBanned(String ip) {
		  ResultSet inDataBase = MySQL.onQuery("SELECT * FROM ipbans WHERE ip LIKE '" + ip + "'");
		  
		  try {
			  if(inDataBase.next())
			  {
				  return true;
			  }
		  }
		  catch(SQLException ex) {
			  
		  }
		  
		  return false;
	}
	
	public boolean isBanned(String p) {
		  ResultSet inDataBase = MySQL.onQuery("SELECT * FROM bans WHERE name LIKE '" + p + "'");
		  
		  try {
			  if(inDataBase.next())
			  {
				  return true;
			  }
		  }
		  catch(SQLException ex) {
			  
		  }
		  
		  return false;
	}
	
	public void setMuted(String p, long time, String grund) {
		  ResultSet inDataBase = MySQL.onQuery("SELECT * FROM mute WHERE name LIKE '" + p + "'");
		  
		  try {
			  if(!inDataBase.next())
			  {
				  MySQL.onUpdate("INSERT INTO mute(name, zeit, grund) VALUES('" + p + "','" + time + "','" + grund + "')");
			  }
		  }
		  catch(SQLException ex) {
			  
		  }
	}
	
	public long getMuteTime(String p) {
		  ResultSet inDataBase = MySQL.onQuery("SELECT * FROM mute WHERE name LIKE '" + p + "'");
		  
		  try {
			  if(inDataBase.next())
			  {
				  return inDataBase.getLong("zeit");
			  }
		  }
		  catch(SQLException ex) {
			  
		  }
		  
		  return -2;
	}
	
	public String getMuteGrund(String p) {
		  ResultSet inDataBase = MySQL.onQuery("SELECT * FROM mute WHERE name LIKE '" + p + "'");
		  
		  try {
			  if(inDataBase.next())
			  {
				  return inDataBase.getString("grund");
			  }
		  }
		  catch(SQLException ex) {
			  
		  }
		  
		  return "Fehlverhalten";
	}
	
	public boolean isMuted(String p) {
		  ResultSet inDataBase = MySQL.onQuery("SELECT * FROM mute WHERE name LIKE '" + p + "'");
		  
		  try {
			  if(inDataBase.next())
			  {
				  return true;
			  }
		  }
		  catch(SQLException ex) {
			  
		  }
		  
		  return false;
	}
	
	
	public void unmute(String name) {
		  ResultSet inDataBase = MySQL.onQuery("SELECT * FROM mute WHERE name LIKE '" + name + "'");
		  
		  try {
			  if(inDataBase.next())
			  {
				  MySQL.onUpdate("DELETE FROM mute WHERE name LIKE '" + name + "' LIMIT 1");
			  }
		  }
		  catch(SQLException ex) {
			  
		  }
	}
	
	public void MuteRunner() {
		
		BungeeCord.getInstance().getScheduler().schedule(main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				//System.out.println("Tempmute Timer Aufruf");
				
				ResultSet inDataBase = MySQL.onQuery("SELECT * FROM mute");
				  
				  try {
					  while(inDataBase.next())
					  {
						  String name = inDataBase.getString("name");
						  long time = inDataBase.getLong("zeit");
						  
						//  System.out.println("Checking for tempmute " + name + " with " + time + " minutes ban");
						  
						  if(time > 1) {
							  MySQL.onUpdate("UPDATE mute SET zeit = zeit - 1 WHERE name LIKE '" + name + "'");
						  }
						  else if(time == 1) {
							  MySQL.onUpdate("DELETE FROM mute WHERE name LIKE '" + name + "' LIMIT 1");
						  }
					  }
				  }
				  catch(SQLException ex) {
					  ex.printStackTrace();
				  }
				  
				  
					inDataBase = MySQL.onQuery("SELECT * FROM bans");
					  
					  try {
						  while(inDataBase.next())
						  {
							  String name = inDataBase.getString("name");
							  String time = inDataBase.getString("zeit");
							  
							  if(!time.equalsIgnoreCase("perma")) {
								  if(isNumb(time)) {
									  long tl = Long.parseLong(time);
									  if(tl > 1) {
										  MySQL.onUpdate("UPDATE bans SET zeit = zeit - 1 WHERE name LIKE '" + name + "'");
									  }
									  else if(tl == 1) {
										  MySQL.onUpdate("DELETE FROM bans WHERE name LIKE '" + name + "' LIMIT 1");
									  }
								  }
							  }
						  }
					  }
					  catch(SQLException ex) {
						  ex.printStackTrace();
					  }
			}
		}, 1, 1, TimeUnit.MINUTES);
	}
	
	
	@SuppressWarnings("unused")
	public boolean isNumb(String s) {
		try {
			long l = Long.parseLong(s);
			return true;
		}
		catch(Exception ex) { 
			return false;
		}
	}
}
