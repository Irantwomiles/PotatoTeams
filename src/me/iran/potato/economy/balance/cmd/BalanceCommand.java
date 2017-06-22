package me.iran.potato.economy.balance.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.iran.potato.PotatoTeams;
import me.iran.potato.economy.balance.Balance;
import net.md_5.bungee.api.ChatColor;

public class BalanceCommand implements CommandExecutor {

	PotatoTeams plugin;
	
	public BalanceCommand(PotatoTeams plugin) {
		this.plugin = plugin;
	}
	
	private Balance balance = new Balance(PotatoTeams.getInstance());
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		
		if(cmd.getName().contentEquals("balance")) {
			
			player.sendMessage(ChatColor.GOLD + "You have " + balance.getBalance(player.getUniqueId().toString()) + " gold");
			
		}
		
		return true;
	}

}
