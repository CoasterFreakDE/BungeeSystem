package bungee.manager;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;


public class Tablistmanager 
{
	
	
	
	public static void sendTab(ProxiedPlayer p) {
		
		
		
		p.setTabHeader(new TextComponent("§bWillkommen §a" + RankManager.getColor(p.getName()) + p.getName()  + "\n§7§oDu spielst auf §a§lSultex.de\n"), new TextComponent("§5" + BungeeCord.getInstance().getOnlineCount() + "§7/§6" + BungeeCord.getInstance().config.getPlayerLimit() +  "\n§bTeamSpeak: §b§lSultex.de"));
		
		
		
	
	}
	
}
