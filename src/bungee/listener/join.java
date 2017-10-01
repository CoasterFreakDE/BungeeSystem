package bungee.listener;

import bungee.Main.main;
import bungee.manager.RankManager;
import bungee.manager.Tablistmanager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class join implements Listener
{

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PostLoginEvent e) throws Exception {
		ProxiedPlayer p = e.getPlayer();
		
		RankManager.createPlayer(p);
		
		
		
		
		
		//CHECK IF BANNED
		
		if(main.getBm().isBanned(p.getName())) {
			String message = main.getBm().getGrund(p.getName());
			String time = main.getBm().getTime(p.getName());
			
			if(isNumb(time)) {
				Long tl = Long.parseLong(time);
				
				final double scale1440 = 1.0/1440;
				final double scale60 = 1.0/60;
				int dd = (int) (tl * scale1440);
				int hh = (int) ((tl * scale60) - dd*24);
				int mm = (int) (tl - hh*60 - dd*1440);
				
				p.disconnect("§cDu wurdest §4TEMPORÄR §cvon Netzwerk gebannt. \n§7Grund§8: §c" + message + "\n \n \n§e" + dd + " Tage, " + hh + " Stunden, " + mm + " Minuten");
				return;
			}
			else {
				p.disconnect("§cDu wurdest §4PERMANENT §cvon Netzwerk gebannt. \n§7Grund§8: §c" + message);
				return;
			}
		}
		
		if(main.getBm().isipBanned(p.getAddress().getAddress().toString())) {
			String message = main.getBm().getIPGrund(p.getAddress().getAddress().toString());
			p.disconnect("§cDu wurdest §4PERMANENT §cvon Netzwerk gebannt. \n§7Grund§8: §c" + message);
			return;
		}
		
		
		p.sendMessage("§aWillkommen auf §aSultex.de");
		Tablistmanager.sendTab(p);

		
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
