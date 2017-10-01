package bungee.commands;

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

public class ServerCommand extends Command
{

	public ServerCommand() {
		super("server");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender s, String[] args) {
	
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer)s;
			
			if(RankManager.hasPermission(p, "Servers")) {
				if(args.length == 0) {					
					p.sendMessage("§6Du bist derzeit auf §2§o" + p.getServer().getInfo().getName());
					
					TextComponent com = new TextComponent();
					
					for(String sv : BungeeCord.getInstance().getServers().keySet()) {
						ServerInfo info = BungeeCord.getInstance().getServers().get(sv);

						TextComponent ser = new TextComponent(" §a§o" + sv);
						ser.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7" + info.getPlayers().size() + " Spieler\n§7§oKlicke zum connecten.").create() ) );
						ser.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + sv));
						
						com.addExtra(ser);

					}
					
					p.sendMessage(com);
					
				}
				else {
					//Send to server
					
					if(args.length == 1) {
						String sv = args[0];
					
						if(BungeeCord.getInstance().getServerInfo(sv) != null) {
							p.connect(BungeeCord.getInstance().getServerInfo(sv));
						}
						else {
							p.sendMessage("§7Dieser Server existert nicht.");
						}
					}
					else {
						p.sendMessage("§7Bitte benutze §6/server <Servername>");
					}
				}
			}
		}
		
	}

	
	
}
