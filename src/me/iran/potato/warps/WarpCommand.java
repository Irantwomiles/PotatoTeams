package me.iran.potato.warps;

import me.iran.potato.PotatoTeams;
import org.bukkit.ChatColor;
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

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("go")) {

            if(args.length < 1) {
                warp.getWarps(player);
                return true;
            }

            if(args.length == 1) {
                warp.teleportToWarp(player, args[0]);
                return true;
            }

            if(args[0].equalsIgnoreCase("set")) {

                if(args.length < 2) {
                    player.sendMessage(ChatColor.RED + "/go set <name>");
                    return true;
                }

                if(player.hasPermission("mcsoup.pro")) {
                    warp.createWarp(player, args[1], player.getLocation());
                } else if(player.hasPermission("mcsoup.mvp")) {

                    if(warp.warpCount(player) < 25) {
                        warp.createWarp(player, args[1], player.getLocation());
                    } else {
                        player.sendMessage(ChatColor.RED + "You have reached your limit of warps.");
                    }

                } else if(player.hasPermission("mcsoup.vip")) {

                    if(warp.warpCount(player) < 15) {
                        warp.createWarp(player, args[1], player.getLocation());
                    } else {
                        player.sendMessage(ChatColor.RED + "You have reached your limit of warps.");
                    }

                } else {

                    if(warp.warpCount(player) < 5) {
                        warp.createWarp(player, args[1], player.getLocation());
                    } else {
                        player.sendMessage(ChatColor.RED + "You have reached your limit of warps.");
                    }

                }

            }

        }

        return true;
    }
}

