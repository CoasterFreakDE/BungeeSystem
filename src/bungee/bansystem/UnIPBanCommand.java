package bungee.bansystem;

import bungee.Main.main;
import bungee.manager.RankManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class UnIPBanCommand extends Command
{

	public UnIPBanCommand() {
		super("ipunban");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender s, String[] args) {
		
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) s;
			
			if(RankManager.hasPermission(p, "ipunban")) {
				if(args.length == 1) {
					String ip = args[0];
					
					if(main.getBm().isipBanned(ip)) {
						main.getBm().ipunban(ip);
						
						p.sendMessage(main.getBm().prefix + "§cDiese IP wurde entbannt.");
					}
					else {
						p.sendMessage(main.getBm().prefix + "§cDiese IP ist nicht gebannt.");
					}
				}
				else {
					p.sendMessage(main.getBm().prefix + "§cBitte benutze§7: §e/ipunban §7<IP>");
				}
			}
		}
	}

	
	
}
