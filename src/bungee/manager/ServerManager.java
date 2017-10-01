package bungee.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import bungee.Main.main;
import bungee.MySQL.MySQL;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;

public class ServerManager {

	
	public static boolean isBungeeChat(String server) {
		if(server.startsWith("MS-")) {
			ResultSet set = MySQL.onQuery("SELECT * FROM MyServer WHERE server = '" + server + "'");
			
			try {
				if(set.next())
				{
					int isServerChat = set.getInt("serverchat");
					
					if(isServerChat == 1) {
						return false;
					}
				}
			}catch (SQLException ex) { }
			
			
		}
		else {
			Configuration config = main.getConfiguration();
			
			if(config.getStringList("ServerChat") != null) {
				if(config.getStringList("ServerChat").contains(server)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public static void setServerChat(String server, boolean chat) {
		if(server.startsWith("MS-")) {
			MySQL.onUpdate("UPDATE MyServer SET serverchat = '" +  (chat ? 1 : 0) + "' WHERE server = '" + server + "'");
		}
	}
	
	public static void onlineTimer() {
		
		BungeeCord.getInstance().getScheduler().schedule(main.getInstance(), new Runnable() {
			
			@Override
			public void run() {		
				for(ProxiedPlayer p : BungeeCord.getInstance().getPlayers()) {
					int time = getTime(p.getName()) + 1;
					MySQL.onUpdate("UPDATE CMS SET time = '" + time + "' WHERE name = '" + p.getName() + "'");
					
					int hours = (int) time/60;
					int minutes = time - (60*hours);
					
					if(hours >= 24) {
						if(!RankManager.hasAchievement(p, "Global.Stammspieler")) {
							RankManager.addAchievement(p, "Global.Stammspieler");
						}
					}
					
					if(minutes == 0 && hours > 0) {
						TextComponent comp1 = new TextComponent("");
						TextComponent comp2 = new TextComponent(" §e§l+ §6§l§o1 Spielstunde");
						TextComponent comp3 = new TextComponent("   §e§l+ §6§l§o100 Coins");
						TextComponent comp4 = new TextComponent("   §e§l+ §6§l§o100 XP");
						
						comp2.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/onlinetime"));
						comp2.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_ITEM, new ComponentBuilder("GOLD_NUGGET").create()));
						
						RankManager.addCoins(p, 100);
						RankManager.addXP(p, 100);
						
						p.sendMessage(comp1);
						p.sendMessage(comp2);
						p.sendMessage(comp3);
						p.sendMessage(comp4);
						p.sendMessage(comp1);
					}
					
//					System.out.println("Player " + p.getName() + " Hours: " + hours + " Minutes: " + minutes);
				}
			}
		}, 1, 1, TimeUnit.MINUTES);
	}
	
	
	public static int getTime(String p) {
		int time = 0;
		
		ResultSet set = MySQL.onQuery("SELECT * FROM CMS WHERE name = '" + p + "'");
		
		try {
			if(set.next()) {
				time = set.getInt("time");
			}
		}
		catch(SQLException ex) { }
		
		return time;
	}
}
