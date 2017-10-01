package bungee.commands;

import bungee.Main.main;
import bungee.manager.RankManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class GlistCommand extends Command
{

	public GlistCommand() {
		super("gl");
	}

	@Override
	public void execute(CommandSender s, String[] args) {
	
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer)s;
			
			if(RankManager.hasPermission(p, "Servers")) {
				if(args.length == 0) {
					
					TextComponent com = new TextComponent();
					TextComponent com2 = new TextComponent();
					
					for(String sv : BungeeCord.getInstance().getServers().keySet()) {
						ServerInfo info = BungeeCord.getInstance().getServers().get(sv);
						
						if(info.getPlayers().size() != 0) {
							TextComponent ser = new TextComponent("\n§7[§2" + sv + "§7] §7(§e" + info.getPlayers().size() + "§7) ");

							for(ProxiedPlayer t : info.getPlayers()) {
								ser.addExtra("§7§o" + t.getName() + " ");
							}
							
							com.addExtra(ser);
						}

					}
					
					com.addExtra(com2);
					p.sendMessage(com);
					
					TextComponent ser = new TextComponent(main.PREFIX + "§aEs sind derzeit §e" + BungeeCord.getInstance().getPlayers().size() + " Spieler §aonline.");
					ser.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Klicke um auf unsere Website zu gelangen.").create() ) );
					ser.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://invisibleblock.de"));
					
					p.sendMessage(ser);
				}
			}
			else {
				TextComponent ser = new TextComponent(main.PREFIX + "§aEs sind derzeit §e" + BungeeCord.getInstance().getPlayers().size() + " Spieler §aonline.");
				ser.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Klicke um auf unsere Website zu gelangen.").create() ) );
				ser.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://invisibleblock.de"));
				
				p.sendMessage(ser);
			}
		}
		
	}

	
	
}
