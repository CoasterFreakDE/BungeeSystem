package bungee.Main;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import bungee.MySQL.MySQL;
import bungee.MySQL.MySQLData;
import bungee.auth.AuthManager;
import bungee.auth.LoginCommand;
import bungee.auth.RegisterCommand;
import bungee.auth.prejoin;
import bungee.bansystem.BanCommand;
import bungee.bansystem.BanManager;
import bungee.bansystem.ChatBanCommand;
import bungee.bansystem.ChatClear;
import bungee.bansystem.ChatUnBanCommand;
import bungee.bansystem.CheckCommand;
import bungee.bansystem.IPBanCommand;
import bungee.bansystem.KickCommand;
import bungee.bansystem.ReportCommand;
import bungee.bansystem.UnBanCommand;
import bungee.bansystem.UnIPBanCommand;
import bungee.commands.GlistCommand;
import bungee.commands.PermissionsCommand;
import bungee.commands.RangTester;
import bungee.commands.ServerCommand;
import bungee.commands.bs;
import bungee.commands.coins;
import bungee.commands.hub;
import bungee.commands.move;
import bungee.commands.onlinetime;
import bungee.commands.ping;
import bungee.commands.rank;
import bungee.commands.teamchat;
import bungee.commands.teamchatmanager;
import bungee.commands.toggleChat;
import bungee.commands.umfrage;
import bungee.commands.whereami;
import bungee.listener.chatmanager;
import bungee.listener.join;
import bungee.manager.AutoChat;
import bungee.manager.RankManager;
import bungee.manager.ServerManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class main extends Plugin
{

	private static Configuration configuration, chatfilter, motd, mysql, reports, automessage;
	private static File datafolder;
	private static AuthManager auth;
	private static BanManager bm;
	private static main instance;
	public static String PREFIX = "§8| §aSultex.de §8» §7";
	
	public static List<String> motd1;
	public static List<String> motd2;
	
	
	@Override
	public void onEnable() {
		instance = this;
		
		//Listener
		ProxyServer.getInstance().getPluginManager().registerListener(this, new join());
		ProxyServer.getInstance().getPluginManager().registerListener(this, new chatmanager());
		
		//AUTH
		ProxyServer.getInstance().getPluginManager().registerListener(this, new prejoin());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new LoginCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new RegisterCommand());
		
		
		//Commands
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new ping(this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new whereami(this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new teamchat(this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new teamchatmanager(this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new move(this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new rank());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new coins());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new ReportCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new ServerCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new GlistCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new umfrage());		
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new hub());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new onlinetime());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new RangTester());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new toggleChat());
		
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new bs(this));
		
		bm = new BanManager();
		
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new ChatClear());
		ProxyServer.getInstance().getPluginManager().registerCommand(this , new BanCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this , new UnBanCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this , new IPBanCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this , new UnIPBanCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CheckCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new KickCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new ChatBanCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new ChatUnBanCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new PermissionsCommand());
		
//		auth = new AuthManager();
		
		
		datafolder = getDataFolder();
		
		if(!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		
		File file = new File(getDataFolder(), "config.yml");
		
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		file = new File(getDataFolder(), "chatfilter.yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			chatfilter = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		file = new File(getDataFolder(), "motd.yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			motd = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		file = new File(getDataFolder(), "mysql.yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			mysql = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		file = new File(getDataFolder(), "reports.yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			reports = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		file = new File(getDataFolder(), "automessage.yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			automessage = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		new MySQLData();
		MySQL.onConnect();
		onReconnectMySQL();
		

		modt();
		AutoChat.onAutoChat();
		RankManager.scheduler();
		ServerManager.onlineTimer();
		
		System.out.println("[BungeeSystem] Plugin aktiviert.");
	}
	
	@Override
	public void onDisable() {
		MySQL.onDisconect();
	}
	
	public static AuthManager getAuth() {
		return auth;
	}
	
	public static BanManager getBm() {
		return bm;
	}
	
	public static Configuration getConfiguration() {
		return configuration;
	}
	
	public static Configuration getChatfilter() {
		return chatfilter;
	}
	
	public static File getDatafolder() {
		return datafolder;
	}
	
	public static Configuration getMotd() {
		return motd;
	}
	
	public static Configuration getAutomessage() {
		return automessage;
	}
	
	
	public static void modt() {
		if(motd.getStringList("Motd1") == null) {
			motd1 = new ArrayList<String>();
			
			motd.set("Motd1", motd1);
		}
		else {
			motd1 = motd.getStringList("Motd1");
		}
		
		if(motd.getStringList("Motd2") == null) {
			motd2 = new ArrayList<String>();
			
			motd.set("Motd2", motd2);
		}
		else {
			motd2 = motd.getStringList("Motd2");
		}
		
		
		saveConfig();
	}
	
	public static Configuration getMysql() {
		return mysql;
	}
	
	public static Configuration getReports() {
		return reports;
	}
	
	
	public void onReconnectMySQL() {
		BungeeCord.getInstance().getScheduler().schedule(this, new Runnable() {
			
			@Override
			public void run() {
				MySQL.onDisconect();
				MySQL.onConnect();
			}
		}, 5, 5, TimeUnit.MINUTES);
	}
	

	
	public static void saveMySQLConfig() {
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(mysql, new File(getDatafolder(), "mysql.yml"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		loadMySQLConfig();
	}
	
	public static void loadMySQLConfig() {
		File file = new File(datafolder, "mysql.yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			mysql = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveConfig() {
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, new File(getDatafolder(), "config.yml"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(chatfilter, new File(getDatafolder(), "chatfilter.yml"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(motd, new File(getDatafolder(), "motd.yml"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(automessage, new File(getDatafolder(), "automessage.yml"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		saveReports();
		reloadConfig();
	}
	
	public static void saveReports() {
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(reports, new File(getDatafolder(), "reports.yml"));
			System.out.println("Config gespeichert.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		reloadReports();
	}
	
	public static void reloadReports() {
		File file = new File(datafolder, "reports.yml");
		try {
			reports = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
			System.out.println("Config neu geladen.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void reloadConfig() {
		File file = new File(datafolder, "config.yml");
		try {
			configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		file = new File(datafolder, "chatfilter.yml");
		try {
			chatfilter = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		file = new File(datafolder, "motd.yml");
		try {
			motd = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		file = new File(datafolder, "automessage.yml");
		try {
			automessage = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
			AutoChat.reload = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}

	public static Plugin getInstance() {
		// TODO Auto-generated method stub
		return instance;
	}
}
