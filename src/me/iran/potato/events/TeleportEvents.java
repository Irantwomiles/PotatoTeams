package me.iran.potato.events;

import me.iran.potato.PotatoTeams;
import me.iran.potato.util.CollectionsUtil;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TeleportEvents implements Listener {

	PotatoTeams plugin;
	
	public TeleportEvents (PotatoTeams plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		
		Player player = event.getPlayer();
		
		if(CollectionsUtil.getTeleportHq().containsKey(player.getName())) {
			if(!(event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockZ() == event.getTo().getBlockZ())) {
				CollectionsUtil.getTeleportHq().remove(player.getName());
				player.sendMessage(ChatColor.RED + "Teleportation canceled!");
			}
		}
		
		if(CollectionsUtil.getTeleportRally().containsKey(player.getName())) {
			if(!(event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockZ() == event.getTo().getBlockZ())) {
				CollectionsUtil.getTeleportRally().remove(player.getName());
				player.sendMessage(ChatColor.RED + "Teleportation canceled!");
			}
		}
		
		if(CollectionsUtil.getSpawn().containsKey(player.getName())) {
			if(!(event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockZ() == event.getTo().getBlockZ())) {
				CollectionsUtil.getSpawn().remove(player.getName());
				player.sendMessage(ChatColor.RED + "Teleportation canceled!");
			}
		}

		if(CollectionsUtil.getWarp().containsKey(player.getName())) {
			if(!(event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockZ() == event.getTo().getBlockZ())) {
				CollectionsUtil.getWarp().remove(player.getName());

				try {
					CollectionsUtil.getWarpName().remove(player.getName());
				} catch(Exception e) {

				}

				player.sendMessage(ChatColor.RED + "Teleportation canceled!");
			}
		}
	}
	
	/*
	 * Avoid leaks
	 */
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		
		Player player = event.getPlayer();
		
		if(CollectionsUtil.getTeleportHq().containsKey(player.getName())) {
			CollectionsUtil.getTeleportHq().remove(player.getName());
		}
		
		if(CollectionsUtil.getTeleportRally().containsKey(player.getName())) {
			CollectionsUtil.getTeleportRally().remove(player.getName());
		}
		
		if(CollectionsUtil.getSpawn().containsKey(player.getName())) {
			CollectionsUtil.getSpawn().remove(player.getName());
		}

		if(CollectionsUtil.getWarpName().containsKey(player.getName())) {
			CollectionsUtil.getWarpName().remove(player.getName());
		}

		if(CollectionsUtil.getWarp().containsKey(player.getName())) {
			CollectionsUtil.getWarp().remove(player.getName());
		}

	}
}
