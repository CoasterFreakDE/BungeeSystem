package bungee.auth;

import java.util.HashMap;

public class AuthManager 
{

	private HashMap<String, String> ips;

	public AuthManager() {
		ips = new HashMap<String, String>();
		
		
	}

	public HashMap<String, String> getIps() {
		return ips;
	}
	
}
