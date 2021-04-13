package com.emiv.blockantispam;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import net.md_5.bungee.api.ChatColor;

public class blockPlaceEvent implements Listener {

	Main plugin;
	public blockPlaceEvent(Main instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		boolean canPlace = true;
		if (!e.getPlayer().hasPermission(plugin.getConfig().getString("PermToByPass"))) {
			if (plugin.blocksPlaced.containsKey(p)) {
				if (plugin.blocksPlaced.get(p) >= plugin.getConfig().getInt("MaxBlockPerSecond")) {
					e.setCancelled(true);
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ErrorMessage").replace("%prefix%", plugin.getConfig().getString("PluginPrefix")).replace("%block-amount%", String.valueOf(plugin.getConfig().getInt("MaxBlockPerSecond")))));
					canPlace = false;
				}
			}
		}
		if (canPlace) {
			int blocks = 1;
			if (plugin.blocksPlaced.containsKey(p)) {
				blocks = plugin.blocksPlaced.get(p) + 1;
			}
			plugin.blocksPlaced.put(p, blocks);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getServer().getPluginManager().getPlugin("BlockAntiSpam"), new Runnable() {
				@Override
			    public void run() {
					plugin.blocksPlaced.put(p, plugin.blocksPlaced.get(p) - 1);
				}
			}, 20L);
		}
	}
	
}
