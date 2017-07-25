package me.iran.potato.warps;

import me.iran.potato.PotatoTeams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Shayan on 6/27/2017.
 */
public class WarpCommand implements CommandExecutor {

    PotatoTeams plugin;

    public WarpCommand(PotatoTeams plugin) {
        this.plugin = plugin;
    }

    Warps warp = new Warps();

    @SuppressWarnings("deprecation")
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("go")) {

            if(args.length < 1) {
                player.sendMessage(ChatColor.GRAY + "/go set <name>");
                player.sendMessage(ChatColor.GRAY + "/go remove <name>");
                player.sendMessage(ChatColor.GRAY + "/go list");
                return true;
            }

            if(args[0].equalsIgnoreCase("list")) {
                warp.getWarpNames(player, player.getUniqueId().toString());
                return true;
            }

            if(args[0].equalsIgnoreCase("set")) {

                if(args.length < 2) {
                    player.sendMessage(ChatColor.RED + "/go set <name>");
                    return true;
                }

                if(player.hasPermission("potatoteams.pro")) {

                    if(warp.warpCount(player.getUniqueId().toString()) < 25) {
                        warp.createWarp(player, args[1], player.getLocation());
                    } else {
                        player.sendMessage(ChatColor.RED + "You have reached your limit of warps.");
                    }

                } else if(player.hasPermission("potatoteams.mvp")) {

                    if(warp.warpCount(player.getUniqueId().toString()) < 15) {
                        warp.createWarp(player, args[1], player.getLocation());
                    } else {
                        player.sendMessage(ChatColor.RED + "You have reached your limit of warps.");
                    }

                } else if(player.hasPermission("potatoteams.vip")) {

                    if(warp.warpCount(player.getUniqueId().toString()) < 10) {
                        warp.createWarp(player, args[1], player.getLocation());
                    } else {
                        player.sendMessage(ChatColor.RED + "You have reached your limit of warps.");
                    }

                } else {

                    if(warp.warpCount(player.getUniqueId().toString()) < 5) {
                        warp.createWarp(player, args[1], player.getLocation());
                    } else {
                        player.sendMessage(ChatColor.RED + "You have reached your limit of warps.");
                    }

                }

            } else if(args[0].equalsIgnoreCase("remove")) {

                if(args.length < 2) {
                    player.sendMessage(ChatColor.RED + "/go remove <name>");
                    return true;
                }

                warp.deleteWarp(player, args[1]);
            } else {
                
                try {
                    warp.teleportToWarp(player, args[0]);
                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + "/go <name>");
                }
            
            }

        }
        
        if(cmd.getName().equalsIgnoreCase("ago")) {
        	
        	if(!player.hasPermission("potatoteams.staff.admin")) {
        		player.sendMessage(ChatColor.RED + ":)");
        		return true;
        	}
        	
            if(args.length < 1) {
                player.sendMessage(ChatColor.GRAY + "/ago tp <player> <warp>");
                player.sendMessage(ChatColor.GRAY + "/ago remove <player> <name>");
                player.sendMessage(ChatColor.GRAY + "/ago list <player>");
                return true;
            }

            try {
                
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                
                if(target == null) {
                	player.sendMessage(ChatColor.RED + "Couldn't find that player");
                	return true;
                }
                
                
                if(args[0].equalsIgnoreCase("list")) {
                	
    				if (args.length < 2) {
    					player.sendMessage(ChatColor.GRAY + "/ago list <player>");
    					return true;
    				}
                	
                    warp.getWarpNames(player, target.getUniqueId().toString());
                    return true;
                    
                } else if(args[0].equalsIgnoreCase("tp")) {

    				if (args.length < 3) {
    					 player.sendMessage(ChatColor.GRAY + "/ago tp <player> <warp>");
    					return true;
    				}

    				warp.forceTp(player, target.getUniqueId().toString(), args[2]);
    				
                } else if(args[0].equalsIgnoreCase("remove")) {

                	if (args.length < 3) {
    					 player.sendMessage(ChatColor.GRAY + "/ago remove <player> <warp>");
    					return true;
    				}

                    warp.forceDeleteWarp(player, target.getUniqueId().toString(), args[2]);
                    
                }
            } catch (Exception e) {
                player.sendMessage(ChatColor.GRAY + "/ago tp <player> <warp>");
                player.sendMessage(ChatColor.GRAY + "/ago remove <player> <name>");
                player.sendMessage(ChatColor.GRAY + "/ago list <player>");
            }

        	
        }

        return true;
    }
}

