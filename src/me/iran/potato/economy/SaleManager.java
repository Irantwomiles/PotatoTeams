package me.iran.potato.economy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.iran.potato.PotatoTeams;
import me.iran.potato.economy.balance.Balance;

public class SaleManager {

	private File file = null;
	
	private Balance balance = new Balance(PotatoTeams.getInstance());
	
	private static ArrayList<Sale> sales = new ArrayList<>();
	
	private static SaleManager manager;
	
	public SaleManager() {}
	
	public static SaleManager getManager() {
		if(manager == null) {
			manager = new SaleManager();
		}
		
		return manager;
	}
	
	public void loadSales() {
		
		file = new File(PotatoTeams.getInstance().getDataFolder(), "economy.yml");
		
		if(file.exists()) {
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			for(String number : config.getConfigurationSection("economy.id").getKeys(false)) {
				
				Object obj = config.get("economy.id." + number + ".item");
				
				ItemStack item = (ItemStack) obj;
				
				String seller = config.getString("economy.id." + number + ".seller");
				
				double price = config.getDouble("economy.id." + number + ".price");
				
				int count = config.getInt("economy.id." + number + ".count");
				
				int id = config.getInt("economy.id." + number + ".id");
				
				Sale sale = new Sale(item, seller, price, item.getAmount());
				
				item.setAmount(count);
				
				sale.setItem(item);
				sale.setSeller(seller);
				sale.setCount(count);
				sale.setPrice(price);
				sale.setId(id);
				
				sales.add(sale);
				
			}
			
		}
		
	}
	
	public void saveSales() {
		
		file = new File(PotatoTeams.getInstance().getDataFolder(), "economy.yml");
		
		if(file.exists()) {
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			for(Sale sale : sales) {
				
				if(config.contains("economy.id." + sale.getId())) {
					
					config.set("economy.id." + sale.getId(), null);
					
					config.createSection("economy.id." + sale.getId() + ".seller");
					config.createSection("economy.id." + sale.getId() + ".item");
					config.createSection("economy.id." + sale.getId() + ".price");
					config.createSection("economy.id." + sale.getId() + ".id");
					config.createSection("economy.id." + sale.getId() + ".count");

					config.set("economy.id." + sale.getId() + ".seller", sale.getSeller());
					config.set("economy.id." + sale.getId() + ".item", sale.getItem());
					config.set("economy.id." + sale.getId() + ".price", sale.getPrice());
					config.set("economy.id." + sale.getId() + ".id", sale.getId());
					config.set("economy.id." + sale.getId() + ".count", sale.getCount());
					
				}
				
			}
			
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void updatePlayerSales(Sale sale) {

		file = new File(PotatoTeams.getInstance().getDataFolder(), "economy.yml");

		if (file.exists()) {

			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

			if (config.contains("economy.id." + sale.getId())) {
				
				System.out.println(sale.getCount() + " count");
				
				if(sale.getCount() <= 0) {

					config.set("economy.id." + sale.getId(), null);
					
					try {
						config.save(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					System.out.println("Count <= 0 set to null");
					
					return;
				}
				
				System.out.println("Path exists and set to null");

				config.set("economy.id." + sale.getId() + ".seller", sale.getSeller());
				config.set("economy.id." + sale.getId() + ".item", sale.getItem());
				config.set("economy.id." + sale.getId() + ".price", sale.getPrice());
				config.set("economy.id." + sale.getId() + ".id", sale.getId());
				config.set("economy.id." + sale.getId() + ".count", sale.getCount());
				
				try {
					config.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} else {
				
				System.out.println("Path doesn't exists, updating");
				
				config.createSection("economy.id." + sale.getId() + ".seller");
				config.createSection("economy.id." + sale.getId() + ".item");
				config.createSection("economy.id." + sale.getId() + ".price");
				config.createSection("economy.id." + sale.getId() + ".id");
				config.createSection("economy.id." + sale.getId() + ".count");
				
				config.set("economy.id." + sale.getId() + ".seller", sale.getSeller());
				config.set("economy.id." + sale.getId() + ".item", sale.getItem());
				config.set("economy.id." + sale.getId() + ".price", sale.getPrice());
				config.set("economy.id." + sale.getId() + ".id", sale.getId());
				config.set("economy.id." + sale.getId() + ".count", sale.getCount());
				
				try {
					config.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			
		}

	}
	
	@SuppressWarnings("deprecation")
	public void sellItem(Player player, double price, ItemStack item, int count) {
		
		for(Sale sale : getSpecificItemSaleList(item)) {
			
			if(sale.getPrice() == price) {
				player.sendMessage(ChatColor.RED + "A " + item.getType().toString() + " with that price is already in the market");
				return;
			}
			
		}

		int stack = 0;
		
		for(ItemStack it : player.getInventory().getContents()) {
			
			if(it != null && it.getType() == item.getType() && it.getData().getData() == item.getData().getData()) {
				stack = stack + it.getAmount();
			}
			
		}
		
		if(stack < count) {
			player.sendMessage(ChatColor.RED + "You don't have enough of that item to sell");
			return;
		}

		item.setAmount(count);

		player.getInventory().removeItem(item);
		
		Sale sale = new Sale(item, player.getUniqueId().toString(), price, count);
	
		sales.add(sale);
		
		player.sendMessage(ChatColor.GOLD + "Added " + item.getAmount() + " of " + item.getType().toString() + " to the market for " + price);
		
		updatePlayerSales(sale);
		
	}
	
	@SuppressWarnings("deprecation")
	public void sellPotion(final Player player, double price, ItemStack item, int count) {
		
		for(Sale sale : getSpecificItemSaleList(item)) {
			
			if(sale.getPrice() == price) {
				player.sendMessage(ChatColor.RED + "A " + item.getType().toString() + " with that price is already in the market");
				return;
			}
			
		}

		int stack = 0;
		
		for(ItemStack it : player.getInventory().getContents()) {
			
			if(it != null && it.getType() == item.getType() && it.getData().getData() == item.getData().getData()) {
				stack = stack + it.getAmount();
			}
			
		}

		if(stack < count) {
			player.sendMessage(ChatColor.RED + "You don't have enough of that item to sell");
			return;
		}

		item.setAmount(count);
		
		for(int i = 0; i < player.getInventory().getContents().length; i++) {
					
			ItemStack it = player.getInventory().getContents()[i];
			
			if(count > 0) {
				
				if(it != null && it.getType() == item.getType() && it.getData().getData() == item.getData().getData()) {
					
					
					if(it.getAmount() == count) {
						count = 0;
						
						player.getInventory().removeItem(player.getInventory().getContents()[i]);
					} else if(it.getAmount() == 1) {
						count--;
						player.getInventory().removeItem(player.getInventory().getContents()[i]);
						
					} else if(it.getAmount() > count) {
						it.setAmount(it.getAmount() - count);
						count = 0;
						
					} 
				}
				
			}
			
		}

		Sale sale = new Sale(item, player.getUniqueId().toString(), price, item.getAmount());
	
		sales.add(sale);
		
		player.sendMessage(ChatColor.GOLD + "Added " + item.getAmount() + " of " + item.getType().toString() + " to the market for " + price);
		
		updatePlayerSales(sale);
		
	}
	
	public void buyItem(Player player, ItemStack item, double price, int amount) {
		
		/*
		 * TODO: 
		 * - Make sure the players inventory isn't full
		 */
		
		ArrayList<Double> cost = new ArrayList<>();
		
		for(Sale sale : getSpecificItemSaleList(item)) {
			cost.add(sale.getPrice());
		}
		
		Collections.sort(cost);
		
		if(cost.size() > 0) {
			
			if(price >= cost.get(0)) {
				
				Sale sale = getSaleByPrice(cost.get(0));
			
				if(amount <= sale.getCount()) {

					double actual = cost.get(0) * amount; 
					
					if(price >= actual) {
						
						/*
						 * The issue is this:
						 * 
						 * if I sell 64 coble stone for 64 Gold, does that mean 1 cobble is equal to 1 gold or are we staying with 64
						 */
						
						OfflinePlayer seller = Bukkit.getOfflinePlayer(UUID.fromString(sale.getSeller()));
						
						if(balance.getBalance(player.getUniqueId().toString()) >= cost.get(0)) {
							
							if(amount < sale.getCount()) {
								
								sale.setCount(sale.getCount() - amount);
								
								balance.updateBalance(sale.getSeller(), balance.getBalance(sale.getSeller()) + actual);
								
								if(seller.isOnline()) {
									((Player) seller).sendMessage(ChatColor.GOLD + "Someone has bought " + amount + " " + item.getType().toString() + " from you for " + sale.getPrice() + " gold. Your balance is " + balance.getBalance(sale.getSeller()));
								}
								
								item.setAmount(amount);
								
								player.getInventory().addItem(item);
								
								balance.updateBalance(player.getUniqueId().toString(), balance.getBalance(player.getUniqueId().toString()) - actual);
								
								player.sendMessage(ChatColor.GRAY + "You have just bought " + amount + " " + item.getType().toString() + " for " + sale.getPrice() + " your new balance is " + balance.getBalance(player.getUniqueId().toString()));
								
								updatePlayerSales(sale);
								
								return;
								
							} else if(amount == sale.getCount()) {
								
								sale.setCount(0);
								
								balance.updateBalance(sale.getSeller(), balance.getBalance(sale.getSeller()) + actual);
								
								if(seller.isOnline()) {
									((Player) seller).sendMessage(ChatColor.GOLD + "Someone has bought " + amount + " " + item.getType().toString() + " from you for " + sale.getPrice() + " gold. Your balance is " + balance.getBalance(sale.getSeller()));
								}
								player.getInventory().addItem(item);
								
								balance.updateBalance(player.getUniqueId().toString(), balance.getBalance(player.getUniqueId().toString()) - actual);
								
								player.sendMessage(ChatColor.GRAY + "You have just bought " + amount + " " + item.getType().toString() + " for " + sale.getPrice() + " your new balance is " + balance.getBalance(player.getUniqueId().toString()));
							
								updatePlayerSales(sale);
								
								sales.remove(sale);
								
								return;
							}
						} else {
							player.sendMessage(ChatColor.RED + "You don't have enough Gold to buy that");
						}
						
					}
				} else {
					player.sendMessage(ChatColor.RED + "The market only has " + sale.getCount() + " of " + item.getType().toString());
				}
				
			}   else {
				player.sendMessage(ChatColor.RED + "The cheapest " + item.getType().toString() + " is " + cost.get(0));
			}
			
			
		} else {
			player.sendMessage(ChatColor.RED + "The market does not have any " + item.getType().toString());
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public ArrayList<Sale> getSpecificItemSaleList(ItemStack item) {
		
		ArrayList<Sale> s = new ArrayList<>();
		
		for(Sale sale : sales) {
			if(sale.getItem().getType() == item.getType() && sale.getItem().getData().getData() == item.getData().getData()) {
				s.add(sale);
			}
		}
		
		return s;
	}
	
	public Sale getSaleByPrice(double price) {
		
		for(Sale sale : sales) {
			if(sale.getPrice() == price) {
				return sale;
			}
		}
		
		return null;
		
	}
	
	public ArrayList<Sale> getSalesByPlayer(Player player) {
		
		ArrayList<Sale> s = new ArrayList<>();
		
		for(Sale sale : sales) {
			if(sale.getSeller().equals(player.getUniqueId().toString())) {
				s.add(sale);
			}
		}
		
		return s;
	}
}
