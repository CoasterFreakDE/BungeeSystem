package bungee.commands;

import bungee.manager.MathUtils;
import bungee.manager.RankManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class rank extends Command
{

	public rank() {
		super("rank");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender s, String[] args)
	{
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) s;
			
			if(!p.hasPermission("BungeeSystem.rank") && !RankManager.hasPermission(p, "rank"))
				return;
		}
		
		if(args.length == 2) {
			String player = args[0];
			String rank = args[1];
			
			RankManager.setRank(player, rank);
			s.sendMessage("§7[§c§oRanks§7] §aGruppe gesetzt. §7(§6" + rank + "§7)");
		}
		else if(args.length == 3) {
			String player = args[0];
			String rank = args[1];
			String timeS = args[2];
			int time = 0;
			
			if(MathUtils.isInt(timeS)) {
				time = Integer.parseInt(timeS);
				ProxiedPlayer pl = BungeeCord.getInstance().getPlayer(player);
				
				if(pl != null) {
					RankManager.setTimedRank(pl.getUniqueId().toString(), rank, time);
					s.sendMessage(new TextComponent("§aZeitrang gesetzt: §e" + pl.getName() + " -> §6" + rank));
					s.sendMessage(new TextComponent(" -> §9+" + time + " Monat(e)"));
				}
				else {
					s.sendMessage(new TextComponent("§cSpieler ist nicht online"));
				}
			}
			else {
				s.sendMessage(new TextComponent("§cZeit muss eine GanzZahl sein §7§o[1,2,3,...]"));
			}
		}
		else {
			s.sendMessage("§7[§c§oRanks§7] §6Bitte benutze: §e/rank <Player> <Rank>");
		}
		
	}

	
	
}
