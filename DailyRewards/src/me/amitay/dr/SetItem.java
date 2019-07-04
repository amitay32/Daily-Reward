package me.amitay.dr;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;


public class SetItem implements CommandExecutor {
	private Main pl;

	@SuppressWarnings("static-access")
	public SetItem (Main pl) {
		this.pl = pl;
	}
	String prefix = (ChatColor.GRAY + "" + ChatColor.BOLD + "[" + ChatColor.AQUA + "" + ChatColor.BOLD + "Daily Rewards" + ChatColor.GRAY + "" + ChatColor.BOLD + "] " 
			+ ChatColor.BLUE + ChatColor.BOLD + ">> ");


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {	

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.DARK_RED + "This plugin is for players only!");
			return true;
		}		
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("setitem")) {
			if (p.hasPermission("setitem.use")){
				if(args.length == 3){
                    String slot = args[0];
                    ItemStack item = p.getItemInHand();
                    int amount = p.getItemInHand().getAmount();
                    MaterialData data = item.getData();
                    String name = args[1];
                    String lore = args[2];
                    ItemStack air = new ItemStack(Material.AIR);
				 
				if(!(item == air)){
                        int num = Integer.parseInt(slot);
                        String path = "." + num;
                        ArrayList lorre = new ArrayList<>();
                        lorre.add(args[2]);

                       
                        
                        pl.getConfig().set("Items." + ".Slot" + path, "");
                        pl.getConfig().set("Items." + ".Slot" + path + ".Item", item);
                        pl.getConfig().set("Items." + ".Slot" + path + ".Amount", amount);
                        pl.getConfig().set("Items." + ".Slot" + path + ".Name", name);
                        pl.getConfig().set("Items." + ".Slot" + path + ".Lore", lorre);
                        pl.saveConfig();

                        p.sendMessage(prefix + ChatColor.GREEN + item.getType().toString() + " With the name " + ChatColor.WHITE + "" + ChatColor.BOLD + name + ChatColor.RESET
                        		+ ChatColor.GREEN + " with the lore " + ChatColor.WHITE + "" + ChatColor.BOLD + lore + ChatColor.RESET + ChatColor.GREEN +
                        		" has been added to slot " + ChatColor.WHITE + "" + ChatColor.BOLD + num);
                    }
			}
			
		}
		return false;
	}
		return false;
}
}
