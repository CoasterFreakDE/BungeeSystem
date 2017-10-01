package bungee.auth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;


import bungee.Main.main;
import bungee.manager.RankManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.connection.InitialHandler;
import net.md_5.bungee.event.EventHandler;

public class prejoin implements Listener
{
	
	public int slots = 50;


	
	
	public String  chars = "abcdefghijklmnopqrstuvwxyz0123456789_-ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public boolean checkName(String name) {
		
		for(char c : name.toCharArray()) {
			String cs = c + "";
			if(chars.indexOf(cs) <= -1) {
//				System.out.println("Name: " + name);
//				System.out.println("  > Zeichen: " + cs + " ist verboten!");
				return false;
			}
			else {
//				System.out.println("Name: " + name);
//				System.out.println("  > Zeichen: " + cs + " an Stelle " + chars.indexOf(cs));
			}
		}
		
		
		return true;
	}
	
	  @EventHandler
	  public void onAsyncPreLoginEvent(PreLoginEvent e)
	  {
	    InitialHandler handler = (InitialHandler)e.getConnection();
	    
	    if(!checkName(handler.getName())) {
	    	e.setCancelReason("§c§lKeine Sonderzeichen in Namen!\n§eBitte suche dir einen anderen aus.");
	    	e.setCancelled(true);
	    }
	    
//	    if(handler.getName().contains(" ")) {
//	    	e.setCancelReason("§cBitte keine Leerzeichen in Namen");
//	    	e.setCancelled(true);
//	    }
	    
//	    if(handler.getName().contains("§")) {
//	    	e.setCancelReason("§cBitte keine Sonderzeichen in Namen");
//	    	e.setCancelled(true);
//	    }
	    
	    if(handler.getName().length() > 16) {
	    	e.setCancelReason("§cNameslaenge bitte unter 16");
	    	e.setCancelled(true);
	    }
	    
	    if(isPremium(handler.getName())) {
	    	handler.setOnlineMode(true);
	    	System.out.println("OnlineMode true für " + handler.getName());
	    }
	    else {
	    	handler.disconnect("§cNur Premium Minecraft Spieler erlaubt.");
	    }
	    
	    
	    
	    
	    if(BungeeCord.getInstance().getOnlineCount() >= slots) {
	    	
	    	if(RankManager.getRank(handler.getName()).equalsIgnoreCase("spieler")) {
	    		handler.disconnect("§cDas Spielerlimit von §2§l" + slots + " §cist erreicht.\n§7§oKaufe dir §6§lPremium §7§oum trotzdem joinen zu können.\n \n§7§oInfos auf §etaymetic.net/shop");
	    	}

	    }
	    
	    //System.out.println(handler.getLoginRequest().getData());
	  }
	  
	@SuppressWarnings("deprecation")
	public void testForPremium(final ProxiedPlayer p) {
		
		if(!p.getPendingConnection().isOnlineMode()) {
			
			p.disconnect("§cCracked?");
			
			//NON PREMIUM USER
			
			//IS IN PREMIUM LIST?
			if(RankManager.isPremiumAuth(p)) {
					//RAUS MIT DIR
					p.disconnect("§cKannst du dir keinen eigenen \n§cMinecraft Account leisten?");
					return;
			}
			
			if(main.getAuth().getIps().containsKey(p.getName())) {
				if(!p.getAddress().getAddress().toString().equals(main.getAuth().getIps().get(p.getName()))) {
					System.out.println(p.getName() + " connecting from " + p.getAddress().getAddress().toString() + "  Saved: " + main.getAuth().getIps().get(p.getName()));
					main.getAuth().getIps().remove(p.getName());
					
					if(p.getServer() == null) {
						System.out.println("Premium: false - Server: null - auth - IP");
						BungeeCord.getInstance().getScheduler().schedule(main.getInstance(), new Runnable() {
							
							@Override
							public void run() {
								p.connect(BungeeCord.getInstance().getServerInfo("auth"));
								
							}
						}, 1, TimeUnit.SECONDS);
					}
					else if(!p.getServer().getInfo().getName().equalsIgnoreCase("auth")) {
						System.out.println("Premium: false - Server: all - auth - IP");
						BungeeCord.getInstance().getScheduler().schedule(main.getInstance(), new Runnable() {
							
							@Override
							public void run() {
								p.connect(BungeeCord.getInstance().getServerInfo("auth"));
								
							}
						}, 1, TimeUnit.SECONDS);
					}
					else {
						if(RankManager.isRegistered(p.getName())) {
							p.sendMessage("§7[§c§oAuth§7] §aBitte logge dich ein. §e/login <Dein Passwort>");
						}
						else {
							p.sendMessage("§7[§c§oAntiBot§7] §cBitte absolviere §4§fzuerst §cdas Jump n' Run bevor du dich §eregistrierst.");
						}
					}
				}
			}
			else {
				if(p.getServer() == null) {
					System.out.println("Premium: false - Server: null - auth");
					
					BungeeCord.getInstance().getScheduler().schedule(main.getInstance(), new Runnable() {
						
						@Override
						public void run() {
							p.connect(BungeeCord.getInstance().getServerInfo("auth"));
							
						}
					}, 1, TimeUnit.SECONDS);
				}
				else if(!p.getServer().getInfo().getName().equalsIgnoreCase("auth")) {
					System.out.println("Premium: false - Server: all - auth");
					BungeeCord.getInstance().getScheduler().schedule(main.getInstance(), new Runnable() {
						
						@Override
						public void run() {
							p.connect(BungeeCord.getInstance().getServerInfo("auth"));
							
						}
					}, 1, TimeUnit.SECONDS);
				}
				else {
					if(RankManager.isRegistered(p.getName())) {
						p.sendMessage("§7[§c§oAuth§7] §aBitte logge dich ein. §e/login <Dein Passwort>");
					}
					else {
						p.sendMessage("§7[§c§oAntiBot§7] §cBitte absolviere §4§fzuerst §cdas Jump n' Run bevor du dich §eregistrierst.");
					}
				}
			}
		}
		else {
			//PREMIUM USER -> ADD TO LIST
			
			if(p.getServer() == null) {
				System.out.println("Premium: true - Server: null - lobby");
				p.connect(BungeeCord.getInstance().getServerInfo("lobby"));
			}
			else if(p.getServer().getInfo().getName().equalsIgnoreCase("auth") && !p.getName().equals("Coaster_Freak") && !p.getName().equals("SnowPlaysDE")) {
				p.sendMessage("§7[§c§oAuth§7] §6§oWieso willst du auf den CrackedUsers Auth Server? ;)");
				
				System.out.println("Premium: true - Server: auth - lobby");
				p.connect(BungeeCord.getInstance().getServerInfo("lobby"));
			}
			
		}
	}
	
	
	@EventHandler
	public void serverChange(ServerSwitchEvent e) {
		testForPremium(e.getPlayer());
	}
	
	public boolean isPremium(String name)
	{
		    String output = readURL("https://api.mojang.com/users/profiles/minecraft/" + name);
		    
		    System.out.println(output);
		    
		      if(output == null)
		    	 return false;
		    
		      if (output.startsWith("{\"id\":\"")) {
		    	  return true;
		      }
		      else {
		    	  return false;
		      }
	}
	  
	  
	  private String readURL(String url)
	  {
	    try
	    {
	      HttpURLConnection con = (HttpURLConnection)new URL(url).openConnection();
	      
	      con.setRequestMethod("GET");
	      con.setRequestProperty("User-Agent", "SkinsRestorer");
	      con.setConnectTimeout(5000);
	      con.setReadTimeout(5000);
	      

	      StringBuilder output = new StringBuilder();
	      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	      String line;
	      while ((line = in.readLine()) != null)
	      {
	        output.append(line);
	      }
	      in.close();
	      
	      return output.toString();
	    }
	    catch (Exception e) {}
	    return "";
	  }
	  

}
