package bungee.manager;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import bungee.Main.main;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.config.Configuration;

public class AutoChat 
{
	static Configuration am;
	public static boolean reload;

	public static void onAutoChat() {
		
		am = main.getAutomessage();
		reload = false;
		
		if(am.getStringList("Messages") == null) {
			System.out.println("Automessage.yml not filled.");
			return;
		}
		
		
		
		BungeeCord.getInstance().getScheduler().schedule(main.getInstance(), new Runnable() {
			
			List<String> messages = am.getStringList("Messages");
			int i = messages.size();
			
			@Override
			public void run() {
				
				if(reload) {
					messages = am.getStringList("Messages");
					i = messages.size();
					reload = false;
				}
				
				
				Random rand = new Random();
				
				if(i > 0) {
					String msg = messages.get(rand.nextInt(i));
					
					msg = msg.replace("ae", "ä");
					msg = msg.replace("ue", "ü");
					msg = msg.replace("oe", "ö");
					msg = msg.replace("Ae", "Ä");
					msg = msg.replace("Ue", "Ü");
					msg = msg.replace("Oe", "Ö");
					
					TextComponent text = new TextComponent(ChatColor.translateAlternateColorCodes('&', msg));
					
					BungeeCord.getInstance().broadcast(text);
				}
			}
		}, 5, 15, TimeUnit.MINUTES);		
	}
	
	
}
