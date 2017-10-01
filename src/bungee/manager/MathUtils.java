package bungee.manager;

public class MathUtils {

	public static boolean isDouble(String s) {
		try{
			Double.parseDouble(s);
			
			return true;
		}
		catch(NumberFormatException ex)
		{
			return false;
		}
	}
	
	public static boolean isInt(String s) {
		try{
			Integer.parseInt(s);
			
			return true;
		}
		catch(NumberFormatException ex)
		{
			return false;
		}
	}
}
