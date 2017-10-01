package bungee.commands;

import bungee.Main.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ping extends Command
{

	public ping(main m) {
		super("ping");
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender s, String[] args) {
	
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) s;
			
			int ping = p.getPing();
			
			String pinged = "§7";
			
			if(ping <= 30) {
				pinged = "§a";
			}
			else if(ping > 30 && ping <= 60) {
				pinged = "§e";
			}
			else {
				pinged = "§c";
			}
			
			p.sendMessage("§6Dein Ping§7: " + pinged + ping);
		}
	}

	
	
}
