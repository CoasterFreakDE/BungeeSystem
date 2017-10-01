package bungee.bansystem;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import bungee.MySQL.MySQL;
import bungee.manager.RankManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReportCommand extends Command
{

	public ReportCommand() {
		super("report", null, new String[] { "r", "reports"});
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender s, String[] args)
	{
		String prefix = "§7[§c§oReports§7] §7";
		
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) s;
	
			/*
			 * 	 Report#1
			 * 		.reporter
			 * 		.reported	
			 * 		.message
			 * 		.open
			 * 
			 * 			Status:
			 * 			0  		   1 		  2
			 * 		  Offen   Übernommen	Fertig
			 * 
			 * 
			 */
			
			
			if(args.length >= 2) {
				if(args.length == 3) {
					if(args[0].equalsIgnoreCase("take")) {
						if(RankManager.hasPermission(p, "reports")) {
							String reporter = args[1];
							String secret = args[2];
							
							ResultSet set = MySQL.onQuery("SELECT * FROM reports WHERE reporter = '" + reporter + "' AND secret = '" + secret + "'");
							
							try {
								if(set.next()) {
									int status = set.getInt("status");
									
									if(status == 0) {
										/* Ist Frei -> Annehmen */
										
										MySQL.onUpdate("UPDATE reports SET status = 1 , moderator = '" + p.getName() + "' WHERE reporter = '" + reporter + "' AND secret = '" + secret + "'");
										
										TextComponent msg1 = new TextComponent(prefix + "§7§oReport übernommen: §6" + secret);
										msg1.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Webreport").create() ) );
										msg1.setClickEvent(new ClickEvent( ClickEvent.Action.OPEN_URL, "http://my-server.me/reports/index.php?name=" + reporter + "&secret=" + secret + "&mod=" + p.getName()));
										p.sendMessage(msg1);
									}
									else if(status == 1){
										String mod = set.getString("moderator");
										p.sendMessage(new TextComponent(prefix + "§cDer Report wurde bereits von §e" + mod +  " §cübernommen."));
									}
									else {
										p.sendMessage(new TextComponent(prefix + "§cDieser Report wurde geschlossen."));
									}
								}
								else {
									p.sendMessage(new TextComponent(prefix + "§cDieser Report wurde nicht gefunden."));
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
									
									
							return;
						}
					}
				}
				
				String reporter = p.getName();
				String reported = args[0];
				String message = "";
				
				if(BungeeCord.getInstance().getPlayer(reported) == null) {
					p.sendMessage(prefix + "§cDieser Spieler ist derzeit nicht online.");
					p.sendMessage(prefix + "§cBei einem schweren Vergehen reporte den User bei uns im Teamspeak.");
					return;
				}
				
				StringBuilder builder = new StringBuilder();
				for(int i = 1 ; i < args.length ; i++)builder.append(args[i]).append(" ");
				message = builder.toString();

				
				ResultSet set = MySQL.onQuery("SELECT * FROM reports WHERE reporter = '" + reporter + "' AND reported = '" + reported + "' AND STATUS != 2");
				
				try {
					if(set.next()) {
						p.sendMessage(prefix + "§cDu hast diesen Spieler bereits reported.");
						return;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
								
				/* 	 Reporting Player	*/
				String server = p.getServer().getInfo().getName();
				
				char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
				StringBuilder sb = new StringBuilder();
				Random random = new Random();
				for (int i = 0; i < 8; i++) {
				    char c = chars[random.nextInt(chars.length)];
				    sb.append(c);
				}
				
				String code = sb.toString();
				
				MySQL.onUpdate("INSERT INTO reports(reporter, reported, server, grund, time, secret) VALUES('" + reporter + "', '" + reported + "', '" + server + "', '" + message + "', NOW(), '" + code + "')");
				
				
				
				p.sendMessage(prefix + "§aDu hast den Spieler §6" + reported + " §areported.");
				
				TextComponent msg1 = new TextComponent(prefix + "§7§oDein Reportcode: §6" + code);
				msg1.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Webreport").create() ) );
				msg1.setClickEvent(new ClickEvent( ClickEvent.Action.OPEN_URL, "http://my-server.me/reports/index.php?name=" + reporter + "&secret=" + code));
				p.sendMessage(msg1);
				
				
				for(ProxiedPlayer pl : BungeeCord.getInstance().getPlayers()) {
					
					if(RankManager.hasPermission(pl, "reports")) {
						TextComponent msg = new TextComponent(prefix + " §7Der Spieler §e" + reported + " §7wurde von §6" + reporter + " §7auf §c" + server +  " §7wegen §c" + message + " §7reported." );
						msg.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Übernehmen").create() ) );
						msg.setClickEvent(new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/report take " + reporter + " " + code));
						
						pl.sendMessage(msg);
					}	
					
				}
			}
			else {
				
				p.sendMessage(prefix + "§cBitte benutze: §e/report <Spieler> <Nachricht§7(Grund)§e>");
				
//				System.out.println("CMD: " + args.toString());
//				if(args.length == 1) {
//					if(args[0].equalsIgnoreCase("list")) {
//						if(RankManager.hasPermission(p.getName(), "reports")) {
//							
//							if(reports.getKeys() == null) {
//								p.sendMessage(prefix + "§cDerzeit ist kein Report offen.");
//								return;
//							}
//							
//							p.sendMessage(prefix + "§a§l§oALLE REPORTS");
//							System.out.println("Checking " + reports.getKeys());
//							
//							for(String i : reports.getKeys()) {
//								System.out.println("Checking " + i);
//								if(reports.getBoolean(i + ".open")) {
//									System.out.println(i + " ist offen.");
//									p.sendMessage(" §e" +i);
//									p.sendMessage("    §aReporter: §7" + reports.getString(i + ".reporter"));
//									p.sendMessage("    §cReported: §7" + reports.getString(i + ".reported"));
//									p.sendMessage("    §6Message: §7" + reports.getString(i + ".message"));
//								}
//								else {
//									System.out.println(i + " ist geschlossen.");
//								}
//							}
//							
//							return;
//						}
//						else {
//							p.sendMessage(prefix + "§cKeine Permission.");
//						}
//					}
//					else {
//						p.sendMessage(prefix + "§cBitte benutze: §e/report <Spieler> <Nachricht§7(Grund)§e>");
//					}
//				}
//				else {
//					p.sendMessage(prefix + "§cBitte benutze: §e/report <Spieler> <Nachricht§7(Grund)§e>");
//				}
			}
		}
	}

	public int getSize() {
		ResultSet set = MySQL.onQuery("SELECT * FROM reports WHERE status = 0");
		int i = 0;
		
		try {
			while(set.next()) {
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return i;
	}
	
}
