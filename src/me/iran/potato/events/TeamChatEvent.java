package me.iran.potato.events;

import me.iran.potato.PotatoTeams;
import me.iran.potato.factions.PlayerFaction;
import me.iran.potato.factions.PlayerFactionManager;
import me.iran.potato.util.CollectionsUtil;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class TeamChatEvent implements Listener {

	PotatoTeams plugin;
	
	public TeamChatEvent (PotatoTeams plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		
		Player player = event.getPlayer();
		
		PlayerFaction faction = PlayerFactionManager.getManager().getFactionByPlayer(player);
		
		if(faction != null) {
			
			if(CollectionsUtil.getTeamChat().contains(player.getName())) {
				
				event.setCancelled(true);

				String msg = ChatColor.WHITE + "<" + ChatColor.GRAY + player.getName() + ChatColor.WHITE + ">" + ChatColor.GRAY + "[" + faction.getName() + "] " + ChatColor.WHITE + event.getMessage();

				if(faction.getCaptains().contains(player.getUniqueId().toString()) || faction.getLeader().equalsIgnoreCase(player.getUniqueId().toString())) {
					msg = ChatColor.WHITE + "<" + ChatColor.DARK_AQUA + player.getName() + ChatColor.WHITE + ">" + ChatColor.GRAY + "[" + faction.getName() + "] " + ChatColor.WHITE + event.getMessage();
				}

				for(Player p : Bukkit.getServer().getOnlinePlayers()) {

					if(faction.getMembers().contains(p.getUniqueId().toString())) {
						p.sendMessage(msg);
					}
				}
			}
		}
	}
	
}
