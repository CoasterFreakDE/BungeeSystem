package bungee.commands;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class hub extends Command
{

	public hub() {
		super("hub",  null, new String[] { "lobby", "l", "leave", "h"});
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(CommandSender s, String[] args) {
	
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) s;
			
			p.connect(BungeeCord.getInstance().getServerInfo("lobby"));
		}
	}

	
	
}
