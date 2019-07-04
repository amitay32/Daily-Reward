package me.amitay.dr;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	private PlayerData playerData;
	String prefix = (ChatColor.GRAY + "" + ChatColor.BOLD + "[" + ChatColor.AQUA + "" + ChatColor.BOLD + "Daily Rewards" + ChatColor.GRAY + "" + ChatColor.BOLD + "] " 
			+ ChatColor.BLUE + ChatColor.BOLD + ">> ");

	public void onEnable() {
		System.out.println(prefix + ChatColor.GREEN + "Has been ENABLED!");
		playerData = new PlayerData(this);
		playerData.setup();
		getCommand("setitem").setExecutor(new SetItem(this));
		getCommand("dailyRewards").setExecutor(new DailyRewards(this));
		getServer().getPluginManager().registerEvents(new Join(this), this);
	}
	public void onDisable() {
			System.out.println(prefix + ChatColor.GREEN + "Has been DISABLED!");
	}

	public PlayerData getPlayerData() {
		return playerData;
	}

	
}
