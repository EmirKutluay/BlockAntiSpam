package com.emiv.blockantispam;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public HashMap<Player, Integer> blocksPlaced = new HashMap<Player, Integer>();
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		Bukkit.getPluginManager().registerEvents(new blockPlaceEvent(this), this);
	}
	
}
