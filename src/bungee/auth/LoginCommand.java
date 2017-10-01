package bungee.auth;

import bungee.Main.main;
import bungee.manager.PasswordManager;
import bungee.manager.RankManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LoginCommand extends Command
{

	public LoginCommand() {
		super("login");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender s, String[] args) {
		
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) s;
			
			
			if(RankManager.isRegistered(p.getName())) {
				if(args.length == 1) {
					String pass = args[0];
					
					if(!main.getAuth().getIps().containsKey(p.getName())) {
						if(PasswordManager.decrype(RankManager.getPasswordHash(p)).equals(pass)) {
							//EINGELOGGT
							
							main.getAuth().getIps().put(p.getName(), p.getAddress().getAddress().toString());
							
							
							p.sendMessage("§7[§c§oAuth§7] §aEingeloggt. §3§oDu wirst nun auf die Lobby geleitet.");
							p.connect(BungeeCord.getInstance().getServerInfo("lobby"));
						}
						else {
							p.disconnect("§7[§c§oAuth§7] §cDu hast ein falsches Passwort eingegeben.");
							System.out.println("PasswortHash: " + RankManager.getPasswordHash(p) + "  EingegebenHash: " + PasswordManager.encrype(pass));
						}
					}
					else {
						p.sendMessage("§7[§c§oAuth§7] §cDu bist bereits eingeloggt.");
						
						if(p.getServer().getInfo().getName().equalsIgnoreCase("auth"))
							p.connect(BungeeCord.getInstance().getServerInfo("lobby"));
					}
				}
				else {
					p.sendMessage("§7[§c§oAuth§7] §aBitte benutze §e/login <Dein Passwort>");
				}
			}
			else {
				if(RankManager.canRegister(p.getName())) {
					p.sendMessage("§7[§c§oAuth§7] §cBitte registriere dich zuerst.");
				}
				else {
					p.sendMessage("§7[§c§oAntiBot§7] §cBitte absolviere §4§fzuerst §cdas Jump n' Run bevor du dich §eregistrierst.");
				}
			}
		}
		
	}

	
	
}
