package bungee.commands;

import bungee.Main.main;
import bungee.manager.RankManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class move extends Command
{

	public move(main m) {
		super("move");
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender s, String[] args)
	{
		
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer)s;
			
			if(p.hasPermission("BungeeSystem.move") || RankManager.hasPermission(p, "move")) {
				if(args.length == 2) {
					String t = args[0];
					String server = args[1];
					
					if(BungeeCord.getInstance().getPlayer(t) != null) {
						if(BungeeCord.getInstance().getServers().containsKey(server)) {
							BungeeCord.getInstance().getPlayer(t).connect(BungeeCord.getInstance().getServerInfo(server));
							p.sendMessage("§aSpieler wurde gemoved.");
						}
						else {
							p.sendMessage("§cDer Server existert nicht.");
						}
					}
					else {
						p.sendMessage("§cDer Spieler ist nicht online.");
					}
				}
				else {
					p.sendMessage("§cBenutze §a/move [Spieler] [Server]");
				}
			}
			else {
				p.sendMessage("§cKeine Berechtigung.");
			}
		}
		
	}

	
	
}
