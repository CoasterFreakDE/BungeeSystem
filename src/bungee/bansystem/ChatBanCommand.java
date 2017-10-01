package bungee.bansystem;

import bungee.Main.main;
import bungee.manager.RankManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ChatBanCommand extends Command
{

	public ChatBanCommand() {
		super("mute");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender s, String[] args) {
		
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) s;
			
			if(RankManager.hasPermission(p, "mute")) {
				if(args.length == 1) {
					String player = args[0];
					
					if(!main.getBm().isMuted(player)) {
						main.getBm().setMuted(player, -1, "Fehlverhalten");
						
						if(BungeeCord.getInstance().getPlayer(player) != null) {
							BungeeCord.getInstance().getPlayer(player).sendMessage(main.getBm().prefix + "§cDu wurdest §4PERMANENT §cim Netzwerk Chat gemuted. \n§6Grund: §7§oFehlverhalten");
						}
						
						p.sendMessage(main.getBm().prefix + "§cDer Spieler wurde gemuted.");
					}
					else {
						p.sendMessage(main.getBm().prefix + "§cDer Spieler ist bereits gemuted.");
					}
				}
				else if(args.length == 2) {
					String player = args[0];
					String grund = args[2];
					
					if(!main.getBm().isMuted(player)) {
						main.getBm().setMuted(player, -1, grund);
						
						if(BungeeCord.getInstance().getPlayer(player) != null) {
							BungeeCord.getInstance().getPlayer(player).sendMessage(main.getBm().prefix + "§cDu wurdest §4PERMANENT §cim Netzwerk Chat gemuted. \n§6Grund: §7§o" + grund);
						}
						
						p.sendMessage(main.getBm().prefix + "§cDer Spieler wurde gemuted.");
					}
					else {
						p.sendMessage(main.getBm().prefix + "§cDer Spieler ist bereits gemuted.");
					}
				}
				else if(args.length == 4) {
					String player = args[0];
					
					if(!main.getBm().isMuted(player)) {
						String anzahl = args[1];
						String einheit = args[2];
						String grund = args[3];
						int time = 0;
						int timeNoFormat = 0;
						String sE = "Minuten";
						
						try {
							time = Integer.parseInt(anzahl);
							timeNoFormat = time;
						}
						catch(NumberFormatException ex) { }
						
						if(einheit.equalsIgnoreCase("m")) {
							sE = "Minuten";
						}
						else if(einheit.equalsIgnoreCase("h")) {
							time = time * 60;
							sE = "Stunden";
						}
						else if(einheit.equalsIgnoreCase("d")) {
							time = time * 60 * 24;
							sE = "Tage";
						}
						else {
							p.sendMessage(main.getBm().prefix + "§cFalsche einheit.");
							return;
						}
						
						main.getBm().setMuted(player, time, grund);
						
						if(BungeeCord.getInstance().getPlayer(player) != null) {
							BungeeCord.getInstance().getPlayer(player).sendMessage(main.getBm().prefix + "§cDu wurdest für §4" + timeNoFormat + " " + sE + " §cim Netzwerk Chat gemuted. \n§6Grund: §7§o" + grund);
						}
						
						p.sendMessage(main.getBm().prefix + "§cDer Spieler wurde gemuted.");
					}
					else {
						p.sendMessage(main.getBm().prefix + "§cDer Spieler ist bereits gemuted.");
					}
				}
				else {
					p.sendMessage(main.getBm().prefix + "§cBitte benutze§7: §e/mute §7<Spieler> (Anzahl) (Einheit[m|h|d]) (Grund)");
				}
			}
		}
		
		
	}

	
	
}
