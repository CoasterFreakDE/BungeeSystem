package bungee.bansystem;

import bungee.Main.main;
import bungee.manager.RankManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class IPBanCommand extends Command
{

	public IPBanCommand() {
		super("ipban");
	}

	/*
	 * 		SPEICHER: mysql  TABLE: ipbans (ip, grund, zeit)
	 */
	
	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender s, String[] args) {
		
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) s;
			
			if(RankManager.hasPermission(p, "ipban")) {
				if(args.length == 1) {
					String player = args[0];
					String ip = args[0];
					
					if(BungeeCord.getInstance().getPlayer(player) != null) {
						ip = BungeeCord.getInstance().getPlayer(player).getAddress().getAddress().toString();
					}
					
					if(!main.getBm().isipBanned(ip)) {
						main.getBm().ipban(ip, "Fehlverhalten");
						
						if(BungeeCord.getInstance().getPlayer(player) != null) {
							BungeeCord.getInstance().getPlayer(player).disconnect("§cDu wurdest §4PERMANENT §cvon Netzwerk gebannt. \n§7Grund§8: §cFehlverhalten\n \n§aDu kannst auf §ecmsnetwork.eu §aeinen Entbannungsantrag stellen.");
						}
						
						p.sendMessage(main.getBm().prefix + "§cDie IP §6" + ip + " §cwurde gebannt.");
					}
					else {
						p.sendMessage(main.getBm().prefix + "§cDie §6" + ip + " §c ist bereits gebannt.");
					}
				}
				else if(args.length > 1) {
					String player = args[0];
					String ip = args[0];
					
					if(BungeeCord.getInstance().getPlayer(player) != null) {
						ip = BungeeCord.getInstance().getPlayer(player).getAddress().getAddress().toString();
					}
					
					if(!main.getBm().isBanned(player)) {
						StringBuilder builder = new StringBuilder();
						for(int i = 1 ; i < args.length ; i++)builder.append(args[i]).append(" ");
						String message = builder.toString();
						
						main.getBm().ipban(ip, message);
						
						if(BungeeCord.getInstance().getPlayer(player) != null) {
							BungeeCord.getInstance().getPlayer(player).disconnect("§cDu wurdest §4PERMANENT §cvon Netzwerk gebannt. \n§7Grund§8: §c" + message + "\n \n§aDu kannst auf §ecmsnetwork.eu §aeinen Entbannungsantrag stellen.");
						}
					}
					else {
						p.sendMessage(main.getBm().prefix + "§cDie §6" + ip + " §c ist bereits gebannt.");
					}
				}
				else {
					p.sendMessage(main.getBm().prefix + "§cBitte benutze§7: §e/ipban §7<Onlineplayer/IP> (Grund)");
				}
			}
		}
	}
	
}
