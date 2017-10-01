package bungee.bansystem;

import bungee.Main.main;
import bungee.manager.RankManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ChatUnBanCommand extends Command
{

	public ChatUnBanCommand() {
		super("unmute");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender s, String[] args) {
		
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) s;
			
			if(RankManager.hasPermission(p, "mute")) {
				if(args.length == 1) {
					String player = args[0];
					
					if(main.getBm().isMuted(player)) {
						main.getBm().unmute(player);
						
						p.sendMessage(main.getBm().prefix + "§cDer Spieler wurde entmuted.");
					}
					else {
						p.sendMessage(main.getBm().prefix + "§cDer Spieler ist nicht gemuted.");
					}
				}
				else {
					p.sendMessage(main.getBm().prefix + "§cBitte benutze§7: §e/unmute §7<Spieler>");
				}
			}
		}
		
		
	}

	
	
}
