package bungee.commands;

import bungee.Main.main;
import bungee.MySQL.MySQL;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class bs extends Command
{

	public bs(main m) {
		super("bs");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender s, String[] args) {
		
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) s;
			
			if(args.length == 1) {
				String sub = args[0];
				
				if(sub.equalsIgnoreCase("reload") || sub.equalsIgnoreCase("rl")) {
					if(p.hasPermission("BungeeSystem.reload")) {
						main.reloadConfig();
						main.modt();
						p.sendMessage("§3Configuration reloaded.");
					}
					else {
						p.sendMessage("§cNice Try ;) §3Aber ohne Permissions wird das nix.");
					}
				}
				else if(sub.equalsIgnoreCase("mysql")) {
					if(p.hasPermission("BungeeSystem.reload")) {
						MySQL.onDisconect();
						MySQL.onConnect();
						p.sendMessage("§3MySQL reloaded.");
					}
					else {
						p.sendMessage("§cNice Try ;) §3Aber ohne Permissions wird das nix.");
					}
				}
				else {
					sendHelp(p);
					//p.sendMessage("Sub: " + sub);
				}
			}
			else {
				sendHelp(p);
			}
			
		}
		else {
			main.reloadConfig();
			main.modt();
			s.sendMessage("§3Configuration reloaded.");
		}
		
		
	}

	@SuppressWarnings("deprecation")
	public void sendHelp(ProxiedPlayer p) {
		
		p.sendMessage("§6§l[]§8============§6§l[ §f§lBungeeSystem §6§l]§8============§6§l[]");
		p.sendMessage("");
		p.sendMessage("                  §ePlugin by §b§oCoaster_Freak");
		p.sendMessage("                          §eVersion 1.0");
		p.sendMessage("");
		p.sendMessage("  §e/bs reload §3Reloade die Configs");
		p.sendMessage("");
		p.sendMessage("§6§l[]§8============§6§l[ §f§lBungeeSystem §6§l]§8============§6§l[]");
		
	}
	
}
