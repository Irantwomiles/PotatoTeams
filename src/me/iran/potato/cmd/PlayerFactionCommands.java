package me.iran.potato.cmd;

import me.iran.potato.PotatoTeams;
import me.iran.potato.factions.PlayerFaction;
import me.iran.potato.factions.PlayerFactionManager;
import me.iran.potato.util.CollectionsUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.UUID;

public class PlayerFactionCommands implements CommandExecutor {

	PotatoTeams plugin;
	
	public PlayerFactionCommands(PotatoTeams plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		
		String help = ChatColor.DARK_AQUA + "Captains\n"
					+ ChatColor.GRAY + "- /team sethq - Set team HQ at your location\n"
					+ ChatColor.GRAY + "- /team setrally - Set team Rally at your location\n"
					+ ChatColor.GRAY + "- /team setpassword <password> - Change your teams password\n"
					+ ChatColor.GRAY + "- /team promote <player> - Promote a player to Captain [Leader only]\n"
					+ ChatColor.GRAY + "- /team demote <player> - Demote a player to member [Leader only]\n"
					+ ChatColor.GRAY + "- /team ff - Toggle friendly fire\n"
					+ ChatColor.GRAY + "- /team kick <player> - Kick a player from your team\n"
					
					+ ChatColor.DARK_AQUA + "Everyone\n\n"
					
					+ ChatColor.GRAY + "- /team create <name> - Don't forget to change the Password\n"
					+ ChatColor.GRAY + "- /team join <team> <password> - Join a team\n"
					+ ChatColor.GRAY + "- /team leave - Leave your current team\n"
					+ ChatColor.GRAY + "- /team hq - Teleport to your teams HQ\n"
					+ ChatColor.GRAY + "- /team rally - Teleport to your teams rally\n"
					+ ChatColor.GRAY + "- /team info <team> - Get your teams info\n\n"
					+ ChatColor.GRAY + "- /team chat - Toggles team chat\n\n"
					+ ChatColor.DARK_AQUA + "Alias for /team [/t]";
		
		if(cmd.getName().equalsIgnoreCase("team")) {
			
			if(args.length < 1) {
				
				player.sendMessage(help);
				
				return true;
			}
			
			if(args[0].equalsIgnoreCase("create")) {
				
				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + "/team create <name>");
					return true;
				}
				
				PlayerFactionManager.getManager().createFaction(args[1], player);
			}
			
			if(args[0].equalsIgnoreCase("disband")) {
				
				PlayerFactionManager.getManager().disbandFaction(player);
			}
			
			if(args[0].equalsIgnoreCase("setpassword") || args[0].equalsIgnoreCase("setpass") || args[0].equalsIgnoreCase("pass")) {

				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + "/team setpassword <password>");
					return true;
				}
				
				PlayerFactionManager.getManager().setPassword(player, args[1]);
				
			}
			
			if(args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("j")) {
				
				if(args.length < 3) {
					PlayerFactionManager.getManager().joinOpenFaction(player, args[1]);
					return true;
				}
				
				PlayerFactionManager.getManager().joinClosedFaction(player, args[1], args[2]);
			}
			
			if(args[0].equalsIgnoreCase("leave") || args[0].equalsIgnoreCase("l")) {
				
				PlayerFactionManager.getManager().leaveTeam(player);
			}
			
			if(args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("i")) {
				
				if(args.length < 2) {
					PlayerFactionManager.getManager().factionInfo(player);
					return true;
				}
				
				Player target = Bukkit.getPlayer(args[1]);
				
				if(target == null) {
					PlayerFactionManager.getManager().factionInfoByName(player, args[1]);
				} else {
					PlayerFactionManager.getManager().factionInfoByPlayer(player, target);
				}
				
			}
			
			if(args[0].equalsIgnoreCase("open") || args[0].equalsIgnoreCase("o")) {
				
				PlayerFactionManager.getManager().openFaction(player);
				
			}

			if(args[0].equalsIgnoreCase("kick")) {

				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + "/team kick <player>");
					return true;
				}

				OfflinePlayer pl = Bukkit.getOfflinePlayer(args[1]);

				if(pl == null) {
					player.sendMessage(ChatColor.RED + "Couldn't find that player");
					return true;
				}

				PlayerFactionManager.getManager().kickPlayer(player, pl.getUniqueId().toString());

			}

			if(args[0].equalsIgnoreCase("setrally") || args[0].equalsIgnoreCase("sr")) {
				PlayerFactionManager.getManager().setRally(player, player.getLocation());
			}
			
			if(args[0].equalsIgnoreCase("sethq")) {
				PlayerFactionManager.getManager().setHq(player, player.getLocation());
			}
			
			if(args[0].equalsIgnoreCase("rally") || args[0].equalsIgnoreCase("r")) {
				
				teleportRally(player);
			}
			
			if(args[0].equalsIgnoreCase("hq")) {
				
				teleportHq(player);
			}
			
			if(args[0].equalsIgnoreCase("promote")) {
				
				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + "/team promote <player>");
					return true;
				}
				
				Player target = Bukkit.getPlayer(args[1]);
				
				if(target == null) {
					player.sendMessage(ChatColor.RED + "Could not find player");
					return true;
				}
				
				PlayerFactionManager.getManager().promotePlayer(player, target);
			}
			
			if(args[0].equalsIgnoreCase("demote")) {
				
				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + "/team promote <player>");
					return true;
				}
				
				Player target = Bukkit.getPlayer(args[1]);
				
				if(target == null) {
					player.sendMessage(ChatColor.RED + "Could not find player");
					return true;
				}
				
				PlayerFactionManager.getManager().demotePlayer(player, target);
			}
			
			if(args[0].equalsIgnoreCase("ff")) {
				
				PlayerFactionManager.getManager().toggleFriendyFire(player);
			}
			
			if(args[0].equalsIgnoreCase("chat")) {
				
				PlayerFaction faction = PlayerFactionManager.getManager().getFactionByPlayer(player);
				
				if(faction != null) {
					
					if(CollectionsUtil.getTeamChat().contains(player.getName())) {
						CollectionsUtil.getTeamChat().remove(player.getName());
						
						player.sendMessage(ChatColor.DARK_AQUA + "Now in public chat");
						
					} else {
						CollectionsUtil.getTeamChat().add(player.getName());
						
						player.sendMessage(ChatColor.DARK_AQUA + "Now in team chat");
					}
				}
			}
			
		}
		
		return true;
	}

	private void teleportHq(Player player) {
		
		PlayerFaction faction = PlayerFactionManager.getManager().getFactionByPlayer(player);
		
		if(faction == null) {
			player.sendMessage(ChatColor.RED + "You must be in a team to do that command!");
			return;
		}
		
		if(faction.getHq() == null) {
			player.sendMessage(ChatColor.RED + "Your team has not set a team HQ!");
			return;
		}
		
		if(CollectionsUtil.getCombat().containsKey(player.getName())) {
			player.sendMessage(ChatColor.RED + "Can't teleport while in combat (" + CollectionsUtil.getCombat().get(player.getName()) + " seconds)");
			return;
		}
		
		for (Entity p : player.getNearbyEntities(10, 10, 10)) {
			
			if (p instanceof Player) {

				Player near = (Player) p;

				if(!faction.getMembers().contains(near.getUniqueId().toString())) {
					
					CollectionsUtil.getTeleportHq().put(player.getName(), 10);
					
					player.sendMessage(ChatColor.GRAY + "There are enemy players near you, please don't move or get damaged for 10 seconds!");
					
					return;
				}
			}
		}
		
		player.teleport(faction.getHq());
		player.sendMessage(ChatColor.GREEN + "Teleported to Team HQ");
	}

	private void teleportRally(Player player) {
		
		PlayerFaction faction = PlayerFactionManager.getManager().getFactionByPlayer(player);
		
		if(faction == null) {
			player.sendMessage(ChatColor.RED + "You must be in a team to do that command!");
			return;
		}
		
		if(faction.getRally() == null) {
			player.sendMessage(ChatColor.RED + "Your team has not set a team rally!");
			return;
		}
		
		if(CollectionsUtil.getCombat().containsKey(player.getName())) {
			player.sendMessage(ChatColor.RED + "Can't teleport while in combat (" + CollectionsUtil.getCombat().get(player.getName()) + " seconds)");
			return;
		}
		
		for (Entity p : player.getNearbyEntities(10, 10, 10)) {
			
			if (p instanceof Player) {

				Player near = (Player) p;

				if(!faction.getMembers().contains(near.getUniqueId().toString())) {
					
					CollectionsUtil.getTeleportRally().put(player.getName(), 10);
					
					player.sendMessage(ChatColor.GRAY + "There are enemy players near you, please don't move or get damaged for 10 seconds!");
					
					return;
				}
			}
		}
		
		player.teleport(faction.getRally());
		player.sendMessage(ChatColor.GREEN + "Teleported to Team Rally");
	}
	
}
