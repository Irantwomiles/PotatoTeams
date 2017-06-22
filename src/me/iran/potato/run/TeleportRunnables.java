package me.iran.potato.run;

import me.iran.potato.factions.PlayerFaction;
import me.iran.potato.factions.PlayerFactionManager;
import me.iran.potato.util.CollectionsUtil;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportRunnables extends BukkitRunnable {

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			
			
			//Team HQ cooldown
			if(CollectionsUtil.getTeleportHq().containsKey(p.getName())) {
				
				CollectionsUtil.getTeleportHq().put(p.getName(), CollectionsUtil.getTeleportHq().get(p.getName()) - 1);
				
				if(CollectionsUtil.getTeleportHq().get(p.getName()) <= 0) {
					
					CollectionsUtil.getTeleportHq().remove(p.getName());
					
					PlayerFaction faction = PlayerFactionManager.getManager().getFactionByPlayer(p);
					
					if(faction != null && faction.getHq() != null) {
						
						p.teleport(faction.getHq());
						p.sendMessage(ChatColor.DARK_AQUA + "Teleported to Team HQ");
					}
					
				}
				
			}
			
			//Team Rally cooldown
			if(CollectionsUtil.getTeleportRally().containsKey(p.getName())) {
				
				CollectionsUtil.getTeleportRally().put(p.getName(), CollectionsUtil.getTeleportRally().get(p.getName()) - 1);
				
				if(CollectionsUtil.getTeleportRally().get(p.getName()) <= 0) {
					
					CollectionsUtil.getTeleportRally().remove(p.getName());
					
					PlayerFaction faction = PlayerFactionManager.getManager().getFactionByPlayer(p);
					
					if(faction != null && faction.getRally() != null) {
						
						p.teleport(faction.getRally());
						p.sendMessage(ChatColor.DARK_AQUA + "Teleported to Team Rally");
					}
					
				}
				
			}
			
			//Combat tag cooldown
			if(CollectionsUtil.getCombat().containsKey(p.getName())) {
				
				CollectionsUtil.getCombat().put(p.getName(), CollectionsUtil.getCombat().get(p.getName()) - 1);
				
				if(CollectionsUtil.getCombat().get(p.getName()) <= 0) {
					
					CollectionsUtil.getCombat().remove(p.getName());
					
				}
				
			}
			
			//Spawn cooldown
			if(CollectionsUtil.getSpawn().containsKey(p.getName())) {
				
				CollectionsUtil.getSpawn().put(p.getName(), CollectionsUtil.getSpawn().get(p.getName()) - 1);
				
				if(CollectionsUtil.getSpawn().get(p.getName()) <= 0) {
					
					CollectionsUtil.getSpawn().remove(p.getName());
					p.sendMessage(ChatColor.GRAY + "Teleported Spawn");
					CollectionsUtil.getSafe().add(p.getName());
				}
				
			}
			
		}
		
	}
	
}

