package bungee.commands;

import bungee.MySQL.MySQL;
import bungee.manager.RankManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class coins extends Command {

	public coins() {
		super("coins");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender s, String[] args) {
		
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) s;
			
			if(RankManager.hasPermission(p, "Coins")) {
				if(args.length == 2) {
					String name = args[0];
					String coinsS = args[1];
					
					if(isNmb(coinsS)) {
						int coins = Integer.parseInt(coinsS);
						
						MySQL.onUpdate("UPDATE CMS SET money = money + '" + coins + "' WHERE name = '" + name + "'");
						s.sendMessage("§7System | §aCoins get.");
					}
					else if(coinsS.equalsIgnoreCase("enderdragon")) {
						MySQL.onUpdate("INSERT INTO Reittiere(name, tier) VALUES('"+ name + "', 'Enderdragon')");
						s.sendMessage("§7System | §aEnderdrachen alarm.");
					}
					else if(coinsS.equalsIgnoreCase("mysterybox")) {
						MySQL.onUpdate("UPDATE mysterybox SET mbox = mbox + 1 WHERE name = '" + name + "'");
						s.sendMessage("§7System | §aMystery Box");
					}
					else if(coinsS.equalsIgnoreCase("mysterykey")) {
						MySQL.onUpdate("UPDATE mysterybox SET mkey = mkey + 1 WHERE name = '" + name + "'");
						s.sendMessage("§7System | §aMystery key.");
					}
					else if(coinsS.equalsIgnoreCase("specialbox")) {
						MySQL.onUpdate("UPDATE mysterybox SET sbox = sbox + 1 WHERE name = '" + name + "'");
						s.sendMessage("§7System | §aSpecial Box.");
					}
					else if(coinsS.equalsIgnoreCase("specialkey")) {
						MySQL.onUpdate("UPDATE mysterybox SET skey = skey + 1 WHERE name = '" + name + "'");
						s.sendMessage("§7System | §aSpecial key.");
					}
					else {
						s.sendMessage("§e/coins [Name] [Amount]");
					}
				}
				else {
					p.sendMessage("§bMy-Server.ME §8❘ §a§oDeine Coins: §e§o" + RankManager.getCoins(p));
				}
			}
			else {
				p.sendMessage("§bMy-Server.ME §8❘ §a§oDeine Coins: §e§o" + RankManager.getCoins(p));
			}
		}
		else {
			if(args.length == 2) {
				String name = args[0];
				String coinsS = args[1];
				
				if(isNmb(coinsS)) {
					int coins = Integer.parseInt(coinsS);
					
					MySQL.onUpdate("UPDATE CMS SET money = money + '" + coins + "' WHERE name = '" + name + "'");
					s.sendMessage("§7System | §aCoins get.");
				}
				else if(coinsS.equalsIgnoreCase("enderdragon")) {
					MySQL.onUpdate("INSERT INTO Reittiere(name, tier) VALUES('"+ name + "', 'Enderdragon')");
					s.sendMessage("§7System | §aEnderdrachen alarm.");
				}
				else if(coinsS.equalsIgnoreCase("mysterybox")) {
					MySQL.onUpdate("UPDATE mysterybox SET mbox = mbox + 1 WHERE name = '" + name + "'");
					s.sendMessage("§7System | §aMystery Box");
				}
				else if(coinsS.equalsIgnoreCase("mysterykey")) {
					MySQL.onUpdate("UPDATE mysterybox SET mkey = mkey + 1 WHERE name = '" + name + "'");
					s.sendMessage("§7System | §aMystery key.");
				}
				else if(coinsS.equalsIgnoreCase("specialbox")) {
					MySQL.onUpdate("UPDATE mysterybox SET sbox = sbox + 1 WHERE name = '" + name + "'");
					s.sendMessage("§7System | §aSpecial Box.");
				}
				else if(coinsS.equalsIgnoreCase("specialkey")) {
					MySQL.onUpdate("UPDATE mysterybox SET skey = skey + 1 WHERE name = '" + name + "'");
					s.sendMessage("§7System | §aSpecial key.");
				}
			}
			else {
				s.sendMessage("§e/coins [Name] [Amount]");
			}
		}		
	}
	
	
	public boolean isNmb(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}

}
