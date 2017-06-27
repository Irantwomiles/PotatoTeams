package me.iran.potato.economy.cmd;

import me.iran.potato.PotatoTeams;
import me.iran.potato.economy.SaleManager;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class SaleCommands implements CommandExecutor {

	PotatoTeams plugin;
	
	public SaleCommands(PotatoTeams plugin) {
		this.plugin = plugin;
	}
	
	/*
	 * TODO:
	 * - Allow the buy/sale of potions
	 * - Change Material to ItemStack
	 *   * Players can sell/buy their own goods to repair items
	 *   * Players will not get enchantments on items sold
	 */
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("sell")) {

			if(args.length < 3) {
				player.sendMessage(ChatColor.RED + "/sell <amount> <item> <price>");
				return true;
			}

			//dps2

			Potion potion = null;

			switch(args[1]) {

				case "strp2":

					potion = new Potion(PotionType.STRENGTH, 2);

					try {
						SaleManager.getManager().sellPotion(player, Double.parseDouble(args[2]), potion.toItemStack(1), Integer.parseInt(args[0]));

					} catch(Exception e) {
						player.sendMessage(ChatColor.RED + "/sell <amount> <item> <price>");
					}

					break;
				case "strp":

					potion = new Potion(PotionType.STRENGTH, 1);

					try {
						SaleManager.getManager().sellPotion(player, Double.parseDouble(args[2]), potion.toItemStack(1), Integer.parseInt(args[0]));

					} catch(Exception e) {
						player.sendMessage(ChatColor.RED + "/sell <amount> <item> <price>");
					}

					break;
				case "swp2":

					potion = new Potion(PotionType.SPEED, 2);

					try {
						SaleManager.getManager().sellPotion(player, Double.parseDouble(args[2]), potion.toItemStack(1), Integer.parseInt(args[0]));
					} catch(Exception e) {
						player.sendMessage(ChatColor.RED + "/sell <amount> <item> <price>");
					}

					break;
				case "swp":

					potion = new Potion(PotionType.SPEED, 1);

					try {
						SaleManager.getManager().sellPotion(player, Double.parseDouble(args[2]), potion.toItemStack(1), Integer.parseInt(args[0]));
					} catch(Exception e) {
						player.sendMessage(ChatColor.RED + "/sell <amount> <item> <price>");
					}

					break;
				case "frp1e":
					potion = new Potion(PotionType.FIRE_RESISTANCE);

					potion.setHasExtendedDuration(true);

					try {
						SaleManager.getManager().sellPotion(player, Double.parseDouble(args[2]), potion.toItemStack(1), Integer.parseInt(args[0]));
					} catch(Exception e) {
						player.sendMessage(ChatColor.RED + "/sell <amount> <item> <price>");
					}

					break;
				case "dps2":

					potion = new Potion(PotionType.INSTANT_DAMAGE, 2);
					potion.setSplash(true);

					try {
						SaleManager.getManager().sellPotion(player, Double.parseDouble(args[2]), potion.toItemStack(1), Integer.parseInt(args[0]));

					} catch(Exception e) {
						player.sendMessage(ChatColor.RED + "/sell <amount> <item> <price>");
					}

					break;
				case "soup":
					try {
						SaleManager.getManager().sellItem(player, Double.parseDouble(args[2]), new ItemStack(Material.MUSHROOM_SOUP, Integer.parseInt(args[0])), Integer.parseInt(args[0]));
					} catch(Exception e) {
						player.sendMessage(ChatColor.RED + "/sell <amount> <item> <price>");
					}

					break;

					default:

						try {

							ItemStack item = new ItemStack(Material.getMaterial(args[1].toUpperCase()), Integer.parseInt(args[0]));

							SaleManager.getManager().sellItem(player, Double.parseDouble(args[2]), item, item.getAmount());



						} catch(Exception e) {
							player.sendMessage(ChatColor.RED + "/sell <amount> <item> <price>");

						}

						break;

			}


		}

		if(cmd.getName().equalsIgnoreCase("buy")) {

			if(args.length < 3) {
				player.sendMessage(ChatColor.RED + "/buy <amount> <item> <price>");
				return true;
			}

			Potion potion = null;

			switch(args[1]) {

				case "strp2":
					potion = new Potion(PotionType.STRENGTH, 2);

					try {
						SaleManager.getManager().buyItem(player, potion.toItemStack(Integer.parseInt(args[0])), Double.parseDouble(args[2]), Integer.parseInt(args[0]));
					} catch(Exception e) {
						player.sendMessage(ChatColor.RED + "/buy <amount> <item> <price>");
					}
					break;
				case "strp":
					potion = new Potion(PotionType.STRENGTH, 1);

					try {
						SaleManager.getManager().buyItem(player, potion.toItemStack(Integer.parseInt(args[0])), Double.parseDouble(args[2]), Integer.parseInt(args[0]));
					} catch(Exception e) {
						player.sendMessage(ChatColor.RED + "/buy <amount> <item> <price>");
					}
					break;
				case "swp2":
					potion = new Potion(PotionType.SPEED, 2);

					try {
						SaleManager.getManager().buyItem(player, potion.toItemStack(Integer.parseInt(args[0])), Double.parseDouble(args[2]), Integer.parseInt(args[0]));
					} catch(Exception e) {
						player.sendMessage(ChatColor.RED + "/buy <amount> <item> <price>");
					}
					break;
				case "swp":

					potion = new Potion(PotionType.SPEED, 1);

					try {
						SaleManager.getManager().buyItem(player, potion.toItemStack(Integer.parseInt(args[0])), Double.parseDouble(args[2]), Integer.parseInt(args[0]));
					} catch(Exception e) {
						player.sendMessage(ChatColor.RED + "/buy <amount> <item> <price>");
					}
					break;
				case "frp12":
					potion = new Potion(PotionType.FIRE_RESISTANCE);
					potion.setHasExtendedDuration(true);

					try {
						SaleManager.getManager().buyItem(player, potion.toItemStack(Integer.parseInt(args[0])), Double.parseDouble(args[2]), Integer.parseInt(args[0]));
					} catch(Exception e) {
						player.sendMessage(ChatColor.RED + "/buy <amount> <item> <price>");
					}
					break;
				case "dps2":
					potion = new Potion(PotionType.INSTANT_DAMAGE, 2);
					potion.setSplash(true);

					try {
						SaleManager.getManager().buyItem(player, potion.toItemStack(Integer.parseInt(args[0])), Double.parseDouble(args[2]), Integer.parseInt(args[0]));
					} catch(Exception e) {
						player.sendMessage(ChatColor.RED + "/buy <amount> <item> <price>");
					}
					break;
				case "soup":
					try {
						SaleManager.getManager().buyItem(player, new ItemStack(Material.MUSHROOM_SOUP, Integer.parseInt(args[0])), Double.parseDouble(args[2]), Integer.parseInt(args[0]));
					} catch(Exception e) {
						player.sendMessage(ChatColor.RED + "/buy <amount> <item> <price>");
					}
					break;

					default:
						try {
							SaleManager.getManager().buyItem(player, new ItemStack(Material.getMaterial(args[1].toUpperCase()), Integer.parseInt(args[0])), Double.parseDouble(args[2]), Integer.parseInt(args[0]));
						} catch(NumberFormatException e) {
							player.sendMessage(ChatColor.RED + "/buy <amount> <item> <price>");
						}

						break;
			}

		}
		
		return true;
	}
}
