package me.iran.potato.economy.balance.cmd;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.iran.potato.PotatoTeams;
import me.iran.potato.economy.balance.Balance;
import net.md_5.bungee.api.ChatColor;

public class WithdrawCommand implements CommandExecutor {

	PotatoTeams plugin;
	
	public WithdrawCommand(PotatoTeams plugin) {
		this.plugin = plugin;
	}
	
	private Balance balance = new Balance(PotatoTeams.getInstance());

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("withdraw")) {
			
			try{
				
				if(args.length < 1) {
					player.sendMessage(ChatColor.RED + "/withdraw <number>");
					return true;
				}
				
				ItemStack item = new ItemStack(Material.GOLD_INGOT, 0);

				if(balance.getBalance(player.getUniqueId().toString()) < 1) {
					player.sendMessage(ChatColor.RED + "You don't have enough gold to withdraw");
					return true;
				}
				
				double gold = balance.getBalance(player.getUniqueId().toString());
				
				int amount = Integer.parseInt(args[0]);

				if(amount < 1) {
					player.sendMessage(ChatColor.RED + "You can't withdraw less than 1 gold");
					return true;
				}

				if(amount > gold) {
					player.sendMessage(ChatColor.RED + "You don't have that much gold");
					return true;
				}


				int open = 0;

				for(ItemStack it : player.getInventory().getContents()) {
					if(it == null) {
						open++;
					}
				}

				if(open == 0) {
					player.sendMessage(ChatColor.RED + "Your inventory is full!");
					return true;
				}

				gold = gold - amount;
				
				balance.updateBalance(player.getUniqueId().toString(), gold);
				
				item.setAmount(amount);
				
				player.getInventory().addItem(item);
				
				player.sendMessage(ChatColor.GRAY + "Withdrew " + amount + " Gold from your account");
				
			} catch (NumberFormatException e) {
				player.sendMessage(ChatColor.RED + "/withdraw <number>");
			}
			
		}
		
		return true;
	}
	
}
