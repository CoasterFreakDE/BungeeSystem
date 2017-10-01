package bungee.bansystem;

import java.util.Collection;

import bungee.manager.RankManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ChatClear extends Command
{

	public ChatClear() {
		super("chatclear", null, new String[] { "cc" });
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender s, String[] args) {
		
		
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer)s;
			Collection<ProxiedPlayer> players = p.getServer().getInfo().getPlayers();
			
			if(RankManager.hasPermission(p, "ChatClear")) {
				for(ProxiedPlayer pl : players) {
					
					for(int i = 0; i < 100; i++) {
						pl.sendMessage(" ");
					}
					
					
					pl.sendMessage("───§e▄████▄§f─────");
					pl.sendMessage("──§e███▄█▀§f──────");
					pl.sendMessage("─§e▐████§f──§7Der Chat wurde von §ePacman §7gefressen.");
					pl.sendMessage("──§e█████▄§f──────");
					pl.sendMessage("───§e▀████▀§f─────");
					pl.sendMessage(" ");
				}	
			}
		}
		else {
			for(int i = 0; i < 100; i++) {
				BungeeCord.getInstance().broadcast(" ");
			}
			
			BungeeCord.getInstance().broadcast("───§e▄████▄§f─────");
			BungeeCord.getInstance().broadcast("──§e███▄█▀§f──────");
			BungeeCord.getInstance().broadcast("─§e▐████§f──§7Der Chat wurde von §ePacman §7gefressen.");
			BungeeCord.getInstance().broadcast("──§e█████▄§f──────");
			BungeeCord.getInstance().broadcast("───§e▀████▀§f─────");	
			BungeeCord.getInstance().broadcast(" ");
		}
		
	}

	
	
	
	
	
}
