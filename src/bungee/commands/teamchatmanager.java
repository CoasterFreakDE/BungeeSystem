package bungee.commands;

import java.util.List;

import bungee.Main.main;
import bungee.manager.RankManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

public class teamchatmanager extends Command
{

	public teamchatmanager(main m) {
		super("teamchat");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender s, String[] args) {

		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer)s;
			
			Configuration config = main.getConfiguration();
			List<String> players = config.getStringList("teamchat");
			
			
			if(p.hasPermission("BungeeSystem.teamchat") || RankManager.hasPermission(p, "teamchat")) {
				
				if(args.length == 2) {
					String sub = args[0];
					String pl = args[1];
					
					if(sub.equalsIgnoreCase("add")) {
						if(!players.contains(pl)) {
							players.add(pl);
							config.set("teamchat", players);
							main.saveConfig();
							
							p.sendMessage("§a" + pl + " §7ist nun im §bTeamchat");
						}
						else {
							p.sendMessage("§cDieser Spieler ist schon im §bTeamchat");
						}
					}
					else if(sub.equalsIgnoreCase("remove")) {
						if(players.contains(pl)) {
							players.remove(pl);
							config.set("teamchat", players);
							main.saveConfig();
							
							p.sendMessage("§c" + pl + " §7ist nicht mehr im §bTeamchat");
						}
						else {
							p.sendMessage("§cDieser Spieler ist nicht im §bTeamchat");
						}
					}
					else {
						p.sendMessage("§6Benutze §e/teamchat §aadd§7|§cremove §e[Spieler]");
					}
				}
				else {
					p.sendMessage("§6Benutze §e/teamchat §aadd§7|§cremove §e[Spieler]");
				}
			}
			else {
				p.sendMessage("§cKeine Berechtigung.");
			}
		}	
	}

	
	
}
