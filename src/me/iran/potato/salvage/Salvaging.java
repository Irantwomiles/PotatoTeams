package me.iran.potato.salvage;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.iran.potato.PotatoTeams;

public class Salvaging implements Listener {

	PotatoTeams plugin;
	
	public Salvaging (PotatoTeams plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		
		if(event.getAction() == null) {
			return;
		}
		
		if(player.getItemInHand().getType() == null) {
			return;
		}
		

		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			//Diamond Block
			
			if(event.getClickedBlock().getType() == Material.DIAMOND_BLOCK) {

				if (event.getClickedBlock().getRelative(BlockFace.NORTH).getType() == Material.FURNACE
						|| event.getClickedBlock().getRelative(BlockFace.SOUTH).getType() == Material.FURNACE
						|| event.getClickedBlock().getRelative(BlockFace.WEST).getType() == Material.FURNACE
						|| event.getClickedBlock().getRelative(BlockFace.EAST).getType() == Material.FURNACE) {
					
					String name = player.getItemInHand().getType().toString();
					
					System.out.println(name);
					
					event.setCancelled(true);
					player.updateInventory();
					
					int currdura = 0;
					
					int perc = 0;
					
					try {
						currdura = player.getItemInHand().getType().getMaxDurability() - player.getItemInHand().getDurability();
						perc = (currdura * 100 / player.getItemInHand().getType().getMaxDurability());
					} catch (Exception e) {
						
					}
					

					
					double x = event.getClickedBlock().getLocation().getX();
					double y = event.getClickedBlock().getLocation().getY() + 2;
					double z = event.getClickedBlock().getLocation().getZ();
					
					Location loc = new Location(event.getClickedBlock().getLocation().getWorld(), x, y, z);
					
					int cost = 0;
					
					int amount = 0;
					
					if(name.equalsIgnoreCase("DIAMOND_HELMET")) {
						
						cost = 5;
						
						amount = (int) ((cost * perc) / 100);
						
						if(amount == 0) {
							amount = 1;
						}
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.DIAMOND, amount));
						
					} else if(name.equalsIgnoreCase("DIAMOND_CHESTPLATE")) {
						cost = 8;
						
						amount = (int) ((cost * perc) / 100);
						
						if(amount == 0) {
							amount = 1;
						}
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.DIAMOND, amount));
					} else if(name.equalsIgnoreCase("DIAMOND_LEGGINGS")) {
						cost = 7;
						
						amount = (int) ((cost * perc) / 100);
						
						if(amount == 0) {
							amount = 1;
						}
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.DIAMOND, amount));
					
					} else if(name.equalsIgnoreCase("DIAMOND_BOOTS")) {
						cost = 4;
						
						amount = (int) ((cost * perc) / 100);
						
						if(amount == 0) {
							amount = 1;
						}
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.DIAMOND, amount));
					} else if(name.equalsIgnoreCase("DIAMOND_PICKAXE") || name.equalsIgnoreCase("DIAMOND_AXE")) {
						cost = 3;
						
						amount = (int) ((cost * perc) / 100);
						
						if(amount == 0) {
							amount = 1;
						}
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.DIAMOND, amount));
					
					} else if(name.equalsIgnoreCase("DIAMOND_HOE") || name.equalsIgnoreCase("DIAMOND_SWORD")) {
						cost = 2;
						
						amount = (int) ((cost * perc) / 100);
						
						if(amount == 0) {
							amount = 1;
						}
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.DIAMOND, amount));
					
					} else if(name.equalsIgnoreCase("DIAMOND_SPADE")) {
						cost = 1;
						
						amount = (int) ((cost * perc) / 100);
						
						if(amount == 0) {
							amount = 1;
						}
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.DIAMOND, amount));
					
					} else if(name.equalsIgnoreCase("DIAMOND_BARDING")) {
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.DIAMOND, 4));
					} 
					
				}
				
			}
			
			//Gold Block
			
			if(event.getClickedBlock().getType() == Material.GOLD_BLOCK) {

				if (event.getClickedBlock().getRelative(BlockFace.NORTH).getType() == Material.FURNACE
						|| event.getClickedBlock().getRelative(BlockFace.SOUTH).getType() == Material.FURNACE
						|| event.getClickedBlock().getRelative(BlockFace.WEST).getType() == Material.FURNACE
						|| event.getClickedBlock().getRelative(BlockFace.EAST).getType() == Material.FURNACE) {
					
					String name = player.getItemInHand().getType().toString();
					
					System.out.println(name);
					
					event.setCancelled(true);
					player.updateInventory();
					
					int currdura = 0;
					
					int perc = 0;
					
					try {
						currdura = player.getItemInHand().getType().getMaxDurability() - player.getItemInHand().getDurability();
						perc = (currdura * 100 / player.getItemInHand().getType().getMaxDurability());
					} catch (Exception e) {
						
					}
					

					
					double x = event.getClickedBlock().getLocation().getX();
					double y = event.getClickedBlock().getLocation().getY() + 2;
					double z = event.getClickedBlock().getLocation().getZ();
					
					Location loc = new Location(event.getClickedBlock().getLocation().getWorld(), x, y, z);
					
					int cost = 0;
					
					int amount = 0;
					
					if(name.equalsIgnoreCase("GOLD_HELMET")) {
						
						cost = 5;
						
						amount = (int) ((cost * perc) / 100);
						
						if(amount == 0) {
							amount = 1;
						}
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.GOLD_INGOT, amount));
						
					} else if(name.equalsIgnoreCase("GOLD_CHESTPLATE")) {
						cost = 8;
						
						amount = (int) ((cost * perc) / 100);
						
						if(amount == 0) {
							amount = 1;
						}
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.GOLD_INGOT, amount));
					} else if(name.equalsIgnoreCase("GOLD_LEGGINGS")) {
						cost = 7;
						
						amount = (int) ((cost * perc) / 100);
						
						if(amount == 0) {
							amount = 1;
						}
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.GOLD_INGOT, amount));
					
					} else if(name.equalsIgnoreCase("GOLD_BOOTS")) {
						cost = 4;
						
						amount = (int) ((cost * perc) / 100);
						
						if(amount == 0) {
							amount = 1;
						}
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.GOLD_INGOT, amount));
					} else if(name.equalsIgnoreCase("GOLD_PICKAXE") || name.equalsIgnoreCase("GOLD_AXE")) {
						cost = 3;
						
						amount = (int) ((cost * perc) / 100);
						
						if(amount == 0) {
							amount = 1;
						}
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.GOLD_INGOT, amount));
					
					} else if(name.equalsIgnoreCase("GOLD_HOE") || name.equalsIgnoreCase("GOLD_SWORD")) {
						cost = 2;
						
						amount = (int) ((cost * perc) / 100);
						
						if(amount == 0) {
							amount = 1;
						}
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.GOLD_INGOT, amount));
					
					} else if(name.equalsIgnoreCase("GOLD_SPADE")) {
						cost = 1;
						
						amount = (int) ((cost * perc) / 100);
						
						if(amount == 0) {
							amount = 1;
						}
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.GOLD_INGOT, amount));
					
					} else if(name.equalsIgnoreCase("GOLD_BARDING")) {
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.GOLD_INGOT, 4));
					} 
					
				}
				
			}
			
			//Iron Block
			
			if(event.getClickedBlock().getType() == Material.IRON_BLOCK) {

				if (event.getClickedBlock().getRelative(BlockFace.NORTH).getType() == Material.FURNACE
						|| event.getClickedBlock().getRelative(BlockFace.SOUTH).getType() == Material.FURNACE
						|| event.getClickedBlock().getRelative(BlockFace.WEST).getType() == Material.FURNACE
						|| event.getClickedBlock().getRelative(BlockFace.EAST).getType() == Material.FURNACE) {
					
					String name = player.getItemInHand().getType().toString();
					
					System.out.println(name);
					
					event.setCancelled(true);
					player.updateInventory();
					
					int currdura = 0;
					
					int perc = 0;
					
					try {
						currdura = player.getItemInHand().getType().getMaxDurability() - player.getItemInHand().getDurability();
						perc = (currdura * 100 / player.getItemInHand().getType().getMaxDurability());
					} catch (Exception e) {
						
					}
					
					double x = event.getClickedBlock().getLocation().getX();
					double y = event.getClickedBlock().getLocation().getY() + 2;
					double z = event.getClickedBlock().getLocation().getZ();
					
					Location loc = new Location(event.getClickedBlock().getLocation().getWorld(), x, y, z);
					
					int cost = 0;
					
					int amount = 0;
					
					if(name.equalsIgnoreCase("IRON_HELMET")) {
						
						cost = 5;
						
						amount = (int) ((cost * perc) / 100);
						
						if(amount == 0) {
							amount = 1;
						}
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.IRON_INGOT, amount));
						
					} else if(name.equalsIgnoreCase("IRON_CHESTPLATE")) {
						cost = 8;
						
						amount = (int) ((cost * perc) / 100);
						
						if(amount == 0) {
							amount = 1;
						}
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.IRON_INGOT, amount));
					} else if(name.equalsIgnoreCase("IRON_LEGGINGS")) {
						cost = 7;
						
						amount = (int) ((cost * perc) / 100);
						
						if(amount == 0) {
							amount = 1;
						}
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.IRON_INGOT, amount));
					
					} else if(name.equalsIgnoreCase("IRON_BOOTS")) {
						cost = 4;
						
						amount = (int) ((cost * perc) / 100);
						
						if(amount == 0) {
							amount = 1;
						}
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.IRON_INGOT, amount));
					} else if(name.equalsIgnoreCase("IRON_PICKAXE") || name.equalsIgnoreCase("IRON_AXE")) {
						cost = 3;
						
						amount = (int) ((cost * perc) / 100);
						
						if(amount == 0) {
							amount = 1;
						}
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.IRON_INGOT, amount));
					
					} else if(name.equalsIgnoreCase("IRON_HOE") || name.equalsIgnoreCase("IRON_SWORD")) {
						cost = 2;
						
						amount = (int) ((cost * perc) / 100);
						
						if(amount == 0) {
							amount = 1;
						}
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.IRON_INGOT, amount));
					
					} else if(name.equalsIgnoreCase("IRON_SPADE")) {
						cost = 1;
						
						amount = (int) ((cost * perc) / 100);
						
						if(amount == 0) {
							amount = 1;
						}
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.IRON_INGOT, amount));
					
					} else if(name.equalsIgnoreCase("IRON_BARDING")) {
						
						player.getInventory().removeItem(player.getItemInHand());
						
						Bukkit.getServer().getWorld(player.getWorld().getName()).dropItem(loc, new ItemStack(Material.IRON_INGOT, 4));
					} 
					
				}
				
			}
			
		}
		
	}
	
}
