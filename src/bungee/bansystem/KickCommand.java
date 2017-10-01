package bungee.bansystem;

import bungee.Main.main;
import bungee.manager.RankManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class KickCommand extends Command {

	public KickCommand() {
		super("gkick");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender s, String[] args) {
	
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) s;
			
			if(RankManager.hasPermission(p, "Kick")) {
				if(args.length == 1) {
					String player = args[0];
					
						
						if(BungeeCord.getInstance().getPlayer(player) != null) {
							BungeeCord.getInstance().getPlayer(player).disconnect("§cDu wurdest §cvon Netzwerk gekickt. \n§7Grund§8: §cFehlverhalten");
							p.sendMessage(main.getBm().prefix + "§cDer Spieler wurde gekickt.");
						}
						else {
							p.sendMessage(main.getBm().prefix + "§cDer Spieler ist nicht online.");
						}
				}
				else if(args.length > 1) {
					String player = args[0];
					
					StringBuilder builder = new StringBuilder();
					for(int i = 1 ; i < args.length ; i++)builder.append(args[i]).append(" ");
					String message = builder.toString();
					
					if(BungeeCord.getInstance().getPlayer(player) != null) {
						BungeeCord.getInstance().getPlayer(player).disconnect("§cDu wurdest §cvon Netzwerk gekickt. \n§7Grund§8: §c" + message);
						p.sendMessage(main.getBm().prefix + "§7Der Spieler wurde gekickt.");
					}
					else {
						p.sendMessage(main.getBm().prefix + "§cDer Spieler ist nicht online.");
					}
				}
				else {
					p.sendMessage(main.getBm().prefix + "§cBitte benutze§7: §e/gkick §7<Spieler> (Grund)");
				}
			}
			}
			
		}	
}
