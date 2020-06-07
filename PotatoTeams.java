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
	
	@Ovveride
	public void onEnable() {
		super.onEnable();
		
	}

}
