package me.amitay.dr;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class DailyRewards implements CommandExecutor {
	private Main main;
	
	public DailyRewards(Main main) {
		this.main = main; 
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Error!, this plugin is for players only!");
		return true;
		}
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("dailyRewards")) {
			if (main.getConfig().contains("Items.Slot")) { 
			Inventory inv = Bukkit.createInventory(null, main.getConfig().getInt("Gui-Size"),
					"Daily Rewards");
			for (String key : main.getConfig().getConfigurationSection("Items.Slot")
					.getKeys(false)) {

				int amount = main.getConfig()
						.getInt("Items.Slot." + key + ".Amount"); 
				ItemStack item = new ItemBuilder(main.getConfig().getItemStack("Items.Slot." + key + ".Item"))
						.name(main.getConfig().getString("Items.Slot." + key + ".Name"))
						.amount(amount)
						.lore(main.getConfig().getStringList("Items.Slot." + key + ".Lore")).build();
				int slot = Integer.parseInt(key);
				inv.setItem(slot, item);
			}
			p.openInventory(inv);
			
		}
	} else {
		main.getPlayerData().getPlayers().set("Players." + p.getUniqueId().toString() + ".Slot", 0);
		main.getPlayerData().savePlayers();

		}
		return false;
	}

}
