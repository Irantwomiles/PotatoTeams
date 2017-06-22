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

public class DepositCommand implements CommandExecutor {

	PotatoTeams plugin;
	
	public DepositCommand(PotatoTeams plugin) {
		this.plugin = plugin;
	}
	
	private Balance balance = new Balance(PotatoTeams.getInstance());
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		
		if(cmd.getName().contentEquals("deposit")) {
			
			if(args.length < 1) {
				player.sendMessage(ChatColor.RED + "/deposit <number>");
				return true;
			}
			
			try {
				
				int count = Integer.parseInt(args[0]);
				
				int stack = 0;
				
				ItemStack item = new ItemStack(Material.GOLD_INGOT, 0);
				
				for(ItemStack it : player.getInventory().getContents()) {
					
					if(it != null && it.getType() == Material.GOLD_INGOT) {
						stack = stack + it.getAmount();
					}
					
				}
				
				if(stack < count) {
					player.sendMessage(ChatColor.RED + "You don't have " + count + " gold to deposit");
					return true;
				}
				
				item.setAmount(count);
				
				player.getInventory().removeItem(item);
				
				balance.updateBalance(player.getUniqueId().toString(), balance.getBalance(player.getUniqueId().toString()) + count);
				
				player.sendMessage(ChatColor.GOLD + "Added " + count + " gold to your balance. Your new balance is " + balance.getBalance(player.getUniqueId().toString()));
				
			} catch (NumberFormatException e) {
				player.sendMessage(ChatColor.RED + "/deposit <number>");
			}
			

			
		}
		
		return true;
	}
	
}
