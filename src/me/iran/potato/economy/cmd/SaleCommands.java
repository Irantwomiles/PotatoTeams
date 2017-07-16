package me.iran.potato.economy.cmd;

import me.iran.potato.PotatoTeams;
import me.iran.potato.economy.Sale;
import me.iran.potato.economy.SaleManager;
import me.iran.potato.util.CollectionsUtil;

import org.bukkit.ChatColor;
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

			if(Integer.parseInt(args[0]) < 1) {
				player.sendMessage(ChatColor.RED + "The amount that you want to sell must be more than 0");
				return true;
			}

			if(Double.parseDouble(args[2]) < 0) {
				player.sendMessage(ChatColor.RED + "Can't sell anything with negative gold");
				return true;
			}

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
				case "steak":
					try {
						SaleManager.getManager().sellItem(player, Double.parseDouble(args[2]), new ItemStack(Material.COOKED_BEEF, Integer.parseInt(args[0])), Integer.parseInt(args[0]));
					} catch(Exception e) {
						player.sendMessage(ChatColor.RED + "/sell <amount> <item> <price>");
					}

					break;
				case "netherwart":
					try {
						SaleManager.getManager().sellItem(player, Double.parseDouble(args[2]), new ItemStack(Material.NETHER_STALK, Integer.parseInt(args[0])), Integer.parseInt(args[0]));
					} catch(Exception e) {
						player.sendMessage(ChatColor.RED + "/sell <amount> <item> <price>");
					}

					break;
				case "mooshegg":
					try {
						SaleManager.getManager().sellItem(player, Double.parseDouble(args[2]), new ItemStack(Material.MONSTER_EGG, Integer.parseInt(args[0]), (short) 96), Integer.parseInt(args[0]));
					} catch(Exception e) {
						player.sendMessage(ChatColor.RED + "/buy <amount> <item> <price>");
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

			if(CollectionsUtil.getCombat().containsKey(player.getName())) {

				int x = player.getLocation().getBlockX();
				int z = player.getLocation().getBlockZ();

				Math.abs(x);
				Math.abs(z);

				if(x < 512 && z < 512) {
					player.sendMessage(org.bukkit.ChatColor.RED + "You need to be 512 blocks away from spawn before buying anything while combat tagged");
					return true;
				}
			}


			try {
				if(Integer.parseInt(args[0]) < 1) {
					player.sendMessage(ChatColor.RED + "The amount that you want to buy must be more than 0");
					return true;
				}
			} catch (Exception e) {
				player.sendMessage(ChatColor.RED + "/buy <amount> <item> <price>");
			}
			
			
			try {
				if(Double.parseDouble(args[2]) < 0) {
					player.sendMessage(ChatColor.RED + "Can't buy anything with negative gold");
					return true;
				}
			} catch (Exception e) {
				player.sendMessage(ChatColor.RED + "/buy <amount> <item> <price>");
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
				case "steak":
					try {
						SaleManager.getManager().buyItem(player, new ItemStack(Material.COOKED_BEEF, Integer.parseInt(args[0])), Double.parseDouble(args[2]), Integer.parseInt(args[0]));
					} catch(Exception e) {
						player.sendMessage(ChatColor.RED + "/buy <amount> <item> <price>");
					}
					break;
				case "netherwart":
					try {
						SaleManager.getManager().buyItem(player, new ItemStack(Material.NETHER_STALK, Integer.parseInt(args[0])), Double.parseDouble(args[2]), Integer.parseInt(args[0]));
					} catch(Exception e) {
						player.sendMessage(ChatColor.RED + "/buy <amount> <item> <price>");
					}
					break;
				case "mooshegg":
					try {
						SaleManager.getManager().buyItem(player, new ItemStack(Material.MONSTER_EGG, Integer.parseInt(args[0]), (short) 96), Double.parseDouble(args[2]), Integer.parseInt(args[0]));
					} catch(Exception e) {
						player.sendMessage(ChatColor.RED + "/buy <amount> <item> <price>");
					}
					break;
					default:
						try {
							SaleManager.getManager().buyItem(player, new ItemStack(Material.getMaterial(args[1].toUpperCase()), Integer.parseInt(args[0])), Double.parseDouble(args[2]), Integer.parseInt(args[0]));
						} catch(Exception e) {
							player.sendMessage(ChatColor.RED + "/buy <amount> <item> <price>");
						}

						break;
			}

		}

		if(cmd.getName().equalsIgnoreCase("price")) {

            if(args.length < 1) {
                player.sendMessage(ChatColor.RED + "/price <item>");
                return true;
            }

            Potion potion = null;

            Sale sale;

            switch(args[0]) {

                case "strp2":

                    potion = new Potion(PotionType.STRENGTH, 2);

                    try {

                        double price = SaleManager.getManager().getCheapestSale(potion.toItemStack(1));

                        sale = SaleManager.getManager().getSaleByPrice(price, potion.toItemStack(1));

                        player.sendMessage(ChatColor.GOLD + "Item: " + ChatColor.GRAY + "Strength Potion 2" + "\n" +
                                              ChatColor.GOLD + "Stock: " + ChatColor.GRAY + sale.getCount() + "\n" +
                                              ChatColor.GOLD + "Price: " + ChatColor.GRAY + sale.getPrice());

                    } catch(Exception e) {
                        player.sendMessage(ChatColor.RED + "Couldn't find anything, try again! /price <item>");
                    }
                    break;
                case "strp":

                    potion = new Potion(PotionType.STRENGTH, 1);

                    try {
                        double price = SaleManager.getManager().getCheapestSale(potion.toItemStack(1));

                        sale = SaleManager.getManager().getSaleByPrice(price, potion.toItemStack(1));

                        player.sendMessage(ChatColor.GOLD + "Item: " + ChatColor.GRAY + "Strength Potion 1" + "\n" +
                                ChatColor.GOLD + "Stock: " + ChatColor.GRAY + sale.getCount() + "\n" +
                                ChatColor.GOLD + "Price: " + ChatColor.GRAY + sale.getPrice());
                    } catch(Exception e) {
                        player.sendMessage(ChatColor.RED + "Couldn't find anything, try again! /price <item>");
                    }
                    break;
                case "swp2":
                    potion = new Potion(PotionType.SPEED, 2);

                    try {
                        double price = SaleManager.getManager().getCheapestSale(potion.toItemStack(1));

                        sale = SaleManager.getManager().getSaleByPrice(price, potion.toItemStack(1));

                        player.sendMessage(ChatColor.GOLD + "Item: " + ChatColor.GRAY + "Speed Potion 2" + "\n" +
                                ChatColor.GOLD + "Stock: " + ChatColor.GRAY + sale.getCount() + "\n" +
                                ChatColor.GOLD + "Price: " + ChatColor.GRAY + sale.getPrice());
                    } catch(Exception e) {
                        player.sendMessage(ChatColor.RED + "Couldn't find anything, try again! /price <item>");
                    }
                    break;
                case "swp":

                    potion = new Potion(PotionType.SPEED, 1);

                    try {
                        double price = SaleManager.getManager().getCheapestSale(potion.toItemStack(1));

                        sale = SaleManager.getManager().getSaleByPrice(price, potion.toItemStack(1));

                        player.sendMessage(ChatColor.GOLD + "Item: " + ChatColor.GRAY + "Speed Potion 1" + "\n" +
                                ChatColor.GOLD + "Stock: " + ChatColor.GRAY + sale.getCount() + "\n" +
                                ChatColor.GOLD + "Price: " + ChatColor.GRAY + sale.getPrice());
                    } catch(Exception e) {
                        player.sendMessage(ChatColor.RED + "Couldn't find anything, try again! /price <item>");
                    }
                    break;
                case "frp12":
                    
                	potion = new Potion(PotionType.FIRE_RESISTANCE);
                    potion.setHasExtendedDuration(true);

                    try {
                        double price = SaleManager.getManager().getCheapestSale(potion.toItemStack(1));

                        sale = SaleManager.getManager().getSaleByPrice(price, potion.toItemStack(1));

                        player.sendMessage(ChatColor.GOLD + "Item: " + ChatColor.GRAY + "Fire Res Extended" + "\n" +
                                ChatColor.GOLD + "Stock: " + ChatColor.GRAY + sale.getCount() + "\n" +
                                ChatColor.GOLD + "Price: " + ChatColor.GRAY + sale.getPrice());
                    } catch(Exception e) {
                        player.sendMessage(ChatColor.RED + "Couldn't find anything, try again! /price <item>");
                    }
                    break;
                case "dps2":
                    potion = new Potion(PotionType.INSTANT_DAMAGE, 2);
                    potion.setSplash(true);

                    try {
                        double price = SaleManager.getManager().getCheapestSale(potion.toItemStack(1));

                        sale = SaleManager.getManager().getSaleByPrice(price, potion.toItemStack(1));

                        player.sendMessage(ChatColor.GOLD + "Item: " + ChatColor.GRAY + "Damage Potion Splash 2" + "\n" +
                                ChatColor.GOLD + "Stock: " + ChatColor.GRAY + sale.getCount() + "\n" +
                                ChatColor.GOLD + "Price: " + ChatColor.GRAY + sale.getPrice());
                    } catch(Exception e) {
                        player.sendMessage(ChatColor.RED + "Couldn't find anything, try again! /price <item>");
                    }
                    break;
                case "soup":
                    try {
                        ItemStack item = new ItemStack(Material.MUSHROOM_SOUP);

                        double price = SaleManager.getManager().getCheapestSale(item);

                        sale = SaleManager.getManager().getSaleByPrice(price, item);

                        player.sendMessage(ChatColor.GOLD + "Item: " + ChatColor.GRAY + "Mushroom Soup" + "\n" +
                                ChatColor.GOLD + "Stock: " + ChatColor.GRAY + sale.getCount() + "\n" +
                                ChatColor.GOLD + "Price: " + ChatColor.GRAY + sale.getPrice());
                    } catch(Exception e) {
                        player.sendMessage(ChatColor.RED + "Couldn't find anything, try again! /price <item>");
                    }
                    break;
                case "steak":
                    try {
                        ItemStack item = new ItemStack(Material.COOKED_BEEF);

                        double price = SaleManager.getManager().getCheapestSale(item);

                        sale = SaleManager.getManager().getSaleByPrice(price, item);

                        player.sendMessage(ChatColor.GOLD + "Item: " + ChatColor.GRAY + "Steak" + "\n" +
                                ChatColor.GOLD + "Stock: " + ChatColor.GRAY + sale.getCount() + "\n" +
                                ChatColor.GOLD + "Price: " + ChatColor.GRAY + sale.getPrice());
                    } catch(Exception e) {
                        player.sendMessage(ChatColor.RED + "Couldn't find anything, try again! /price <item>");
                    }
                    break;
                case "netherwart":
                    try {
                        ItemStack item = new ItemStack(Material.NETHER_STALK);

                        double price = SaleManager.getManager().getCheapestSale(item);

                        sale = SaleManager.getManager().getSaleByPrice(price, item);

                        player.sendMessage(ChatColor.GOLD + "Item: " + ChatColor.GRAY + "Nether Wart" + "\n" +
                                ChatColor.GOLD + "Stock: " + ChatColor.GRAY + sale.getCount() + "\n" +
                                ChatColor.GOLD + "Price: " + ChatColor.GRAY + sale.getPrice());
                    } catch(Exception e) {
                        player.sendMessage(ChatColor.RED + "Couldn't find anything, try again! /price <item>");
                    }
                    break;
                case "mooshegg":
                    try {
                        ItemStack item = new ItemStack(Material.MONSTER_EGG, 1, (short) 96);

                        double price = SaleManager.getManager().getCheapestSale(item);

                        sale = SaleManager.getManager().getSaleByPrice(price, item);

                        player.sendMessage(ChatColor.GOLD + "Item: " + ChatColor.GRAY + "Mooshroom Egg" + "\n" +
                                ChatColor.GOLD + "Stock: " + ChatColor.GRAY + sale.getCount() + "\n" +
                                ChatColor.GOLD + "Price: " + ChatColor.GRAY + sale.getPrice());
                    } catch(Exception e) {
                        player.sendMessage(ChatColor.RED + "Couldn't find anything, try again! /price <item>");
                    }
                    break;
                default:
                    try {

                        ItemStack item = new ItemStack(Material.getMaterial(args[0].toUpperCase()));

                        double price = SaleManager.getManager().getCheapestSale(item);

                        sale = SaleManager.getManager().getSaleByPrice(price, item);

                        player.sendMessage(ChatColor.GOLD + "Item: " + ChatColor.GRAY + sale.getItem().getType().toString() + "\n" +
                                ChatColor.GOLD + "Stock: " + ChatColor.GRAY + sale.getCount() + "\n" +
                                ChatColor.GOLD + "Price: " + ChatColor.GRAY + sale.getPrice());
                    } catch(Exception e) {
                        player.sendMessage(ChatColor.RED + "Couldn't find anything, try again! /price <item>");
                    }

                    break;
            }

		}

		return true;
	}


}
