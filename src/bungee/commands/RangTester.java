package bungee.commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import bungee.Main.main;
import bungee.MySQL.MySQL;
import bungee.manager.RankManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class RangTester extends Command {

	public RangTester() {
		super("testrank");
		update();
	}

	@Override
	public void execute(CommandSender s, String[] args) {
		
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) s;
			
			ResultSet set = MySQL.onQuery("SELECT * FROM testrank WHERE uuid = '" + p.getName() + "'");
			
			try {
				if(!set.next()) {
					String rank = RankManager.getRank(p);
					
					MySQL.onUpdate("INSERT INTO testrank(uuid, rank, until, complete) VALUES('" + p.getName() + "', '" + rank + "', DATE_ADD(NOW(), INTERVAL 1 HOUR), 0)");
					RankManager.setRank(p, "premium");
					
					p.sendMessage(new TextComponent("§6TestRank §8❘ §eHerzlichen Glückwunsch. §aDu darfst nun 1 Stunde §6Premium §atesten."));
				}
				else {
					p.sendMessage(new TextComponent("§6TestRank §8❘ §cDu hast bereits einmal den §6Premium §cRang getestet."));
				}
			} catch (SQLException e) { }
		}
		
		
	}

	
	public void update() {
		BungeeCord.getInstance().getScheduler().schedule(main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				ResultSet set = MySQL.onQuery("SELECT uuid, rank, until FROM testrank WHERE complete = 0 AND TIMESTAMPDIFF(MINUTE, NOW(),until) <= 0");
				
				try {
					while(set.next()) {
						String user = set.getString("uuid");
						String rank = set.getString("rank");
						RankManager.setRank(user, rank);
						MySQL.onUpdate("UPDATE testrank SET complete = 1 WHERE uuid = '" + user + "'");
						
						if(BungeeCord.getInstance().getPlayer(user) != null) {
							ProxiedPlayer p = BungeeCord.getInstance().getPlayer(user);
							if(p.isConnected()) {
								p.sendMessage(new TextComponent("§6TestRank §8❘ §cDeine Testzeit ist abgelaufen. §eWir hoffen dir hat der Rang gefallen."));
							}
						}
					}
				} catch (SQLException e) { }
			}
		}, 5, 5, TimeUnit.MINUTES);
	}
	
}
