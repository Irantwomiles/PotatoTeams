package me.iran.potato;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.iran.potato.bottlexp.BottleXP;
import me.iran.potato.cmd.PlayerFactionCommands;
import me.iran.potato.economy.SaleManager;
import me.iran.potato.economy.balance.Balance;
import me.iran.potato.economy.balance.cmd.BalanceCommand;
import me.iran.potato.economy.balance.cmd.DepositCommand;
import me.iran.potato.economy.balance.cmd.WithdrawCommand;
import me.iran.potato.economy.cmd.SaleCommands;
import me.iran.potato.events.AttackPlayerEvent;
import me.iran.potato.events.DisconnectEvent;
import me.iran.potato.events.TeamChatEvent;
import me.iran.potato.events.TeleportEvents;
import me.iran.potato.factions.PlayerFactionManager;
import me.iran.potato.run.TeleportRunnables;
import me.iran.potato.salvage.Salvaging;
import me.iran.potato.spawn.SpawnProtection;
import me.iran.potato.spawn.SpawnProtectionCommands;
import me.iran.potato.warps.WarpCommand;

public class PotatoTeams extends JavaPlugin implements Listener {

	private File file = null;
	
	private ConsoleCommandSender log = this.getServer().getConsoleSender();
	
	private TeleportRunnables run = new TeleportRunnables();

	//private SaveRunnable saveRunnable = new SaveRunnable();

	private static PotatoTeams instance;
	
	public static PotatoTeams getInstance() {
		return instance;
	}
	
	public void onEnable() {

		log.sendMessage(ChatColor.AQUA + "-------------------------");
		log.sendMessage(ChatColor.DARK_AQUA + "PotatoTeams Plugin");
		log.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Plugin by: Irantwomiles");
		log.sendMessage(ChatColor.AQUA + "-------------------------");

		instance = this;

		getConfig().options().copyDefaults(true);

		saveConfig();

		/**
		 * playerfaction.yml file
		 */
		
		file = new File(this.getDataFolder(), "playerfaction.yml");
		
		if(!file.exists()) {
			
			file = new File(this.getDataFolder(), "playerfaction.yml");
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			config.createSection("playerfaction");
			
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			log.sendMessage(ChatColor.GREEN + "[Potato] Generated playerfaction.yml file");
			
		}
		
		/**
		 * PlayerFaction folder
		 */
		
		file = new File(this.getDataFolder() + "/PlayerFaction", "DELETE.yml");
		
		if(!file.exists()) {
			file = new File(this.getDataFolder() + "/PlayerFaction", "DELETE.yml");
			
			YamlConfiguration delete = YamlConfiguration.loadConfiguration(file);
			
			delete.createSection("Delete this file");
			
			try {
				delete.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			log.sendMessage(ChatColor.GREEN + "[Potato] Generated /PlayerFaction folder");
		}
		
		/**
		 * PlayerInfo folder
		 */
		
		file = new File(this.getDataFolder() + "/PlayerInfo", "DELETE.yml");
		
		if(!file.exists()) {
			file = new File(this.getDataFolder() + "/PlayerInfo", "DELETE.yml");
			
			YamlConfiguration delete = YamlConfiguration.loadConfiguration(file);
			
			delete.createSection("Delete this file");
			
			try {
				delete.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			log.sendMessage(ChatColor.GREEN + "[Potato] Generated /PlayerInfo folder");
		}
		
		
		/**
		 * economy.yml file
		 */
		
		file = new File(this.getDataFolder(), "economy.yml");
		
		if(!file.exists()) {
			
			file = new File(this.getDataFolder(), "economy.yml");
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			config.createSection("economy.id");
			
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			log.sendMessage(ChatColor.GREEN + "[Potato] Generated economy.yml file");
			
		}

		/**
		 * spawnprotection.yml file
		 */

		file = new File(this.getDataFolder(), "spawnprotection.yml");

		if(!file.exists()) {

			file = new File(this.getDataFolder(), "spawnprotection.yml");

			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

			config.createSection("spawnprotection");

			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}

			log.sendMessage(ChatColor.GREEN + "[Potato] Generated spawnprotection.yml file");

		}
		
		/**
		 * Commands
		 */
		
		getCommand("team").setExecutor(new PlayerFactionCommands(this));
		getCommand("spawnloc1").setExecutor(new SpawnProtectionCommands(this));
		getCommand("spawnloc2").setExecutor(new SpawnProtectionCommands(this));
		getCommand("setspawn").setExecutor(new SpawnProtectionCommands(this));
		getCommand("spawn").setExecutor(new SpawnProtectionCommands(this));
		getCommand("sell").setExecutor(new SaleCommands(this));
		getCommand("buy").setExecutor(new SaleCommands(this));
		getCommand("price").setExecutor(new SaleCommands(this));
		getCommand("balance").setExecutor(new BalanceCommand(this));
		getCommand("deposit").setExecutor(new DepositCommand(this));
		getCommand("withdraw").setExecutor(new WithdrawCommand(this));
		getCommand("go").setExecutor(new WarpCommand(this));
		getCommand("ago").setExecutor(new WarpCommand(this));
		//getCommand("bottle").setExecutor(new BottleXP(this));
		
		/**
		 * Events
		 */
		
		Bukkit.getPluginManager().registerEvents(new AttackPlayerEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new TeleportEvents(this), this);
		Bukkit.getPluginManager().registerEvents(new TeamChatEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new DisconnectEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new SpawnProtection(this), this);
		Bukkit.getPluginManager().registerEvents(new Balance(this), this);
		Bukkit.getPluginManager().registerEvents(new Salvaging(this), this);
		Bukkit.getPluginManager().registerEvents(new BottleXP(this), this);
		Bukkit.getPluginManager().registerEvents(this, this);
		/**
		 * Things needed to load
		 */
		
		PlayerFactionManager.getManager().loadPlayerFactions();
		SaleManager.getManager().loadSales();
		
		/**
		 * Runnables
		 */
		
		run.runTaskTimer(this, 20, 20);
		//saveRunnable.runTaskTimer(this, 20, 20);

		getConfig().options().copyDefaults(true);
		saveConfig();
		
	}
	
	public void onDisable() {
		
		PlayerFactionManager.getManager().savePlayerFactions();

		SaleManager.getManager().saveSales();
		
	}
	
	@EventHandler
	public void onAttack(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if(event.getAction() == null) {
			return;
		}
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(event.getClickedBlock().getType() == Material.ENDER_CHEST) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "Ender Chests are disabled");
			}
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		
		Player player = event.getPlayer();
		
		if(player.getLocation().getWorld().getName().equalsIgnoreCase("world_nether")) {
			if(player.getLocation().getBlockY() >= 128) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "Can't build above the nether");
			}
		}
		
	}

}
