package me.iran.potato.events;

import me.iran.potato.PotatoTeams;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class DisconnectEvent implements Listener {

	PotatoTeams plugin;
	
	public DisconnectEvent (PotatoTeams plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		
		/*
		 * Quit while in combat
		 */
		
		Player player = event.getPlayer();
		
	}

}
