package bungee.auth;

import bungee.Main.main;
import bungee.manager.RankManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class RegisterCommand extends Command
{

	public RegisterCommand() {
		super("register");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender s, String[] args) {
		
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) s;

			
			if(RankManager.canRegister(p.getName())) {
				if(!RankManager.isRegistered(p.getName())) {
					if(args.length == 1) {
						String pass = args[0];
						
						RankManager.setIsRegistered(p.getName(), true);
						RankManager.setPassword(p.getName(), pass);
						main.saveConfig();
						
						main.getAuth().getIps().put(p.getName(), p.getAddress().getAddress().toString());
						p.sendMessage("§7[§c§oAuth§7] §aErfolgreich registriert. §3§oDu wirst nun auf die Lobby geleitet");

						p.connect(BungeeCord.getInstance().getServerInfo("lobby"));
					}
					else {
						p.sendMessage("§7[§c§oAuth§7] §aBitte benutze §e/register <Dein Passwort>");
					}
				}
				else {
					p.sendMessage("§7[§c§oAuth§7] §aBitte benutze §e/login <Dein Passwort>");
				}
			}
			else {
				p.sendMessage("§7[§c§oAntiBot§7] §cBitte absolviere §4§fzuerst §cdas Jump n' Run bevor du dich §eregistrierst.");
			}
		}
	}
}
