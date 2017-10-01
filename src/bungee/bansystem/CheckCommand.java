package bungee.bansystem;

import bungee.Main.main;
import bungee.manager.RankManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CheckCommand extends Command
{

	public CheckCommand() {
		super("check");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender s, String[] args)
	{
		
		
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) s;
			
			
			if(RankManager.hasPermission(p, "check")) {
				
				if(args.length == 1) {
					String player = args[0];
					
					
					if(BungeeCord.getInstance().getPlayer(player) != null) {
						ProxiedPlayer t = BungeeCord.getInstance().getPlayer(player);
						
						if(t.getPendingConnection().isOnlineMode()) {
							TextComponent message = new TextComponent( "§7Der Spieler §e" + t.getName() + " §7spielt mit einem §aOnlineAccount." );
							message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Ban " + t.getName()).create() ) );
							message.setClickEvent(new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "/gban " + t.getName()));
							
							p.sendMessage(message);
						}
						else {
							TextComponent message = new TextComponent( "§7Der Spieler §e" + t.getName() + " §7spielt mit einem §cCrackedAccount." );
							message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7IPBan " + t.getName() + " §8(§6" + t.getAddress().getAddress().toString() + "§8)").create() ) );
							message.setClickEvent(new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "/ipban " + t.getAddress().getAddress().toString()));
							
							p.sendMessage(message);
						}
					}
					else {
						p.sendMessage(main.getBm().prefix + "§cSpieler nicht online.");
					}
				}
				else {
					p.sendMessage(main.getBm().prefix + "§7Benutze §e/check <Spieler>");
				}
			}
		}
	}
}
