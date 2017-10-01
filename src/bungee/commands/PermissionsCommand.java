package bungee.commands;

import bungee.manager.RankManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PermissionsCommand extends Command
{

	public PermissionsCommand() {
		super("perms");
		
	}

	@Override
	public void execute(CommandSender s, String[] args) {
	
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) s;
			
			if(!RankManager.hasPermission(p, "ManagePermissions")) {
				return;
			}
		}
		
		if(args.length == 3) {
			String sub = args[0];
			String group = args[1];
			String perm = args[2];
			
			if(sub.equalsIgnoreCase("add")) {
				RankManager.addPermission(group, perm);
				s.sendMessage(new TextComponent("§7§oPermission §e" + perm + " §7§ozu Gruppe §a" + group + " §7§ohinzugefügt."));
			}
			else if(sub.equalsIgnoreCase("del")) {
				RankManager.removePermission(group, perm);
				s.sendMessage(new TextComponent("§7§oPermission §e" + perm + " §7§ovon Gruppe §a" + group + " §7§oentfernt."));
			}
			else {
				s.sendMessage(new TextComponent("§7§oBenutze /perms add/del [Group] [Permission]"));
			}
		}
		else {
			s.sendMessage(new TextComponent("§7§oBenutze /perms add/del [Group] [Permission]"));
		}
	}

	
	
}
