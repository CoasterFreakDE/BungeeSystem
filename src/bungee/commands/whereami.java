package bungee.commands;

import bungee.Main.main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class whereami extends Command
{

	public whereami(main m) {
		super("whereami");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender s, String[] args) {
		
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) s;
			
			String server = p.getServer().getInfo().getName();
			
			p.sendMessage("§6Du befindest dich auf §a" + server);
		}
		
		
	}

}
