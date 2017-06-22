package me.iran.potato.spawn;

import me.iran.potato.PotatoTeams;
import me.iran.potato.util.CollectionsUtil;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class SpawnProtection implements Listener {

	PotatoTeams plugin;
	
	public SpawnProtection(PotatoTeams plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		
		Player player = event.getPlayer();
		
		if(CollectionsUtil.getSafe().contains(player.getName()) && !isInsideSpawn(player.getLocation())) {
			player.sendMessage(ChatColor.GRAY + "You no longer have spawn protection");
			CollectionsUtil.getSafe().remove(player.getName());
		}
		
	}
	
	@EventHandler
	public void onDamager(EntityDamageByEntityEvent event) {
		
		if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			/*
			 * Hitting someone without spawn protection
			 */
			
			Player player = (Player) event.getEntity();
			
			Player damager = (Player) event.getDamager();
			
			if(!CollectionsUtil.getSafe().contains(player.getName()) && CollectionsUtil.getSafe().contains(damager.getName())) {
				
				CollectionsUtil.getSafe().remove(damager.getName());
				
				damager.sendMessage(ChatColor.GRAY + "You no longer have spawn protection");
				return;
			}
			
			if(CollectionsUtil.getSafe().contains(player.getName()) && CollectionsUtil.getSafe().contains(damager.getName())) {
				event.setCancelled(true);
			}
			

			if(CollectionsUtil.getSafe().contains(player.getName()) && !CollectionsUtil.getSafe().contains(damager.getName())) {
				event.setCancelled(true);
			}
		}
		
	}
	
	public boolean isInsideSpawn(Location loc) {

		int x1 = PotatoTeams.getInstance().getConfig().getInt("safezone.x1");
		int z1 = PotatoTeams.getInstance().getConfig().getInt("safezone.z1");
		
		int x2 = PotatoTeams.getInstance().getConfig().getInt("safezone.x2");
		int z2 = PotatoTeams.getInstance().getConfig().getInt("safezone.z2");
		
		String world = PotatoTeams.getInstance().getConfig().getString("safezone.world");
		
		Location loc1 = new Location(Bukkit.getWorld(world), x1, 0, z1);
		Location loc2 = new Location(Bukkit.getWorld(world), x2, 0, z2);

		int xMax = Math.max(loc1.getBlockX(), loc2.getBlockX());
		int zMax = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

		int xMin = Math.min(loc1.getBlockX(), loc2.getBlockX());
		int zMin = Math.min(loc1.getBlockZ(), loc2.getBlockZ());

		if ((loc.getBlockX() >= xMin) && (loc.getBlockX() <= xMax)) {
			if ((loc.getBlockZ() >= zMin) && (loc.getBlockZ() <= zMax)) {
				return true;
			}
		}

		return false;
	}
	
}
