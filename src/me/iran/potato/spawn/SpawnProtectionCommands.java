package me.iran.potato.spawn;

import me.iran.defystaff.DefyStaff;
import me.iran.defystaff.commands.StaffMode;
import me.iran.potato.PotatoTeams;
import me.iran.potato.factions.PlayerFaction;
import me.iran.potato.factions.PlayerFactionManager;
import me.iran.potato.util.CollectionsUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class SpawnProtectionCommands implements CommandExecutor {

	PotatoTeams plugin;
	
	public SpawnProtectionCommands(PotatoTeams plugin) {
		this.plugin = plugin;
	}

	private SpawnProtection spawn = new SpawnProtection(PotatoTeams.getInstance());

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return true;
		}

		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("spawnloc1")) {
			
			if(player.hasPermission("potatoteams.safezone.setloc1")) {
				PotatoTeams.getInstance().getConfig().set("safezone.x1", player.getLocation().getBlockX());
				PotatoTeams.getInstance().getConfig().set("safezone.z1", player.getLocation().getBlockZ());
				PotatoTeams.getInstance().getConfig().set("safezone.world", player.getLocation().getWorld().getName());
			
				PotatoTeams.getInstance().saveConfig();
		
				player.sendMessage(ChatColor.GREEN + "Set location 1!");
			} 
			
		}
		
		if(cmd.getName().equalsIgnoreCase("spawnloc2")) {
			
			if(player.hasPermission("potatoteams.safezone.setloc2")) {
				PotatoTeams.getInstance().getConfig().set("safezone.x2", player.getLocation().getBlockX());
				PotatoTeams.getInstance().getConfig().set("safezone.z2", player.getLocation().getBlockZ());
				PotatoTeams.getInstance().getConfig().set("safezone.world", player.getLocation().getWorld().getName());
				
				PotatoTeams.getInstance().saveConfig();
			
				player.sendMessage(ChatColor.GREEN + "Set location 2!");
			} 
			
		}
		
		if(cmd.getName().equalsIgnoreCase("setspawn")) {
			
			if(player.hasPermission("potatoteams.spawn.setspawn")) {

				PotatoTeams.getInstance().getConfig().set("spawn.x", player.getLocation().getX());
				PotatoTeams.getInstance().getConfig().set("spawn.y", player.getLocation().getY());
				PotatoTeams.getInstance().getConfig().set("spawn.z", player.getLocation().getZ());
				
				PotatoTeams.getInstance().saveConfig();

				player.sendMessage(ChatColor.GREEN + "Set spawn!");
			} 
			
		}
		
		if(cmd.getName().equalsIgnoreCase("spawn")) {

			if(spawn.isInsideSpawn(player.getLocation()) && CollectionsUtil.getSafe().contains(player.getName())) {
				double x = PotatoTeams.getInstance().getConfig().getDouble("spawn.x");
				double y = PotatoTeams.getInstance().getConfig().getDouble("spawn.y");
				double z = PotatoTeams.getInstance().getConfig().getDouble("spawn.z");

				String world = PotatoTeams.getInstance().getConfig().getString("safezone.world");

				Location loc = new Location(Bukkit.getWorld(world), x, y, z);

				player.teleport(loc);
				player.sendMessage(ChatColor.GRAY + "Teleported to Spawn");
				return true;
			}

			teleportSpawn(player);
			
		}
		
		return true;
	}
	
	private void teleportSpawn(Player player) {

		double x = PotatoTeams.getInstance().getConfig().getInt("spawn.x");
		double y = PotatoTeams.getInstance().getConfig().getInt("spawn.y");
		double z = PotatoTeams.getInstance().getConfig().getInt("spawn.z");
		
		String world = PotatoTeams.getInstance().getConfig().getString("safezone.world");
		
		Location loc = new Location(Bukkit.getWorld(world), x, y, z);

		if(StaffMode.isStaff(player)) {
			
			if(!CollectionsUtil.getSafe().contains(player.getName())) {
				CollectionsUtil.getSafe().add(player.getName());
			}

			player.teleport(loc);

			player.sendMessage(ChatColor.GRAY + "Teleported to Spawn");
			return;
		}
		
		for (Entity p : player.getNearbyEntities(10, 10, 10)) {
			
			if (p instanceof Player) {

				Player near = (Player) p;

				if(PlayerFactionManager.getManager().playerIsInFaction(player)) {
					
					PlayerFaction faction = PlayerFactionManager.getManager().getFactionByPlayer(player);
					
					if(!faction.getMembers().contains(near.getUniqueId().toString())) {
						
						if(player.canSee(near)) {
							
							CollectionsUtil.getSpawn().put(player.getName(), 10);
							
							player.sendMessage(ChatColor.GRAY + "There are enemy players near you, please don't move or get damaged for 10 seconds!");
							
							return;
						
						} 

					}
					
				} else {
					
					if(player.canSee(near)) {
						
						CollectionsUtil.getSpawn().put(player.getName(), 10);
						
						player.sendMessage(ChatColor.GRAY + "There are enemy players near you, please don't move or get damaged for 10 seconds!");
						
						return;
						
					} 
		
				}
			}
		}

		if(!CollectionsUtil.getSafe().contains(player.getName())) {
			CollectionsUtil.getSafe().add(player.getName());
		}

		if(CollectionsUtil.getCombat().containsKey(player.getName())) {
			CollectionsUtil.getCombat().remove(player.getName());
		}
		
		player.teleport(loc);

		player.sendMessage(ChatColor.GRAY + "Teleported to Spawn");

	}


}
