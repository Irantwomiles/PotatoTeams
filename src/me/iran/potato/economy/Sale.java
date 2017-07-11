package me.iran.potato.economy;

import org.bukkit.inventory.ItemStack;

public class Sale {

	private ItemStack item;
	
	private String seller;
	
	private double price;
	
	private int count;
	
	static int id = 0;
	
	private int ID;

	public Sale () {}

	public Sale(ItemStack item, String seller, double price, int count) {
		this.item = item;
		this.seller = seller;
		this.price = price;
		this.count = count;
		
		id++;
		
		ID = id;
		
	}

	public int getId() {
		return ID;
	}
	
	public void setId(int ID) {
		this.ID = ID;
	}
	
	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
