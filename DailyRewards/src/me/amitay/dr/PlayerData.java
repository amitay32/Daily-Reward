package me.amitay.dr;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerData {
	private Main main;
	private FileConfiguration playerscfg;
	private File playersfile;

	public PlayerData(Main main) {
		this.main = main;
		this.playersfile = new File(main.getDataFolder(), "players.yml");
	}

	public void setup() {
		if (!main.getDataFolder().exists()) {
			main.getDataFolder().mkdir();
		}

		if (!playersfile.exists()) {
			try {
				playersfile.createNewFile();
			} catch (IOException e) {
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.RED + "Error!, Could not create players.yml file");
			}
		}
		playerscfg = YamlConfiguration.loadConfiguration(playersfile);
	}

	public FileConfiguration getPlayers() {
		return playerscfg;
	}

	public void savePlayers() {
		try {
			playerscfg.save(playersfile);
		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender()
					.sendMessage(ChatColor.RED + "Error!, Could not save the players.yml file");
		}
	}

	public void reloadPlayers() {
		playerscfg = YamlConfiguration.loadConfiguration(playersfile);
	}
}

