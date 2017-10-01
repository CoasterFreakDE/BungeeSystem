package bungee.bansystem;

import bungee.Main.main;
import bungee.manager.RankManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BanCommand extends Command
{

	public BanCommand() {
		super("gban");
	}
	
	
	/*
	 * 		SPEICHER: mysql  TABLE: bans (name, grund, zeit)
	 */
	
	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender s, String[] args) {
		
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) s;
			
			if(RankManager.hasPermission(p, "ban")) {
				if(args.length == 1) {
					String player = args[0];
					
					if(player.equalsIgnoreCase("list")) {
						for(String st : main.getBm().bannedPlayers()) {
							p.sendMessage(" §7" + st);
						}
					}
					else {
						if(!main.getBm().isBanned(player)) {
							main.getBm().ban(player, "Fehlverhalten");
							
							if(BungeeCord.getInstance().getPlayer(player) != null) {
								BungeeCord.getInstance().getPlayer(player).disconnect("§cDu wurdest §4PERMANENT §cvon Netzwerk gebannt. \n§7Grund§8: §cFehlverhalten\n \n§aDu kannst auf §ecmsnetwork.eu §aeinen Entbannungsantrag stellen.");
							}
							
							p.sendMessage(main.getBm().prefix + "§cDer Spieler wurde gebannt.");
						}
						else {
							p.sendMessage(main.getBm().prefix + "§cDer Spieler ist bereits gebannt.");
						}
					}
				}
				else if(args.length == 4) {
					String player = args[0];
					
					if(!main.getBm().isBanned(player)) {
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
						
						main.getBm().ban(player, grund, "" + time);
						
						if(BungeeCord.getInstance().getPlayer(player) != null) {
							BungeeCord.getInstance().getPlayer(player).disconnect(main.getBm().prefix + "§cDu wurdest für §4" + timeNoFormat + " " + sE + " §cvom Netzwerk gebannt. \n§6Grund: §7§o" + grund);
						}
						
						p.sendMessage(main.getBm().prefix + "§cDer Spieler wurde gebannt.");
					}
					else {
						p.sendMessage(main.getBm().prefix + "§cDer Spieler ist bereits gebannt.");
					}
				}
				else if(args.length > 1) {
					String player = args[0];
					
					if(!main.getBm().isBanned(player)) {
						StringBuilder builder = new StringBuilder();
						for(int i = 1 ; i < args.length ; i++)builder.append(args[i]).append(" ");
						String message = builder.toString();
						
						main.getBm().ban(player, message);
						
						if(BungeeCord.getInstance().getPlayer(player) != null) {
							BungeeCord.getInstance().getPlayer(player).disconnect("§cDu wurdest §4PERMANENT §cvon Netzwerk gebannt. \n§7Grund§8: §c" + message);
						}
						
						p.sendMessage(main.getBm().prefix + "§cDer Spieler wurde gebannt.");
					}
					else {
						p.sendMessage(main.getBm().prefix + "§cDer Spieler ist bereits gebannt.");
					}
				}
				else {
					p.sendMessage(main.getBm().prefix + "§cBitte benutze§7: §e/gban §7<Spieler> (Grund)");
				}
			}
		}
	}

}
