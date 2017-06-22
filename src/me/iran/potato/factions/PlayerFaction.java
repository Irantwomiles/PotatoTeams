package me.iran.potato.factions;

import java.util.ArrayList;

import org.bukkit.Location;

public class PlayerFaction  {	

	private String name;
	private String leader;
	private String rallyWorld;
	private String hqWorld;
	private String pass;
	
	private Location hq;
	private Location rally;
	
	private ArrayList<String> members;
	private ArrayList<String> captains;
	
	private boolean open = false;
	private boolean ff = false;
	
	public PlayerFaction(String name, String leader) {
		
		this.leader = leader;
		this.name = name;
		
		members = new ArrayList<String>();
		captains = new ArrayList<String>();
		
		pass = "password123";
		
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public ArrayList<String> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<String> members) {
		this.members = members;
	}

	public ArrayList<String> getCaptains() {
		return captains;
	}

	public void setCaptains(ArrayList<String> captains) {
		this.captains = captains;
	}

	public String getRallyWorld() {
		return rallyWorld;
	}

	public void setRallyWorld(String rallyWorld) {
		this.rallyWorld = rallyWorld;
	}

	public Location getHq() {
		return hq;
	}

	public void setHq(Location hq) {
		this.hq = hq;
	}

	public Location getRally() {
		return rally;
	}

	public void setRally(Location rally) {
		this.rally = rally;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHqWorld() {
		return hqWorld;
	}

	public void setHqWorld(String hqWorld) {
		this.hqWorld = hqWorld;
	}

	public boolean isFf() {
		return ff;
	}

	public void setFf(boolean ff) {
		this.ff = ff;
	}

}
