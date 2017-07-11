package me.iran.potato.events;

import me.iran.potato.PotatoTeams;

import me.iran.potato.factions.PlayerFactionManager;
import me.iran.potato.util.CollectionsUtil;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DisconnectEvent implements Listener {

	PotatoTeams plugin;
	
	public DisconnectEvent (PotatoTeams plugin) {
		this.plugin = plugin;
	}

	private ConsoleCommandSender log = PotatoTeams.getInstance().getServer().getConsoleSender();

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		/*
		 * Quit while in combat
		 */

		event.setQuitMessage(null);

		Player player = event.getPlayer();

		if(CollectionsUtil.getCombat().containsKey(player.getName())) {

			player.setHealth(0.0);

			CollectionsUtil.getCombat().remove(player.getName());

		}


		if(PlayerFactionManager.getManager().playerIsInFaction(player)) {

			if(PlayerFactionManager.getManager().getOnlineCount(player) <= 1) {

				PlayerFactionManager.getManager().saveFaction(PlayerFactionManager.getManager().getFactionByPlayer(player));
			}

		}

	}


	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.setJoinMessage(null);
	}
}
