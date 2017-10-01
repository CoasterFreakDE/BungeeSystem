package bungee.listener;

import java.util.List;
import java.util.Random;

import bungee.Main.main;
import bungee.MySQL.MySQL;
import bungee.manager.RankManager;
import bungee.manager.ServerManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

public class chatmanager implements Listener
{

	@EventHandler
	public void onChat(ChatEvent e) {
		String msg = e.getMessage();
				
		if(e.getSender() instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) e.getSender();
			
			if(msg.length() < 1) {
				p.sendMessage(new TextComponent(main.PREFIX + "§c§oBitte schreibe mindestens 1 Zeichen."));
				e.setCancelled(true);
				return;
			}
			
			if(msg.endsWith("\\")) {
				p.sendMessage(new TextComponent(main.PREFIX + "§c§oBitte schreibe kein \\ am Ende deines Satzes."));
				e.setCancelled(true);
				return;
			}
			
			/*
			 *  PATTERN CHECK
			 */
			
			String pattern = "▀▁▂▃▄▅▆▇█▉▊▋▌^abcdefghijklmnopqrstuvwxyzäüö0123456789!'\"/&%$-_#*+~.:,;€@=?ß´`♥ ♦«»✖√✓•★<>|{}[]()\\йцукенгшщзхфывапролджэячсмитьбю.-Ъъ№";
			
			if(!RankManager.hasPermission(p, "bypassChatFilter")) {
			
			 for(char c : msg.toLowerCase().toCharArray()) {
			    if(pattern.indexOf(c) == -1) {
			    	e.getSender().disconnect(new TextComponent(main.PREFIX + "§cDu hast ein Verbotenes Zeichen benutzt!"));
					e.setCancelled(true);
					return;
			    }
			 }
			 
			}
			
			
			int time;
			if((time = ServerManager.getTime(p.getName())) < 10) {
				if(!e.isCommand()) {
					TextComponent component = new TextComponent(main.PREFIX + "§cDu bist noch §e" + (10-time) + " Minuten §cgemuted. \n" + main.PREFIX + "§7§oSpiele doch noch ein bisschen.");
					component.setClickEvent(new ClickEvent(Action.OPEN_URL, "sultex.de"));
					p.sendMessage(component);
					e.setCancelled(true);
					return;
				}
			}
			
			if(!e.isCommand()) {
				//chatlog(time TIMESTAMP, uuid VARCHAR(64), name VARCHAR(32), server VARCHAR(32), nachricht VARCHAR(256)
				MySQL.onUpdate("INSERT INTO chatlog(time, uuid, name, server, nachricht) VALUES(now(), '" + p.getUniqueId() + "', '" + p.getName() + "', '" + p.getServer().getInfo().getName() + "', '" + msg + "')");
				
			}
			
			if(!RankManager.hasPermission(p, "bypassChatFilter")) {
				
				Configuration filter = main.getChatfilter();
				
				List<String> blist = filter.getStringList("Blacklist");
				List<String> replace = filter.getStringList("Replace");
				
				if(blist.isEmpty()) {
					blist.add("penis");
					filter.set("Blacklist", blist);
					main.saveConfig();
				}
				
				if(replace.isEmpty()) {
					replace.add("Cookie");
					filter.set("Replace", replace);
					main.saveConfig();
				}
				
				//REPLACEER
				
				String newmsg = "";
				
				for(String s : msg.split(" ")) {
										
					for(String bl : blist) {
						if(s.toLowerCase().contains(bl)) {
							//FILTERED
							
							Random rand = new Random();
							
							s = replace.get(rand.nextInt(replace.size()));
						}
					}

					newmsg += " " + s;
				}
				
				
				msg = newmsg;
				
//				String sl = "";
//				for(char s : msg.toCharArray()) {
//					sl += "§7" + s + "§7";
//				}
//				
//				
//				msg = sl;
			}
			
			
			if(!e.isCommand()) {									
					if(main.getBm().isMuted(p.getName())) {
						long input = main.getBm().getMuteTime(p.getName());
						
						if(input > 0) {
						
							final double scale1440 = 1.0/1440;
							final double scale60 = 1.0/60;
							int dd = (int) (input * scale1440);
							int hh = (int) ((input * scale60) - dd*24);
							int mm = (int) (input - hh*60 - dd*1440);
							String grund = main.getBm().getMuteGrund(p.getName());
							
							TextComponent component = new TextComponent(main.getBm().prefix + "§cDu bist noch §e" + dd + " Tage, " + hh + " Stunden, " + mm + " Minuten §caus §cdem §cChat verbannt. §f| §6Grund: §7§o" + grund);
							component.setClickEvent(new ClickEvent(Action.OPEN_URL, "http://sultex.de"));
							p.sendMessage(component);
						}
						else {
							String grund = main.getBm().getMuteGrund(p.getName());
							TextComponent component = new TextComponent(main.getBm().prefix + "§cDu bist §4PERMANENT §caus dem Chat verbannt. §f| §6Grund: §7§o" + grund);
							component.setClickEvent(new ClickEvent(Action.OPEN_URL, "http://sultex.de")); 
							p.sendMessage(component);
						}
						
						e.setCancelled(true);
						return;
					}
			}
		}
	}
	
	
}
