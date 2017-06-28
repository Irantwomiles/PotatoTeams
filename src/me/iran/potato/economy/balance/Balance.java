package me.iran.potato.economy.balance;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.iran.potato.PotatoTeams;

public class Balance implements Listener {

	private File file = null;
	
	PotatoTeams plugin;
	
	public Balance (PotatoTeams plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		
		createBalance(player.getUniqueId().toString());
	}
	
	public void createBalance(String uuid) {
		
		file = new File(PotatoTeams.getInstance().getDataFolder() + "/PlayerInfo", uuid + ".yml");
		
		if(!file.exists()) {
			
			file = new File(PotatoTeams.getInstance().getDataFolder() + "/PlayerInfo", uuid + ".yml");
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			config.createSection("gold");
			
			config.set("gold", 0.0);
			
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void updateBalance(String uuid, double gold) {
		
		file = new File(PotatoTeams.getInstance().getDataFolder() + "/PlayerInfo", uuid + ".yml");
		
		if(file.exists()) {
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			config.set("gold", gold);
			
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
		
			file = new File(PotatoTeams.getInstance().getDataFolder() + "/PlayerInfo", uuid + ".yml");
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			config.createSection("gold");
			
			config.set("gold", 0.0);
			
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public double getBalance(String uuid) {
		double gold = 0.0;
		
		file = new File(PotatoTeams.getInstance().getDataFolder() + "/PlayerInfo", uuid + ".yml");
		
		if(file.exists()) {
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			gold = config.getDouble("gold");
			
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			return 0.0;
		}
		
		return gold;
	}
}
