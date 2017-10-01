package bungee.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import bungee.Main.main;
import bungee.MySQL.MySQL;
import bungee.manager.RankManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class umfrage extends Command {

	public umfrage() {
		super("umfrage", "", new String[] {"ask"});
		running = false;
		frage = "";
		ja = 0;
		nein = 0;
		voted = new ArrayList<ProxiedPlayer>();
	}

	public static String frage;
	public static boolean running;
	public static int ja, nein;
	public static List<ProxiedPlayer> voted;
	
	@Override
	public void execute(CommandSender s, String[] args) {
		
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer)s;
			
			if(args.length > 0) {
				if(args[0].equalsIgnoreCase("ja")) {
					if(running) {
						if(!voted.contains(p)) {
							ja++;
							voted.add(p);
							TextComponent comp = new TextComponent();
							comp.addExtra("§6§lUmfrage §8❘ §a§oDu hast für §2§lJA §a§ogestimmt.");
							p.sendMessage(comp);
						}
						else {
							TextComponent comp = new TextComponent();
							comp.addExtra("§6§lUmfrage §8❘ §c§oDu hast bereits abgestimmt.");
							p.sendMessage(comp);
						}
					}
					else {
						TextComponent comp = new TextComponent();
						comp.addExtra("§6§lUmfrage §8❘ §c§oEs läuft derzeit keine Umfrage.");
						p.sendMessage(comp);
					}
					return;
				}
				else if(args[0].equalsIgnoreCase("nein")) {
					if(running) {
						if(!voted.contains(p)) {
							nein++;
							voted.add(p);
							TextComponent comp = new TextComponent();
							comp.addExtra("§6§lUmfrage §8❘ §a§oDu hast für §4§lNEIN §a§ogestimmt.");
							p.sendMessage(comp);
						}
						else {
							TextComponent comp = new TextComponent();
							comp.addExtra("§6§lUmfrage §8❘ §c§oDu hast bereits abgestimmt.");
							p.sendMessage(comp);
						}
					}
					else {
						TextComponent comp = new TextComponent();
						comp.addExtra("§6§lUmfrage §8❘ §c§oEs läuft derzeit keine Umfrage.");
						p.sendMessage(comp);
					}
					return;
				}
				else if(!RankManager.hasPermission(p, "Umfragen")) {
					return;
				}
			}
			else {
				return;
			}
		}
		
		if(running) {
			TextComponent comp = new TextComponent();
			comp.addExtra("§6§lUmfrage §8❘ §c§oEs läuft bereits eine Umfrage.");
			s.sendMessage(comp);
		}
		else {
			if(args.length > 1) {
				StringBuilder builder = new StringBuilder();
				for(int i = 0; i < args.length; i++) builder.append(args[i] + " ");
				
				frage = ChatColor.translateAlternateColorCodes('&', builder.toString());
				running = true;
				run();
				
				TextComponent comp = new TextComponent();
				comp.addExtra("§6§lUmfrage §8❘ §a§o" + frage+ " ");
				
				TextComponent compja = new TextComponent();
				compja.addExtra("§8[§2JA§8] ");
				compja.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/ask ja"));
				
				TextComponent compnein = new TextComponent();
				compnein.addExtra("§8[§4NEIN§8]");
				compnein.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/ask nein"));
				
				
				for(ProxiedPlayer p : BungeeCord.getInstance().getPlayers()) {
					p.sendMessage(comp);
					p.sendMessage(compja);
					p.sendMessage(compnein);
				}
			}
		}
	}
	
	public static void run() {
		BungeeCord.getInstance().getScheduler().schedule(main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				int state = 0;
				if(ja < nein) {
					state = 1;
				}
				else if(ja == nein) {
					state = 2;
				}
				
				
				TextComponent comp = new TextComponent();
				comp.addExtra("§6§lUmfrage §8❘ §a§o" + frage+ " ");
				TextComponent comp2 = new TextComponent();
				if(state == 0) {
					comp2.addExtra("§6§lUmfrage §8❘ §a§o" +" §2§lJA§8(§a" + ja + "§8)  §4§lNEIN§8(§c" + nein + "§8)");
				}
				else if(state == 1) {
					comp2.addExtra("§6§lUmfrage §8❘ §a§o" +" §2§lJA§8(§c" + ja + "§8)  §4§lNEIN§8(§a" + nein + "§8)");
				}
				else {
					comp2.addExtra("§6§lUmfrage §8❘ §a§o" +" §2§lJA§8(§e" + ja + "§8)  §4§lNEIN§8(§e" + nein + "§8)");
				}
				
				
				for(ProxiedPlayer p : BungeeCord.getInstance().getPlayers()) {
					p.sendMessage(comp);
					p.sendMessage(comp2);
				}
				
				MySQL.onUpdate("INSERT INTO Umfragen(frage, ja, nein) VALUES('" + frage + "', '" + ja + "', '" + nein + "')");
				running = false;
				voted.clear();
			}
		}, 1, TimeUnit.MINUTES);
	}
}
