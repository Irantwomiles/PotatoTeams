package me.iran.potato;

import java.io.File;
import java.io.IOException;

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
import me.iran.potato.spawn.SpawnProtection;
import me.iran.potato.spawn.SpawnProtectionCommands;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PotatoTeams extends JavaPlugin {

	private File file = null;
	
	private ConsoleCommandSender log = this.getServer().getConsoleSender();
	
	TeleportRunnables run = new TeleportRunnables();
	
	private static PotatoTeams instance;
	
	public static PotatoTeams getInstance() {
		return instance;
	}
	
	/**
	 * TODO: 
	 * - Login message for teams
	 * - Quit message for teams
	 * - Login message for no spawn protection
	 * - Warps
	 * - Remove sections from economy.yml when the item has run out
	 * - testing
	 */
	
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
		 * PlayerGold folder
		 */
		
		file = new File(this.getDataFolder() + "/PlayerGold", "DELETE.yml");
		
		if(!file.exists()) {
			file = new File(this.getDataFolder() + "/PlayerGold", "DELETE.yml");
			
			YamlConfiguration delete = YamlConfiguration.loadConfiguration(file);
			
			delete.createSection("Delete this file");
			
			try {
				delete.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			log.sendMessage(ChatColor.GREEN + "[Potato] Generated /PlayerGold folder");
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
		 * Commands
		 */
		
		getCommand("team").setExecutor(new PlayerFactionCommands(this));
		getCommand("spawnloc1").setExecutor(new SpawnProtectionCommands(this));
		getCommand("spawnloc2").setExecutor(new SpawnProtectionCommands(this));
		getCommand("setspawn").setExecutor(new SpawnProtectionCommands(this));
		getCommand("spawn").setExecutor(new SpawnProtectionCommands(this));
		getCommand("sell").setExecutor(new SaleCommands(this));
		getCommand("buy").setExecutor(new SaleCommands(this));
		getCommand("balance").setExecutor(new BalanceCommand(this));
		getCommand("deposit").setExecutor(new DepositCommand(this));
		getCommand("withdraw").setExecutor(new WithdrawCommand(this));
		/**
		 * Events
		 */
		
		Bukkit.getPluginManager().registerEvents(new AttackPlayerEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new TeleportEvents(this), this);
		Bukkit.getPluginManager().registerEvents(new TeamChatEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new DisconnectEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new SpawnProtection(this), this);
		Bukkit.getPluginManager().registerEvents(new Balance(this), this);
		/**
		 * Things needed to load
		 */
		
		PlayerFactionManager.getManager().loadPlayerFactions();
		SaleManager.getManager().loadSales();
		
		/**
		 * Runnables
		 */
		
		run.runTaskTimer(this, 20, 20);
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
	}
	
	public void onDisable() {
		PlayerFactionManager.getManager().savePlayerFactions();
		
		SaleManager.getManager().saveSales();
		
	}
	
}
