package bungee.commands;

import bungee.manager.RankManager;
import bungee.manager.ServerManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class onlinetime extends Command {

	public onlinetime() {
		super("onlinetime");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender s, String[] args) {

		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) s;
			
			if(args.length == 1) {
				if(RankManager.hasPermission(p, "Onlinetime")){
					String name = args[0];
					int time = ServerManager.getTime(name);
					int hours = (int) time/60;
					int minutes = time - (60*hours);
					
					p.sendMessage("§cSystem §8❘ §6§oDie Onlinetime von §2" + name + " §8: §a"+ hours + " Stunden §7& §a" + minutes + " Minuten");
					
				}
			}
			else {
				int time = ServerManager.getTime(p.getName());
				int hours = (int) time/60;
				int minutes = time - (60*hours);
				
				p.sendMessage("§cSystem §8❘ §6§oDeine Onlinetime§8: §a"+ hours + " Stunden §7& §a" + minutes + " Minuten");
			}
		}
	}
	
	

}
