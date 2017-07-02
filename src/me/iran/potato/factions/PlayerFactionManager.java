package me.iran.potato.factions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.iran.potato.PotatoTeams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PlayerFactionManager {
	
	private File playerfaction = null;
	private File file = null;
	
	private ConsoleCommandSender log = PotatoTeams.getInstance().getServer().getConsoleSender();
	
	public static ArrayList<PlayerFaction> factions = new ArrayList<>();
	
	private static PlayerFactionManager manager;
	
	public PlayerFactionManager() {}
	
	public static PlayerFactionManager getManager() {
		if(manager == null) {
			manager = new PlayerFactionManager();
		}
		
		return manager;
	}
	
	public void loadPlayerFactions() {
		
		//playerfaction.yml file in folder
		playerfaction = new File(PotatoTeams.getInstance().getDataFolder(), "playerfaction.yml");
		
		//Checking if file exists
		if(playerfaction.exists()) {
			
			//Loading it to YAML
			YamlConfiguration config = YamlConfiguration.loadConfiguration(playerfaction);
			
			List<String> names = config.getStringList("playerfaction");
			
			if(names.size() > 0) {
				
				for(int i = 0; i < names.size(); i++) {
					String n = names.get(i);
					
					file = new File(PotatoTeams.getInstance().getDataFolder() + "/PlayerFaction", n + ".yml");
					
					YamlConfiguration facConfig = YamlConfiguration.loadConfiguration(file);
					
					//Don't do deep search
					for(String name : facConfig.getConfigurationSection("playerfaction").getKeys(false)) {
						
						String facName = facConfig.getString("playerfaction." + name + ".name");
						
						String leader = facConfig.getString("playerfaction." + name + ".leader");
						
						String pass = facConfig.getString("playerfaction." + name + ".password");
						
						ArrayList<String> members = (ArrayList<String>) facConfig.getStringList("playerfaction." + name + ".members");
						
						boolean open = facConfig.getBoolean("playerfaction." + name + ".open");
						
						
						PlayerFaction faction = new PlayerFaction(facName, leader);
						
						faction.setName(facName);
						faction.setLeader(leader);
						faction.setMembers(members);
						faction.setOpen(open);
						faction.setPass(pass.toString());
						
						/**
						 * These are things that could not exist yet, so we will load last
						 */
						
						//Captains
						if(facConfig.contains("playerfaction." + name + ".captains")) {
							ArrayList<String> captains = (ArrayList<String>) facConfig.getStringList("playerfaction." + name + ".captains");
							faction.setCaptains(captains);
						}
						
						//Loc1
						if(facConfig.contains("playerfaction." + name + ".hq")) {
							
							int x = facConfig.getInt("playerfaction." + name + ".hq.x");
							int y = facConfig.getInt("playerfaction." + name + ".hq.y");
							int z = facConfig.getInt("playerfaction." + name + ".hq.z");
							
							faction.setHqWorld(facConfig.getString("playerfaction." + name + ".hq.world"));
							
							Location hq = new Location(Bukkit.getWorld(faction.getHqWorld()), x, y, z);
						
							faction.setHq(hq);
							
						}
						
						//Loc2
						if(facConfig.contains("playerfaction." + name + ".rally")) {
							
							int x = facConfig.getInt("playerfaction." + name + ".rally.x");
							int y = facConfig.getInt("playerfaction." + name + ".rally.y");
							int z = facConfig.getInt("playerfaction." + name + ".rally.z");
							
							faction.setRallyWorld(facConfig.getString("playerfaction." + name + ".rally.world"));
							
							Location rally = new Location(Bukkit.getWorld(faction.getRallyWorld()), x, y, z);
						
							faction.setRally(rally);
							
						}
						
						factions.add(faction);
					}
				}
				
				log.sendMessage(ChatColor.DARK_GREEN + "----------------------");
				log.sendMessage(ChatColor.GREEN + "[PotatoTeams] Teams loaded correctly!");
				log.sendMessage(ChatColor.DARK_GREEN + "----------------------");
				
			}
			
			
		} else {
			
			log.sendMessage(ChatColor.RED + "[Potato] Seems like I couldn't find the playerfaction.yml file!");
			
		}
		
	}
	
	public void savePlayerFactions() {
		
		playerfaction = new File(PotatoTeams.getInstance().getDataFolder(), "playerfaction.yml");
		
		if(playerfaction.exists()) {
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(playerfaction);
			
			List<String> facNames = config.getStringList("playerfaction");
			
			for(String faction : facNames) {
				
				file = new File(PotatoTeams.getInstance().getDataFolder() + "/PlayerFaction", faction + ".yml");
				
				file.delete();
				
			}
			
			facNames.clear();
			
			for(PlayerFaction fac : factions) {
				facNames.add(fac.getName());
			}
			
			config.set("playerfaction", facNames);
			
			try {
				config.save(playerfaction);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			for(String name : facNames) {
				
				PlayerFaction faction = getFactionByName(name);
				
				if(faction != null) {
					
					file = new File(PotatoTeams.getInstance().getDataFolder() + "/PlayerFaction", name + ".yml");
					
					YamlConfiguration facConfig = YamlConfiguration.loadConfiguration(file);
					
					facConfig.createSection("playerfaction." + name + ".name");
					facConfig.createSection("playerfaction." + name + ".leader");
					facConfig.createSection("playerfaction." + name + ".members");
					facConfig.createSection("playerfaction." + name + ".open");
					facConfig.createSection("playerfaction." + name + ".password");
					
					if(faction.getCaptains().size() > 0) {
						facConfig.createSection("playerfaction." + name + ".captains");
						facConfig.set("playerfaction." + name + ".captains", faction.getCaptains());
					}
					
					if(faction.getHq() != null) {
						facConfig.createSection("playerfaction." + name + ".hq.x");
						facConfig.createSection("playerfaction." + name + ".hq.y");
						facConfig.createSection("playerfaction." + name + ".hq.z");
						facConfig.createSection("playerfaction." + name + ".hq.world");
						
						
						facConfig.set("playerfaction." + name + ".hq.x", faction.getHq().getBlockX());
						facConfig.set("playerfaction." + name + ".hq.y", faction.getHq().getBlockY());
						facConfig.set("playerfaction." + name + ".hq.z", faction.getHq().getBlockZ());
						facConfig.set("playerfaction." + name + ".hq.world", faction.getHqWorld());
					}
					
					if(faction.getRally() != null) {
						facConfig.createSection("playerfaction." + name + ".rally.x");
						facConfig.createSection("playerfaction." + name + ".rally.y");
						facConfig.createSection("playerfaction." + name + ".rally.z");
						facConfig.createSection("playerfaction." + name + ".rally.world");
						
						
						facConfig.set("playerfaction." + name + ".rally.x", faction.getRally().getBlockX());
						facConfig.set("playerfaction." + name + ".rally.y", faction.getRally().getBlockY());
						facConfig.set("playerfaction." + name + ".rally.z", faction.getRally().getBlockZ());
						facConfig.set("playerfaction." + name + ".rally.world", faction.getRallyWorld());
					}
					
					
					facConfig.set("playerfaction." + name + ".name", faction.getName());
					facConfig.set("playerfaction." + name + ".leader", faction.getLeader());
					facConfig.set("playerfaction." + name + ".members", faction.getMembers());
					facConfig.set("playerfaction." + name + ".open", faction.isOpen());
					facConfig.set("playerfaction." + name + ".password", faction.getPass());
					
					try {
						facConfig.save(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					log.sendMessage(ChatColor.DARK_GREEN + "----------------------");
					log.sendMessage(ChatColor.GREEN + "[PotatoTeams] Teams saved correctly!");
					log.sendMessage(ChatColor.DARK_GREEN + "----------------------");
				}
				
			}
			
		}
		
	}

	public void saveFaction(PlayerFaction faction) {

		playerfaction = new File(PotatoTeams.getInstance().getDataFolder(), "playerfaction.yml");


		if (playerfaction.exists()) {

			YamlConfiguration listConfig = YamlConfiguration.loadConfiguration(playerfaction);

			List<String> list = listConfig.getStringList("playerfaction");

			if (!list.contains(faction.getName())) {
				list.add(faction.getName());
			}

			listConfig.set("playerfaction", list);

			file = new File(PotatoTeams.getInstance().getDataFolder() + "/PlayerFaction", faction.getName() + ".yml");

			if (!file.exists()) {

				file = new File(PotatoTeams.getInstance().getDataFolder() + "/PlayerFaction", faction.getName() + ".yml");

				YamlConfiguration facConfig = YamlConfiguration.loadConfiguration(file);

				facConfig.createSection("playerfaction." + faction.getName() + ".name");
				facConfig.createSection("playerfaction." + faction.getName() + ".leader");
				facConfig.createSection("playerfaction." + faction.getName() + ".members");
				facConfig.createSection("playerfaction." + faction.getName() + ".open");
				facConfig.createSection("playerfaction." + faction.getName() + ".password");

				if (faction.getCaptains().size() > 0) {
					facConfig.createSection("playerfaction." + faction.getName() + ".captains");
					facConfig.set("playerfaction." + faction.getName() + ".captains", faction.getCaptains());
				}

				if (faction.getHq() != null) {
					facConfig.createSection("playerfaction." + faction.getName() + ".hq.x");
					facConfig.createSection("playerfaction." + faction.getName() + ".hq.y");
					facConfig.createSection("playerfaction." + faction.getName() + ".hq.z");
					facConfig.createSection("playerfaction." + faction.getName() + ".hq.world");


					facConfig.set("playerfaction." + faction.getName() + ".hq.x", faction.getHq().getBlockX());
					facConfig.set("playerfaction." + faction.getName() + ".hq.y", faction.getHq().getBlockY());
					facConfig.set("playerfaction." + faction.getName() + ".hq.z", faction.getHq().getBlockZ());
					facConfig.set("playerfaction." + faction.getName() + ".hq.world", faction.getHqWorld());
				}

				if (faction.getRally() != null) {
					facConfig.createSection("playerfaction." + faction.getName() + ".rally.x");
					facConfig.createSection("playerfaction." + faction.getName() + ".rally.y");
					facConfig.createSection("playerfaction." + faction.getName() + ".rally.z");
					facConfig.createSection("playerfaction." + faction.getName() + ".rally.world");


					facConfig.set("playerfaction." + faction.getName() + ".rally.x", faction.getRally().getBlockX());
					facConfig.set("playerfaction." + faction.getName() + ".rally.y", faction.getRally().getBlockY());
					facConfig.set("playerfaction." + faction.getName() + ".rally.z", faction.getRally().getBlockZ());
					facConfig.set("playerfaction." + faction.getName() + ".rally.world", faction.getRallyWorld());
				}


				facConfig.set("playerfaction." + faction.getName() + ".name", faction.getName());
				facConfig.set("playerfaction." + faction.getName() + ".leader", faction.getLeader());
				facConfig.set("playerfaction." + faction.getName() + ".members", faction.getMembers());
				facConfig.set("playerfaction." + faction.getName() + ".open", faction.isOpen());
				facConfig.set("playerfaction." + faction.getName() + ".password", faction.getPass());

				try {
					facConfig.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else {

				YamlConfiguration facConfig = YamlConfiguration.loadConfiguration(file);

				if (facConfig.contains("playerfaction." + faction.getName() + ".captains")) {

					if (faction.getCaptains().size() > 0) {
						facConfig.set("playerfaction." + faction.getName() + ".captains", faction.getCaptains());
					} else {
						facConfig.set("playerfaction." + faction.getName() + ".captains", null);
					}

				} else if (!facConfig.contains("playerfaction." + faction.getName() + ".captains")) {

					if (faction.getCaptains().size() > 0) {

						facConfig.createSection("playerfaction." + faction.getName() + ".captains");

						facConfig.set("playerfaction." + faction.getName() + ".captains", faction.getCaptains());
					}

				}

				if (facConfig.contains("playerfaction." + faction.getName() + ".hq")) {

					if (faction.getHq() != null) {
						facConfig.set("playerfaction." + faction.getName() + ".hq.x", faction.getHq().getBlockX());
						facConfig.set("playerfaction." + faction.getName() + ".hq.y", faction.getHq().getBlockY());
						facConfig.set("playerfaction." + faction.getName() + ".hq.z", faction.getHq().getBlockZ());
						facConfig.set("playerfaction." + faction.getName() + ".hq.world", faction.getHqWorld());
					} else {
						facConfig.set("playerfaction." + faction.getName() + ".hq", null);
					}

				} else if (!facConfig.contains("playerfaction." + faction.getName() + ".hq")) {

					if (faction.getHq() != null) {
						facConfig.createSection("playerfaction." + faction.getName() + ".hq.x");
						facConfig.createSection("playerfaction." + faction.getName() + ".hq.y");
						facConfig.createSection("playerfaction." + faction.getName() + ".hq.z");
						facConfig.createSection("playerfaction." + faction.getName() + ".hq.world");

						facConfig.set("playerfaction." + faction.getName() + ".hq.x", faction.getHq().getBlockX());
						facConfig.set("playerfaction." + faction.getName() + ".hq.y", faction.getHq().getBlockY());
						facConfig.set("playerfaction." + faction.getName() + ".hq.z", faction.getHq().getBlockZ());
						facConfig.set("playerfaction." + faction.getName() + ".hq.world", faction.getHqWorld());
					}

				}

				if (facConfig.contains("playerfaction." + faction.getName() + ".rally")) {

					if (faction.getRally() != null) {
						facConfig.set("playerfaction." + faction.getName() + ".rally.x", faction.getRally().getBlockX());
						facConfig.set("playerfaction." + faction.getName() + ".rally.y", faction.getRally().getBlockY());
						facConfig.set("playerfaction." + faction.getName() + ".rally.z", faction.getRally().getBlockZ());
						facConfig.set("playerfaction." + faction.getName() + ".rally.world", faction.getRallyWorld());
					} else {
						facConfig.set("playerfaction." + faction.getName() + ".rally", null);
					}

				} else if (!facConfig.contains("playerfaction." + faction.getName() + ".rally")) {

					if (faction.getRally() != null) {
						facConfig.createSection("playerfaction." + faction.getName() + ".rally.x");
						facConfig.createSection("playerfaction." + faction.getName() + ".rally.y");
						facConfig.createSection("playerfaction." + faction.getName() + ".rally.z");
						facConfig.createSection("playerfaction." + faction.getName() + ".rally.world");

						facConfig.set("playerfaction." + faction.getName() + ".rally.x", faction.getRally().getBlockX());
						facConfig.set("playerfaction." + faction.getName() + ".rally.y", faction.getRally().getBlockY());
						facConfig.set("playerfaction." + faction.getName() + ".rally.z", faction.getRally().getBlockZ());
						facConfig.set("playerfaction." + faction.getName() + ".rally.world", faction.getRallyWorld());
					}

				}

				facConfig.set("playerfaction." + faction.getName() + ".name", faction.getName());
				facConfig.set("playerfaction." + faction.getName() + ".leader", faction.getLeader());
				facConfig.set("playerfaction." + faction.getName() + ".members", faction.getMembers());
				facConfig.set("playerfaction." + faction.getName() + ".open", faction.isOpen());
				facConfig.set("playerfaction." + faction.getName() + ".password", faction.getPass());


				try {
					facConfig.save(file);

					System.out.println("saved facConfig");
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			try {
				listConfig.save(playerfaction);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public void removePlayerFaction(PlayerFaction faction) {

		playerfaction = new File(PotatoTeams.getInstance().getDataFolder(), "playerfaction.yml");

		if(playerfaction.exists()) {

			YamlConfiguration config = YamlConfiguration.loadConfiguration(playerfaction);

			if(config.getStringList("playerfaction").contains(faction.getName())) {

				config.getStringList("playerfaction").remove(faction.getName());

				file = new File(PotatoTeams.getInstance().getDataFolder() + "/PlayerFaction", faction.getName() + ".yml");

				if(file.exists()) {
					file.delete();
				}

				try {
					config.save(file);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

	public void createFaction(String name, Player player) {
		
		if(getFactionByName(name) != null) {
			player.sendMessage(ChatColor.RED + "A team by the name of '" + ChatColor.YELLOW + name + ChatColor.RED + "' already exists.");
			return;
		}
		
		String[] notAllowed = { ",", ";", "!", "@", "#", "$",
				"%", "^", "&", "*", "(", ")", "+", "=", "`",
				"~", ".", "<", ">", "/", "\"", ":", ";", "{",
				"}", "?" };
		
		for(String no : notAllowed) {
			if (name.contains(no)) {
				player.sendMessage(ChatColor.RED + "You can't use those characters in your team name!");
				return;
			}
		}
		
		if(playerIsInFaction(player)) {
			player.sendMessage(ChatColor.RED + "Already in a Team.");
			return;
		}
		
		if(name.length() >= 12) {
			player.sendMessage(ChatColor.RED + "Your team name can't be longer than 12 letters");
			return;
		}
		
		PlayerFaction faction = new PlayerFaction(name, player.getUniqueId().toString());
		
		faction.getMembers().add(player.getUniqueId().toString());
		
		factions.add(faction);
		
		player.sendMessage(ChatColor.DARK_AQUA + "Team created");
	}
	
	public boolean playerIsInFaction(Player player) {

		for(PlayerFaction faction : factions) {
			if(faction.getMembers().contains(player.getUniqueId().toString())) {
				return true;
			}
		}
		
		return false;
	}

	public PlayerFaction getFactionByName(String name) {
		
		for(PlayerFaction faction : factions) {
			if(faction.getName().equalsIgnoreCase(name)) {
				return faction;
			}
		}
		
		return null;
	}
	
	public PlayerFaction getFactionByPlayer(Player player) {
		
		for(PlayerFaction faction : factions) {
			if(faction.getMembers().contains(player.getUniqueId().toString())) {
				return faction;
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public void joinOpenFaction(Player player, String name) {
		
		PlayerFaction faction = getFactionByName(name);
		
		if(faction == null) {
			player.sendMessage(ChatColor.RED + "That team does not exist.");
			return;
		}
		
		if(faction.getMembers().size() >= 30) {
			player.sendMessage(ChatColor.RED + "That team is full.");
			return;
		}
		
		if(playerIsInFaction(player)) {
			player.sendMessage(ChatColor.RED + "You are already in a team.");
			return;
		}
		
		if(faction.isOpen()) {
			faction.getMembers().add(player.getUniqueId().toString());

			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				if(faction.getMembers().contains(p.getUniqueId().toString())) {
					p.sendMessage(ChatColor.DARK_AQUA + player.getName() + " has joined the team!");
				}
			}
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void toggleFriendyFire(Player player) {
		
		PlayerFaction faction = getFactionByPlayer(player);
		
		if(faction == null) {
			player.sendMessage(ChatColor.RED + "You are not in a team");
			return;
		}

		if(faction.isFf()) {
			
			faction.setFf(false);
			
			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				if(faction.getMembers().contains(p.getUniqueId().toString())) {
					p.sendMessage(ChatColor.RED + player.getName() + " has turned off friendy fire!");
				}
			}
			
		} else {
			
			faction.setFf(true);
			
			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				if(faction.getMembers().contains(p.getUniqueId().toString())) {
					p.sendMessage(ChatColor.GREEN + player.getName() + " has turned on friendy fire!");
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void joinClosedFaction(Player player, String name, String pass) {
		
		PlayerFaction faction = getFactionByName(name);
		
		if(faction == null) {
			player.sendMessage(ChatColor.RED + "That team does not exist.");
			return;
		}
		
		if(!faction.getPass().equals(pass)) {
			player.sendMessage(ChatColor.RED + "Incorrect password.");
			return;
		}
		
		if(faction.getMembers().size() >= 30) {
			player.sendMessage(ChatColor.RED + "That team is full.");
			return;
		}
		
		if(!faction.isOpen() && faction.getPass().equals(pass)) {
			faction.getMembers().add(player.getUniqueId().toString());

			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				if(faction.getMembers().contains(p.getUniqueId().toString())) {
					p.sendMessage(ChatColor.DARK_AQUA + player.getName() + " has joined the team!");
				}
			}
		}

	}
	
	@SuppressWarnings("deprecation")
	public void leaveTeam(Player player) {
		
		PlayerFaction faction = getFactionByPlayer(player);
		
		if(faction == null) {
			player.sendMessage(ChatColor.RED + "You are not in a team! /team create <name>");
			return;
		}
		
		if(faction.getLeader().equals(player.getUniqueId().toString())) {
			player.sendMessage(ChatColor.RED + "Please make someone leader before leaving your team! /team leader <player>");
			return;
		}
		
		if(faction.getCaptains().contains(player.getUniqueId().toString())) {
			
			faction.getCaptains().remove(player.getUniqueId().toString());
		}
		
		faction.getMembers().remove(player.getUniqueId().toString());
		
		player.sendMessage(ChatColor.RED + "You have left the team!");
		
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			if(faction.getMembers().contains(p.getUniqueId().toString())) {
				p.sendMessage(ChatColor.RED + player.getName() + " has left the team!");
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void setRally(Player player, Location loc) {
	
		PlayerFaction faction = getFactionByPlayer(player);
		
		if(faction == null) {
			player.sendMessage(ChatColor.RED + "You are not in a team! /team create <name>");
			return;
		}
		
		if(faction.getCaptains().contains(player.getUniqueId().toString()) || faction.getLeader().equals(player.getUniqueId().toString())) {

			int x = Math.abs(player.getLocation().getBlockX());
			int z = Math.abs(player.getLocation().getBlockZ());

			if(x < 512 && z < 512) {
				player.sendMessage(ChatColor.RED + "You need to be 512 blocks away from spawn before setting team rally");
				return;
			}

			faction.setRally(loc);
			faction.setRallyWorld(loc.getWorld().getName());
			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				if(faction.getMembers().contains(p.getUniqueId().toString())) {
					p.sendMessage(ChatColor.DARK_AQUA + player.getName() + " has updated the team rally! /team rally");
				}
			}

		} else {
			player.sendMessage(ChatColor.RED + "Only captains and the leader can do this command");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void setHq(Player player, Location loc) {
	
		PlayerFaction faction = getFactionByPlayer(player);
		
		if(faction == null) {
			player.sendMessage(ChatColor.RED + "You are not in a team! /team create <name>");
			return;
		}
		
		if(faction.getCaptains().contains(player.getUniqueId().toString()) || faction.getLeader().equals(player.getUniqueId().toString())) {

			int x = Math.abs(player.getLocation().getBlockX());
			int z = Math.abs(player.getLocation().getBlockZ());

			if(x < 512 && z < 512) {
				player.sendMessage(ChatColor.RED + "You need to be 512 blocks away from spawn before setting team hq");
				return;
			}

			faction.setHq(loc);
			faction.setHqWorld(loc.getWorld().getName());
			
			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				if(faction.getMembers().contains(p.getUniqueId().toString())) {
					p.sendMessage(ChatColor.DARK_AQUA + player.getName() + " has updated the team hq! /team hq");
				}
			}
			
			return;
		} else {
			player.sendMessage(ChatColor.RED + "Only captains and the leader can do this command");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void kickPlayer(Player player, String target) {
	
		PlayerFaction faction = getFactionByPlayer(player);
		
		if(faction == null) {
			player.sendMessage(ChatColor.RED + "You are not in a team! /team create <name>");
			return;
		}
		
		//Captains can't kick other captains
		if(faction.getCaptains().contains(player.getUniqueId().toString()) && faction.getCaptains().contains(target)) {
			player.sendMessage(ChatColor.RED + "You can't kick other captains, only leaders can!");
			return;
		} 
		
		//Can't kick leader
		if(faction.getCaptains().contains(player.getUniqueId().toString()) && faction.getLeader().equals(target)) {
			player.sendMessage(ChatColor.RED + "You can't kick the leader!");
			return;
		} 
		
		//Captain kicking player
		if(faction.getCaptains().contains(player.getUniqueId().toString()) &&
				
				!faction.getLeader().equals(target) &&
				
				!faction.getCaptains().contains(target)) {
			
			if(faction.getMembers().contains(target)) {
				faction.getMembers().remove(target);
				
				for(Player p : Bukkit.getServer().getOnlinePlayers()) {
					if(faction.getMembers().contains(p.getUniqueId().toString())) {
						p.sendMessage(ChatColor.RED + Bukkit.getOfflinePlayer(UUID.fromString(target)).getName() + " has been kicked from the team by " + player.getName());
					}
				}
				return;
			}
		}
		
		//Leader kicking a captain
		if(faction.getLeader().equals(player.getUniqueId().toString()) && faction.getCaptains().contains(target)) {
			
			if(faction.getMembers().contains(target)) {
				
				faction.getMembers().remove(target);
				faction.getCaptains().remove(target);
				
				for(Player p : Bukkit.getServer().getOnlinePlayers()) {
					if(faction.getMembers().contains(p.getUniqueId().toString())) {
						p.sendMessage(ChatColor.RED + Bukkit.getOfflinePlayer(UUID.fromString(target)).getName() + " has been kicked from the team by " + player.getName());
					}
				}
				
				return;
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void setPassword(Player player, String pass) {
	
		PlayerFaction faction = getFactionByPlayer(player);
		
		if(faction == null) {
			player.sendMessage(ChatColor.RED + "You are not in a team! /team create <name>");
			return;
		}
		
		if(faction.getCaptains().contains(player.getUniqueId().toString()) || faction.getLeader().equals(player.getUniqueId().toString())) {
			
			faction.setPass(pass);
			
			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				if(faction.getMembers().contains(p.getUniqueId().toString())) {
					p.sendMessage(ChatColor.DARK_AQUA + player.getName() + " has updated the password! /team info");
				}
			}
			
			return;
		}

	}

	@SuppressWarnings("deprecation")
	public void openFaction(Player player) {
	
		PlayerFaction faction = getFactionByPlayer(player);
		
		if(faction == null) {
			player.sendMessage(ChatColor.RED + "You are not in a team! /team create <name>");
			return;
		}
		
		if(faction.getCaptains().contains(player.getUniqueId().toString()) || faction.getLeader().equals(player.getUniqueId().toString())) {
			
			if(!faction.isOpen()) {
				faction.setOpen(true);
				
				for(Player p : Bukkit.getServer().getOnlinePlayers()) {
					if(faction.getMembers().contains(p.getUniqueId().toString())) {
						p.sendMessage(ChatColor.DARK_AQUA + player.getName() + " has opened the team for the public");
					}
				}
			} else {
				faction.setOpen(false);
				
				for(Player p : Bukkit.getServer().getOnlinePlayers()) {
					if(faction.getMembers().contains(p.getUniqueId().toString())) {
						p.sendMessage(ChatColor.RED + player.getName() + " has closed the team");
					}
				}
			}

		} else {
			player.sendMessage(ChatColor.RED + "Must be a captain to do this command");
		}
	}

	public int getOnlineCount(Player player) {
		PlayerFaction faction = getFactionByPlayer(player);

		int online = 0;

		if(faction == null) {
			return 0;
		}

		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			if(faction.getMembers().contains(p.getUniqueId().toString())) {
				online++;
			}
		}
		return online;

	}

	public void factionInfo(Player player) {
	
		PlayerFaction faction = getFactionByPlayer(player);
		
		if(faction == null) {
			player.sendMessage(ChatColor.RED + "You are not in a team! /team create <name>");
			return;
		}

		String offlineSuffix = ChatColor.GRAY + " - Offline ";
		
		String hq = ChatColor.RED.toString() + "Not Set";
		
		if(faction.getHq() != null) {
			hq = ChatColor.GREEN.toString() + "Set";
		}
		
		String rally = ChatColor.RED.toString() + "Not Set";
		
		if(faction.getRally() != null) {
			rally = ChatColor.GREEN.toString() + "Set";
		}

		String ff = ChatColor.RED.toString() + "Off";

		if(faction.isFf() == true) {
			rally = ChatColor.GREEN.toString() + "On";
		}
		
		String msg = " " + " \n" +
					 ChatColor.GRAY + "***" + ChatColor.DARK_AQUA + faction.getName() + ChatColor.GRAY + "***\n" +
				     ChatColor.GRAY + "Password: " + ChatColor.RED + faction.getPass() + "\n" +
				     ChatColor.GRAY + "Headquarters: " + hq + "\n" +
				     ChatColor.GRAY + "Rally point: " + rally + "\n" +
				     ChatColor.GRAY + "Friendly Fire: " + ff + "\n" +
					 ChatColor.GRAY + "Members (" + getOnlineCount(player) + "/" + faction.getMembers().size() + "):";

		player.sendMessage(msg);

		for(String p : faction.getMembers()) {
			
			OfflinePlayer pl = Bukkit.getOfflinePlayer(UUID.fromString(p));

			String onlineSuffix = "";

			String name = ChatColor.GRAY + pl.getName();
			
			if(faction.getCaptains().contains(p) || faction.getLeader().equals(p)) {
				
				name = ChatColor.DARK_AQUA + pl.getName();
				
				if(pl.isOnline()) {

					onlineSuffix = ChatColor.GRAY + " - " + ((int) (((Player) pl).getHealth() / 20 * 100)) + "%";

					player.sendMessage("  " + name + onlineSuffix);
				} else {
					player.sendMessage("  " + name + offlineSuffix);
				}
				
			} else {
				if(pl.isOnline()) {

					onlineSuffix = ChatColor.GRAY + " - " + ((int) (((Player) pl).getHealth() / 20 * 100)) + "%";

					player.sendMessage("  " + name + onlineSuffix);
				} else {
					player.sendMessage("  " + name + offlineSuffix);
				}
			}
		}


		player.sendMessage("");
	}

	public void factionInfoByPlayer(Player player, Player target) {

		PlayerFaction faction = getFactionByPlayer(target);
		
		if(faction == null) {
			player.sendMessage(ChatColor.RED + target.getName() + " is not in a team!");
			return;
		}

		String offlineSuffix = ChatColor.GRAY + " - Offline ";

		String hq = ChatColor.RED.toString() + "Not Set";

		if(faction.getHq() != null) {
			hq = ChatColor.GREEN.toString() + "Set";
		}

		String rally = ChatColor.RED.toString() + "Not Set";

		if(faction.getRally() != null) {
			rally = ChatColor.GREEN.toString() + "Set";
		}

		String msg = " " + " \n" +
				ChatColor.GRAY + "***" + ChatColor.DARK_AQUA + faction.getName() + ChatColor.GRAY + "***" + "\n";
		if(faction.getMembers().contains(player.getUniqueId().toString()))
		{
			msg = msg + ChatColor.GRAY + "Headquarters: " + hq + "\n" +
					ChatColor.GRAY + "Rally point: " + rally + "\n";
		}
		msg = msg + ChatColor.GRAY + "Members (" + getOnlineCount(player) + "/" + faction.getMembers().size() + "):";
		
		player.sendMessage(msg);
		
		for(String p : faction.getMembers()) {


			OfflinePlayer pl = Bukkit.getOfflinePlayer(UUID.fromString(p));

			String onlineSuffix = "";

			String name = ChatColor.GRAY + pl.getName();
			
			if(faction.getCaptains().contains(p) || faction.getLeader().equals(p)) {
				
				name = ChatColor.DARK_AQUA + pl.getName();
				
				if(pl.isOnline()) {

					onlineSuffix = ChatColor.GRAY + " - " + ((int) (((Player) pl).getHealth() / 20 * 100)) + "%";

					player.sendMessage("  " +name + onlineSuffix);
				} else {
					player.sendMessage("  " +name + offlineSuffix);
				}
				
			} else {
				if(pl.isOnline()) {
					onlineSuffix = ChatColor.GRAY + " - " + ((int) (((Player) pl).getHealth() / 20 * 100)) + "%";
					player.sendMessage("  " + name + onlineSuffix);
				} else {
					player.sendMessage("  " + name + offlineSuffix);
				}
			}
		}

		player.sendMessage(" ");
		
	}
	
	public void factionInfoByName(Player player, String n) {

		PlayerFaction faction = getFactionByName(n);

		if(faction == null) {
			player.sendMessage(ChatColor.RED + n + " could not be found");
			return;
		}

		String offlineSuffix = ChatColor.GRAY + " - Offline ";

		String hq = ChatColor.RED.toString() + "Not Set";

		if(faction.getHq() != null) {
			hq = ChatColor.GREEN.toString() + "Set";
		}

		String rally = ChatColor.RED.toString() + "Not Set";

		if(faction.getRally() != null) {
			rally = ChatColor.GREEN.toString() + "Set";
		}

		String msg = " " + " \n" +
				ChatColor.GRAY + "***" + ChatColor.DARK_AQUA + faction.getName() + ChatColor.GRAY + "***" + "\n";
		if(faction.getMembers().contains(player.getUniqueId().toString()))
		{
			msg = msg + ChatColor.GRAY + "Headquarters: " + hq + "\n" +
					ChatColor.GRAY + "Rally point: " + rally + "\n";
		}
			msg = msg + ChatColor.GRAY + "Members (" + getOnlineCount(player) + "/" + faction.getMembers().size() + "):";

		player.sendMessage(msg);

		for(String p : faction.getMembers()) {


			OfflinePlayer pl = Bukkit.getOfflinePlayer(UUID.fromString(p));

			String onlineSuffix = "";

			String name = ChatColor.GRAY + pl.getName();

			if(faction.getCaptains().contains(p) || faction.getLeader().equals(p)) {

				name = ChatColor.DARK_AQUA + pl.getName();

				if(pl.isOnline()) {
					onlineSuffix = ChatColor.GRAY + " - " + ((int) (((Player) pl).getHealth() / 20 * 100)) + "%";
					player.sendMessage("  " +name + onlineSuffix);
				} else {
					player.sendMessage("  " +name + offlineSuffix);
				}

			} else {
				if(pl.isOnline()) {
					onlineSuffix = ChatColor.GRAY + " - " + ((int) (((Player) pl).getHealth() / 20 * 100)) + "%";
					player.sendMessage("  " + name + onlineSuffix);
				} else {
					player.sendMessage("  " + name + offlineSuffix);
				}
			}
		}

		player.sendMessage(" ");

	}

	@SuppressWarnings("deprecation")
	public void disbandFaction(Player player) {
		
		PlayerFaction faction = getFactionByPlayer(player);
		
		if(faction == null) {
			player.sendMessage(ChatColor.RED + "You are not in a team");
			return;
		}
		
		if(faction.getLeader().equals(player.getUniqueId().toString())) {
			
			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				if(faction.getMembers().contains(p.getUniqueId().toString())) {
					p.sendMessage(ChatColor.RED + player.getName() + " has disbanded the team");
				}
			}

			removePlayerFaction(faction);

			faction.getMembers().clear();
			faction.getCaptains().clear();
			factions.remove(faction);

		} else {
			player.sendMessage(ChatColor.RED + "Only team leaders can do that command!");
		}
		
		
	}
	
	@SuppressWarnings("deprecation")
	public void promotePlayer(Player player, Player target) {
		
		PlayerFaction faction = getFactionByPlayer(player);
		
		if(faction == null) {
			player.sendMessage(ChatColor.RED + "You are not in a team");
			return;
		}
		
		if(faction.getLeader().equals(player.getUniqueId().toString())) {
			
			if(faction.getMembers().contains(target.getUniqueId().toString())) {
				
				if(!faction.getCaptains().contains(target.getUniqueId().toString())) {
					faction.getCaptains().add(target.getUniqueId().toString());
					
					for(Player p : Bukkit.getServer().getOnlinePlayers()) {
						if(faction.getMembers().contains(p.getUniqueId().toString())) {
							p.sendMessage(ChatColor.DARK_AQUA + player.getName() + " has made " + target.getName() + " captain");
						}
					}
					
				}
				
			}
			
		} else {
			player.sendMessage(ChatColor.RED + "Only team leaders can do that command!");
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void demotePlayer(Player player, Player target) {
		
		PlayerFaction faction = getFactionByPlayer(player);
		
		if(faction == null) {
			player.sendMessage(ChatColor.RED + "You are not in a team");
			return;
		}
		
		if(faction.getLeader().equals(player.getUniqueId().toString())) {
			
			if(faction.getMembers().contains(target.getUniqueId().toString())) {
				
				if(faction.getCaptains().contains(target.getUniqueId().toString())) {
					faction.getCaptains().remove(target.getUniqueId().toString());
					
					for(Player p : Bukkit.getServer().getOnlinePlayers()) {
						if(faction.getMembers().contains(p.getUniqueId().toString())) {
							p.sendMessage(ChatColor.RED + player.getName() + " has demoted " + target.getName());
						}
					}
					
				}
				
			}
			
		} else {
			player.sendMessage(ChatColor.RED + "Only team leaders can do that command!");
		}
		
	}
}
