package me.iran.potato.util;

import java.util.ArrayList;
import java.util.HashMap;

public class CollectionsUtil {

	private static HashMap<String, Integer> teleportRally = new HashMap<>();
	
	private static HashMap<String, Integer> teleportHq = new HashMap<>();
	
	private static HashMap<String, Integer> combat = new HashMap<>();
	
	private static HashMap<String, Integer> spawn = new HashMap<>();
	
	private static ArrayList<String> teamChat = new ArrayList<>();
	
	private static ArrayList<String> safe = new ArrayList<>();
	
	public static HashMap<String, Integer> getTeleportRally() {
		return teleportRally;
	}

	public static HashMap<String, Integer> getTeleportHq() {
		return teleportHq;
	}
	
	public static HashMap<String, Integer> getCombat() {
		return combat;
	}

	public static ArrayList<String> getTeamChat() {
		return teamChat;
	}

	public static ArrayList<String> getSafe() {
		return safe;
	}

	public static HashMap<String, Integer> getSpawn() {
		return spawn;
	}

}
