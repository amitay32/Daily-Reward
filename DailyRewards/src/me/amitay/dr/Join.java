package me.amitay.dr;

import java.util.Date;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class Join implements Listener {
	private Main main;
	Date date = new Date(System.currentTimeMillis());
	public Join(Main main) {
		this.main = main; 
	}
	String prefix = (ChatColor.GRAY + "" + ChatColor.BOLD + "[" + ChatColor.AQUA + "" + ChatColor.BOLD + "Daily Rewards" + ChatColor.GRAY + "" + ChatColor.BOLD + "] " 
	+ ChatColor.BLUE + ChatColor.BOLD + ">> ");
	
	@EventHandler 
	public void onPlayerQuit(PlayerQuitEvent e) {
		long time = System.currentTimeMillis();
		main.getPlayerData().getPlayers().set("Players." + e.getPlayer().getUniqueId().toString() + ".TimerForDailyRewardReset",
				(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
		main.getPlayerData().savePlayers();	
	}
	

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		long time = System.currentTimeMillis();
		if (System.currentTimeMillis() > main.getPlayerData().getPlayers().getLong("Players." + p.getUniqueId().toString() + ".NextTime")) {
			main.getPlayerData().getPlayers().set("Players." + p.getUniqueId().toString() + ".Slot",
					main.getPlayerData().getPlayers().getInt("Players." + p.getUniqueId().toString() + ".LastItem" + 1));
			p.sendMessage(ChatColor.GREEN + "You can now take the next daily reward number " + 
					main.getPlayerData().getPlayers().getInt("Players." + p.getUniqueId().toString() + ".Slot" + "!"));
			main.getPlayerData().savePlayers();	
		}
		if (time > main.getPlayerData().getPlayers().getLong("Players." + e.getPlayer().getUniqueId().toString() + ".TimerForDailyRewardReset")) {
			main.getPlayerData().getPlayers().set("Players." + p.getUniqueId().toString() + ".Slot", 0);
			main.getPlayerData().getPlayers().set("Players." + p.getUniqueId().toString() + ".LastItem", 0);
		}
		
		
		main.getPlayerData().savePlayers();		

	}

	@EventHandler
	public void onPlayerClickInv(InventoryClickEvent e) {
		if (e.getInventory().getName().equals("Daily Rewards")) {
			e.setCancelled(true);
			int lastitem = main.getPlayerData().getPlayers().getInt("Players." + e.getWhoClicked().getUniqueId().toString() + ".LastItem");

			ConfigurationSection sec = main.getConfig().getConfigurationSection("Items.Slot");
			int slot = e.getSlot();
			List<String> cmd = main.getConfig().getStringList("Items.Slot." + slot + ".Commands");
			if (e.getClick().isLeftClick()) {
				if (e.getSlot() == main.getPlayerData().getPlayers().getInt("Players." + e.getWhoClicked().getUniqueId().toString() + ".Slot") &&
						e.getClickedInventory().getItem(e.getSlot()).getType().equals(main.getConfig().getItemStack("Items.Slot." + e.getSlot() + ".Item").getType())) {
					List<String> cmds = cmd;
					for (String cmds1 : cmd) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								cmds1.replace("%player%", e.getWhoClicked().getName()));
					}

					main.getPlayerData().getPlayers().set("Players." + e.getWhoClicked().getUniqueId().toString() + ".NextTime",
							(System.currentTimeMillis() + (24L * 60L * 60L * 1000L)));
					main.getPlayerData().getPlayers().set("Players." + e.getWhoClicked().getUniqueId().toString() + ".Slot", -1);
					main.getPlayerData().getPlayers().set("Players." + e.getWhoClicked().getUniqueId().toString() + ".LastItem", e.getSlot());
					main.getPlayerData().savePlayers();
					e.getWhoClicked().sendMessage(prefix + ChatColor.GREEN + "You just claimed reward number " + (slot + 1) + "!");
					return;
				} 
				if (lastitem >= e.getInventory().firstEmpty() -1 && e.getClickedInventory().getItem(e.getSlot()).getType().equals(main.getConfig().getItemStack("Items.Slot." + e.getSlot() + ".Item").getType())){
					e.getWhoClicked().sendMessage(prefix + ChatColor.GREEN + "You just finished all the daily rewards!, Your streak will reset now!");
					main.getPlayerData().getPlayers().set("Players." + e.getWhoClicked().getUniqueId().toString() + ".Slot", 0);
					return;
				}
				if (lastitem >= slot && e.getClickedInventory().getItem(e.getSlot()).getType().equals(main.getConfig().getItemStack("Items.Slot." + e.getSlot() + ".Item").getType())){
					e.getWhoClicked().sendMessage(prefix + ChatColor.GREEN + "You already claimed that reward!. Your next reward will be reward number " + ChatColor.RESET + (lastitem + 2));
					return;
				} else {
				
				if (lastitem <= slot && e.getClickedInventory().getItem(e.getSlot()).getType().equals(main.getConfig().getItemStack("Items.Slot." + e.getSlot() + ".Item").getType())) {
					e.getWhoClicked().sendMessage(prefix + ChatColor.GREEN + "You can not claim this reward yet!");
					return;
				}



			}
		}
	}
}
}



