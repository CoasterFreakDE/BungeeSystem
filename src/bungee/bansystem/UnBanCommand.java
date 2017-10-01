package bungee.bansystem;

import bungee.Main.main;
import bungee.manager.RankManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class UnBanCommand extends Command
{

	public UnBanCommand() {
		super("unban");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender s, String[] args) {
		
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) s;
			
			if(RankManager.hasPermission(p, "unban")) {
				if(args.length == 1) {
					String player = args[0];
					
					if(main.getBm().isBanned(player)) {
						main.getBm().unban(player);
						
						p.sendMessage(main.getBm().prefix + "§cDer Spieler wurde entbannt.");
					}
					else {
						p.sendMessage(main.getBm().prefix + "§cDer Spieler ist nicht gebannt.");
					}
				}
				else {
					p.sendMessage(main.getBm().prefix + "§cBitte benutze§7: §e/unban §7<Spieler>");
				}
			}
		}
	}

	
	
}
