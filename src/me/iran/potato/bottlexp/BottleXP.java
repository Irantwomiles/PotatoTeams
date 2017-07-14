package me.iran.potato.bottlexp;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.iran.potato.PotatoTeams;
import net.md_5.bungee.api.ChatColor;

public class BottleXP implements CommandExecutor, Listener {

	PotatoTeams plugin;
	
	public BottleXP(PotatoTeams plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("bottle")) {
			
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
			
			if(player.getTotalExperience() < 17) {
				player.sendMessage(ChatColor.RED + "You must have atleast 1 level to bottle your XP");
				return true;
			}
			
			ItemStack bottle = new ItemStack(Material.EXP_BOTTLE, 1);
			ItemMeta bmeta = bottle.getItemMeta();
			
			bmeta.setDisplayName(ChatColor.GOLD + "Bottled XP " + ChatColor.YELLOW + player.getTotalExperience());
			bmeta.setLore(Arrays.asList(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "-------------------", ChatColor.RED.toString() + player.getTotalExperience(), ChatColor.YELLOW + "Bottled by: " + ChatColor.GRAY + player.getName(), ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "-------------------"));
			bottle.setItemMeta(bmeta);

			player.setLevel(0);
			player.setExp(0F);
			player.setTotalExperience(0);
			
			player.getInventory().addItem(bottle);
			
			player.updateInventory();
		}
		
		return true;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		
		if(event.getAction() == null) {
			return;
		}
		
		if(player.getItemInHand() == null) {
			return;
		}
		
		if(!player.getItemInHand().hasItemMeta()) {
			return;
		}
		
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			if(player.getItemInHand().getType() == Material.EXP_BOTTLE) {
				
				if(player.getItemInHand().getItemMeta() != null && player.getItemInHand().getItemMeta().hasDisplayName()) {
					
					if(player.getItemInHand().getItemMeta().getDisplayName().contains(ChatColor.GOLD + "Bottled XP ")) {
						event.setCancelled(true);
						player.updateInventory();
						
						String exp = ChatColor.stripColor(player.getItemInHand().getItemMeta().getLore().get(1));
						
						player.giveExp(Integer.parseInt(exp));
						
						if(player.getItemInHand().getAmount() > 1) {
							player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
						} else if(player.getItemInHand().getAmount() == 1) {
							player.getInventory().removeItem(player.getItemInHand());
						}
					}
				}
			}
			
		}
		
	}
	
}
