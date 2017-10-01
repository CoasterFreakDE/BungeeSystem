package bungee.commands;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import bungee.Main.main;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

public class teamchat extends Command
{

	public teamchat(main m) {
		super("tc");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender s, String[] args) {

		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer)s;
			
			Configuration config = main.getConfiguration();
			List<String> players = config.getStringList("teamchat");
			
			
			if(players.contains(p.getName())) {
				
				if(args.length == 0) {
					p.sendMessage("§7Bitte füge eine Nachricht ein.");
					return;
				}
				
				String msg = "";
				
				StringBuilder builder = new StringBuilder();
				for(int i = 0 ; i < args.length ; i++)builder.append(args[i]).append(" ");
				
				msg = ChatColor.translateAlternateColorCodes('&', builder.toString());
				
				TextComponent message = new TextComponent( "§bTeam §6" + p.getDisplayName() );
		        Calendar cal = Calendar.getInstance();
		        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Gesendet um: " + sdf.format(cal.getTime()) + "Uhr").create() ) );
				
				message.addExtra(" §7> ");
				message.addExtra(msg);
				
				for(ProxiedPlayer pl : BungeeCord.getInstance().getPlayers()) {
					if(players.contains(pl.getName())) {
						pl.sendMessage(message);
					}
				}
			}
			else {
				p.sendMessage("§cKeine Berechtigung.");
			}
		}	
	}

	
	
}
