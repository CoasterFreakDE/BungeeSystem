package bungee.commands;

import bungee.manager.RankManager;
import bungee.manager.ServerManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class toggleChat extends Command {

	public toggleChat() {
		super("toggleChat");
	}

	@Override
	public void execute(CommandSender s, String[] args) {
		
		if(s instanceof ProxiedPlayer)  {
			ProxiedPlayer p = (ProxiedPlayer) s;
			
			if(!RankManager.getRank(p).equalsIgnoreCase("spieler")) {
				if(RankManager.getRank(p).equalsIgnoreCase("owner") && args.length == 1) {
					String server = args[0];
					boolean chat = ServerManager.isBungeeChat(server);
					ServerManager.setServerChat(server, chat);
					
					p.sendMessage(new TextComponent("§6MyServer §8❘ §7ServerChat für §6" + server + " " + (chat ? "§aaktiviert" : "§cdeaktiviert")));
				} else {
					String server = "MS-" + p.getName();
					boolean chat = ServerManager.isBungeeChat(server);
					ServerManager.setServerChat(server, chat);
					
					p.sendMessage(new TextComponent("§6MyServer §8❘ §7ServerChat " + (chat ? "§aaktiviert" : "§cdeaktiviert")));
				}
			}
			else {
				p.sendMessage(new TextComponent("§6MyServer §8❘ §7Kaufe dir §6Premium §7um den ServerChat umzuschalten."));
			}
		}
	}

	
	
}
