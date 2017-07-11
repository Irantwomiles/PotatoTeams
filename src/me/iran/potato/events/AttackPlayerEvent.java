package me.iran.potato.events;

import me.iran.potato.PotatoTeams;
import me.iran.potato.factions.PlayerFaction;
import me.iran.potato.factions.PlayerFactionManager;
import me.iran.potato.util.CollectionsUtil;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;

import java.util.Collection;

public class AttackPlayerEvent implements Listener {

	PotatoTeams plugin;
	
	public AttackPlayerEvent (PotatoTeams plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onAttack(EntityDamageByEntityEvent event) {
		
		if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			
			/*
			 * Players attacking each other with ff on/off
			 */
			
			Player player = (Player) event.getEntity();
			Player damager = (Player) event.getDamager();
			
			PlayerFaction faction = PlayerFactionManager.getManager().getFactionByPlayer(damager);

			if(faction == null) {
				CollectionsUtil.getCombat().put(player.getName(), 20);
				CollectionsUtil.getCombat().put(damager.getName(), 20);
			}

			if (faction != null) {

				if (faction.getMembers().contains(player.getUniqueId().toString())) {
					if (!faction.isFf()) {
						event.setCancelled(true);
					}
				} else {
					CollectionsUtil.getCombat().put(player.getName(), 20);
					CollectionsUtil.getCombat().put(damager.getName(), 20);
				}
			}
			
			/*
			 * Player hit while teleporting to HQ/Rally
			 */
			
			if(CollectionsUtil.getTeleportHq().containsKey(player.getName())) {
				CollectionsUtil.getTeleportHq().remove(player.getName());
				
				player.sendMessage(ChatColor.RED + "Teleportation cancelled!");
			}
			
			if(CollectionsUtil.getTeleportRally().containsKey(player.getName())) {
				CollectionsUtil.getTeleportRally().remove(player.getName());
				
				player.sendMessage(ChatColor.RED + "Teleportation cancelled!");
			}
			
			if(CollectionsUtil.getSpawn().containsKey(player.getName())) {
				CollectionsUtil.getSpawn().remove(player.getName());
				
				player.sendMessage(ChatColor.RED + "Teleportation cancelled!");
			}


		}

	}

	@EventHandler
	public void onSplash(PotionSplashEvent event) {

		for(Entity entity : event.getAffectedEntities()) {

			if(entity instanceof  Player && event.getEntity().getShooter() instanceof Player) {

				Player player = (Player) entity;

				Player shooter = (Player) event.getEntity().getShooter();

				if(!player.getName().equalsIgnoreCase(shooter.getName())) {
					if(CollectionsUtil.getSafe().contains(player.getName())) {
						event.getAffectedEntities().remove(entity);
					}
				}
			}

		}

	}

}
