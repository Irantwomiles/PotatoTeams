package me.iran.potato.warps;

import me.iran.potato.PotatoTeams;
import me.iran.potato.factions.PlayerFaction;
import me.iran.potato.factions.PlayerFactionManager;
import me.iran.potato.spawn.SpawnProtection;
import me.iran.potato.util.CollectionsUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shayan on 6/27/2017.
 */
public class Warps{

    private File file = null;

    private SpawnProtection spawn = new SpawnProtection(PotatoTeams.getInstance());

    public void createWarp(Player player, String warp, Location loc) {

    	if(warp.equalsIgnoreCase("set") || warp.equalsIgnoreCase("list") || warp.equalsIgnoreCase("remove")) {
    	     player.sendMessage(ChatColor.RED + "You can't name your warps the following names: set, list, remove");
    		return;
    	}
    	
        file = new File(PotatoTeams.getInstance().getDataFolder() + "/PlayerInfo", player.getUniqueId().toString() + ".yml");

        if(file.exists()) {

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            if(!config.contains("warps." + warp)) {

                config.createSection("warps." + warp + ".x");
                config.createSection("warps." + warp + ".y");
                config.createSection("warps." + warp + ".z");
                config.createSection("warps." + warp + ".pitch");
                config.createSection("warps." + warp + ".yaw");
                config.createSection("warps." + warp + ".world");

                config.set("warps." + warp + ".x", loc.getX());
                config.set("warps." + warp + ".y", loc.getY());
                config.set("warps." + warp + ".z", loc.getZ());
                config.set("warps." + warp + ".pitch", loc.getPitch());
                config.set("warps." + warp + ".yaw", loc.getYaw());
                config.set("warps." + warp + ".world", loc.getWorld().getName());

                try {
                    config.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                player.sendMessage(ChatColor.GRAY + "Create warp " + warp);

            } else {
                player.sendMessage(ChatColor.RED + "You already have a warp by that name");
            }

        }

    }

    public void deleteWarp(Player player, String warp) {

        file = new File(PotatoTeams.getInstance().getDataFolder() + "/PlayerInfo", player.getUniqueId().toString() + ".yml");

        if(file.exists()) {

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            if(config.contains("warps." + warp)) {

                config.set("warps." + warp, null);

                try {
                    config.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                player.sendMessage(ChatColor.GRAY + "Deleted warp " + warp);

            } else {
                player.sendMessage(ChatColor.RED + "You don't have a warp by that name");
            }

        }

    }

    public void getWarpNames(Player player, String uuid) {

        file = new File(PotatoTeams.getInstance().getDataFolder() + "/PlayerInfo", uuid + ".yml");

        if(file.exists()) {

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            String msg = ChatColor.GRAY + "No warps set /go set | remove";

            if(config.contains("warps")) {

                msg = ChatColor.GRAY + "Warps: ";

                List<String> warps = new ArrayList<>();

                for(String s : config.getConfigurationSection("warps").getKeys(false)) {
                   warps.add(s);
                }

                for(int i = 0; i < warps.size(); i++) {
                    msg = msg + warps.get(i) + ", ";
                }

            }

            player.sendMessage(msg);

        }
    }

    public List<Location> getWarps(Player player) {

        file = new File(PotatoTeams.getInstance().getDataFolder() + "/PlayerInfo", player.getUniqueId().toString() + ".yml");

        List<Location> warps = new ArrayList<>();

        if(file.exists()) {

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            if (config.contains("warps")) {

                for (String warp : config.getConfigurationSection("warps").getKeys(false)) {

                    double x = config.getDouble("warps." + warp + ".x");
                    double y = config.getDouble("warps." + warp + ".y");
                    double z = config.getDouble("warps." + warp + ".z");

                    float pitch = config.getFloat("warps." + warp + ".pitch");
                    float yaw = config.getFloat("warps." + warp + ".yaw");

                    String world = config.getString("warps." + warp + ".world");

                    Location loc = new Location(Bukkit.getWorld(world),x, y, z);

                    loc.setPitch(pitch);
                    loc.setYaw(yaw);

                    warps.add(loc);
                }
            }
        }

        return warps;
    }

    public int warpCount(String uuid) {

        int count = 0;

        file = new File(PotatoTeams.getInstance().getDataFolder() + "/PlayerInfo", uuid + ".yml");

        if(file.exists()) {

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            if(config.contains("warps")) {

                for(String w : config.getConfigurationSection("warps").getKeys(false)) {
                    count++;
                }

            }

        }

        return count;
    }

    public void teleportToWarp(Player player, String warp) {

        if(spawn.isInsideSpawn(player.getLocation())) {
            player.sendMessage(ChatColor.RED + "Can't warp inside of spawn");
            return;
        }

        file = new File(PotatoTeams.getInstance().getDataFolder() + "/PlayerInfo", player.getUniqueId().toString() + ".yml");

        if(file.exists()) {

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);


            if(config.contains("warps." + warp)) {

            	double x = config.getDouble("warps." + warp + ".x");
            	double y = config.getDouble("warps." + warp + ".y");
            	double z = config.getDouble("warps." + warp + ".z");

                float pitch = config.getFloat("warps." + warp + ".pitch");
                float yaw = config.getFloat("warps." + warp + ".yaw");

                String world = config.getString("warps." + warp + ".world");

                Location loc = new Location(Bukkit.getWorld(world),x, y, z);

                loc.setPitch(pitch);
                loc.setYaw(yaw);

                for (Entity p : player.getNearbyEntities(10, 10, 10)) {

                    if (p instanceof Player) {

                        Player near = (Player) p;

                        if(PlayerFactionManager.getManager().playerIsInFaction(player)) {

                            PlayerFaction faction = PlayerFactionManager.getManager().getFactionByPlayer(player);

                            if(!faction.getMembers().contains(near.getUniqueId().toString())) {

                            	if(player.canSee(near)) {
                            		
                                CollectionsUtil.getWarp().put(player.getName(), 10);
                                CollectionsUtil.getWarpName().put(player.getName(), warp);

                                player.sendMessage(ChatColor.GRAY + "There are enemy players near you, please don't move or get damaged for 10 seconds!");

                                return;
                            	}
                            	
                            }

                        } else {
                        	
                        	if(player.canSee(near)) {
                        		
                        		CollectionsUtil.getWarp().put(player.getName(), 10);
                        		CollectionsUtil.getWarpName().put(player.getName(), warp);

                        		player.sendMessage(ChatColor.GRAY + "There are enemy players near you, please don't move or get damaged for 10 seconds!");

                        		return;
                        	}
                        }
                    }
                }

                player.teleport(loc);
                player.sendMessage(ChatColor.GRAY + "Teleported to warp");
            } else {
                player.sendMessage(ChatColor.RED + "Couldn't find a warp by the name of " + warp);
            }

        }
    }

    public void forceTp(Player player, String uuid, String warp) {
        file = new File(PotatoTeams.getInstance().getDataFolder() + "/PlayerInfo", uuid + ".yml");

        if(file.exists()) {

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            if(config.contains("warps." + warp)) {

                int x = config.getInt("warps." + warp + ".x");
                int y = config.getInt("warps." + warp + ".y");
                int z = config.getInt("warps." + warp + ".z");

                float pitch = config.getFloat("warps." + warp + ".pitch");
                float yaw = config.getFloat("warps." + warp + ".yaw");

                String world = config.getString("warps." + warp + ".world");

                Location loc = new Location(Bukkit.getWorld(world),x, y, z);

                loc.setPitch(pitch);
                loc.setYaw(yaw);

                player.teleport(loc);
            }

        }
    }

    public void forceDeleteWarp(Player player, String uuid, String warp) {

        file = new File(PotatoTeams.getInstance().getDataFolder() + "/PlayerInfo", uuid + ".yml");

        if(file.exists()) {

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            if(config.contains("warps." + warp)) {

                config.set("warps." + warp, null);

                try {
                    config.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                player.sendMessage(ChatColor.GRAY + "Deleted warp " + warp);

            } else {
                player.sendMessage(ChatColor.RED + "You don't have a warp by that name");
            }

        }

    }
    
}
