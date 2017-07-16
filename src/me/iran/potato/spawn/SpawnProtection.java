package me.iran.potato.spawn;

import java.io.File;
import java.util.List;

import javax.print.attribute.standard.Finishings;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.iran.potato.PotatoTeams;
import me.iran.potato.util.CollectionsUtil;
import net.md_5.bungee.api.ChatColor;

public class SpawnProtection implements Listener {

	PotatoTeams plugin;

	private File file = null;

	public SpawnProtection(PotatoTeams plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {

		Player player = event.getPlayer();

		if(!player.hasPlayedBefore()) {

			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(PotatoTeams.getInstance(), new Runnable() {
				@Override
				public void run() {
					int x = PotatoTeams.getInstance().getConfig().getInt("spawn.x");
					int y = PotatoTeams.getInstance().getConfig().getInt("spawn.y");
					int z = PotatoTeams.getInstance().getConfig().getInt("spawn.z");

					String world = PotatoTeams.getInstance().getConfig().getString("safezone.world");

					Location loc = new Location(Bukkit.getWorld(world), x, y, z);

					player.teleport(loc);
				}
			}, 1L);
			
			CollectionsUtil.getSafe().add(player.getName());

		}

		if(containsPlayer(player)) {
			if(isInsideSpawn(player.getLocation())) {
				removeFromFile(player);
			}
		}

	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();

		if(player.getBedSpawnLocation() == null) {

			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(PotatoTeams.getInstance(), new Runnable() {
				@Override
				public void run() {
					int x = PotatoTeams.getInstance().getConfig().getInt("spawn.x");
					int y = PotatoTeams.getInstance().getConfig().getInt("spawn.y");
					int z = PotatoTeams.getInstance().getConfig().getInt("spawn.z");

					String world = PotatoTeams.getInstance().getConfig().getString("safezone.world");

					Location loc = new Location(Bukkit.getWorld(world), x, y, z);

					player.teleport(loc);
				}
			}, 1L);

			if(!CollectionsUtil.getSafe().contains(player.getName())) {
				CollectionsUtil.getSafe().add(player.getName());
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		
		Player player = event.getPlayer();
		
		if(CollectionsUtil.getSafe().contains(player.getName()) && !isInsideSpawn(player.getLocation())) {
			player.sendMessage(ChatColor.GRAY + "You no longer have spawn protection");
			CollectionsUtil.getSafe().remove(player.getName());
		}
		
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {

		Player player = event.getPlayer();

		if(CollectionsUtil.getSafe().contains(player.getName()) && isInsideSpawn(player.getLocation())) {
			addToFile(player);
			CollectionsUtil.getSafe().remove(player.getName());
		}

	}

	private void addToFile(Player player) {

		file = new File(PotatoTeams.getInstance().getDataFolder(), "spawnprotection.yml");

		if(file.exists()) {

			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

			List<String> list = config.getStringList("spawnprotection");

			if(!list.contains(player.getUniqueId().toString()) && CollectionsUtil.getSafe().contains(player.getName())) {

				list.add(player.getUniqueId().toString());

				config.set("spawnprotection", list);

				try {
					config.save(file);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
	}

	private void removeFromFile(Player player) {

		file = new File(PotatoTeams.getInstance().getDataFolder(), "spawnprotection.yml");

		if(file.exists()) {

			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

			List<String> list = config.getStringList("spawnprotection");

			if(list.contains(player.getUniqueId().toString())) {

				list.remove(player.getUniqueId().toString());

				config.set("spawnprotection", list);

				CollectionsUtil.getSafe().add(player.getName());

				try {
					config.save(file);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
	}

	private boolean containsPlayer(Player player) {

		file = new File(PotatoTeams.getInstance().getDataFolder(), "spawnprotection.yml");

		if(file.exists()) {

			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

			List<String> list = config.getStringList("spawnprotection");

			if(list.contains(player.getUniqueId().toString())) {
				return true;
			} else {
				return false;
			}

		}

		return false;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDamager(EntityDamageByEntityEvent event) {
		
		if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			/*
			 * Hitting someone without spawn protection
			 */
			
			Player player = (Player) event.getEntity();
			
			Player damager = (Player) event.getDamager();
			
			if(!CollectionsUtil.getSafe().contains(player.getName()) && CollectionsUtil.getSafe().contains(damager.getName())) {
				
				CollectionsUtil.getSafe().remove(damager.getName());
				
				damager.sendMessage(ChatColor.GRAY + "You no longer have spawn protection");
				return;
			}

		}
		
		if(event.getDamager() instanceof Snowball) {

			Snowball ball = (Snowball) event.getDamager();
			
			Player damager = (Player) ball.getShooter();
			
			if(event.getEntity() instanceof Player) {
				
				Player player = (Player) event.getEntity();
				
				if(CollectionsUtil.getSafe().contains(player.getName())) {
					event.setCancelled(true);
				}
				
				if(!CollectionsUtil.getSafe().contains(player.getName()) && CollectionsUtil.getSafe().contains(damager.getName())) {
					CollectionsUtil.getSafe().remove(damager.getName());
					player.sendMessage(ChatColor.GRAY + "You no longer have spawn protection");
				}
				
			}
			
		}
		
		if(event.getDamager() instanceof Arrow) {

			Arrow arrow = (Arrow) event.getDamager();
			
			if(arrow.getShooter() instanceof Player) {
				
				Player damager = (Player) arrow.getShooter();
				
				if(event.getEntity() instanceof Player) {
					
					Player player = (Player) event.getEntity();
					
					if(CollectionsUtil.getSafe().contains(player.getName())) {
						event.setCancelled(true);
					}
					
					if(!CollectionsUtil.getSafe().contains(player.getName()) && CollectionsUtil.getSafe().contains(damager.getName())) {
						CollectionsUtil.getSafe().remove(damager.getName());
						damager.sendMessage(ChatColor.GRAY + "You no longer have spawn protection");
					}
					
				}
				
			}
			
		}
		
		if(event.getDamager() instanceof FishHook) {

			FishHook hook = (FishHook) event.getDamager();
			
			if(hook.getShooter() instanceof Player) {
				
				Player damager = (Player) hook.getShooter();
				
				if(event.getEntity() instanceof Player) {
					
					Player player = (Player) event.getEntity();
					
					if(CollectionsUtil.getSafe().contains(player.getName())) {
						event.setCancelled(true);
					}
					
					if(!CollectionsUtil.getSafe().contains(player.getName()) && CollectionsUtil.getSafe().contains(damager.getName())) {
						CollectionsUtil.getSafe().remove(damager.getName());
						damager.sendMessage(ChatColor.GRAY + "You no longer have spawn protection");
					}
					
				}
				
			}
			
		}
		
		
	}
	
	public boolean isInsideSpawn(Location loc) {

		int x1 = PotatoTeams.getInstance().getConfig().getInt("safezone.x1");
		int z1 = PotatoTeams.getInstance().getConfig().getInt("safezone.z1");
		
		int x2 = PotatoTeams.getInstance().getConfig().getInt("safezone.x2");
		int z2 = PotatoTeams.getInstance().getConfig().getInt("safezone.z2");
		
		String world = PotatoTeams.getInstance().getConfig().getString("safezone.world");
		
		Location loc1 = new Location(Bukkit.getWorld(world), x1, 0, z1);
		Location loc2 = new Location(Bukkit.getWorld(world), x2, 0, z2);

		int xMax = Math.max(loc1.getBlockX(), loc2.getBlockX());
		int zMax = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

		int xMin = Math.min(loc1.getBlockX(), loc2.getBlockX());
		int zMin = Math.min(loc1.getBlockZ(), loc2.getBlockZ());

		if ((loc.getBlockX() >= xMin) && (loc.getBlockX() <= xMax)) {
			if ((loc.getBlockZ() >= zMin) && (loc.getBlockZ() <= zMax)) {
				return true;
			}
		}

		return false;
	}
	
}
