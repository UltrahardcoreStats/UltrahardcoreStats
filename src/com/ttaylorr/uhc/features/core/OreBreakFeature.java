package com.ttaylorr.uhc.features.core;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import com.ttaylorr.uhc.UltrahardcoreStats;
import com.ttaylorr.uhc.features.StatsFeature;
import com.ttaylorr.uhc.web.KeyManager;
import com.ttaylorr.uhc.web.URLRunnable;

public class OreBreakFeature extends StatsFeature {

	static ArrayList<Material> ores;
	
	static {
		ores = new ArrayList<Material>();	
		ores.add(Material.COAL_ORE);
		ores.add(Material.IRON_ORE);
		ores.add(Material.GOLD_ORE);
		ores.add(Material.REDSTONE_ORE);
		ores.add(Material.GLOWING_REDSTONE_ORE);
		ores.add(Material.DIAMOND_ORE);
		ores.add(Material.EMERALD_ORE);
		ores.add(Material.QUARTZ_ORE);
	}
	
	public OreBreakFeature(boolean enabled) {
		super(enabled);
		this.setTitle("ores");
		this.setDescription("Communicates all ores being broken to the webserver...");
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) throws IllegalArgumentException, MalformedURLException {
		if (this.isEnabled()) {
			if(ores.contains(event.getBlock().getType()) && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
				HashMap<String, String> args = new HashMap<String, String>();
				args.put(KeyManager.player.name(), event.getPlayer().getName());
				args.put(KeyManager.ore_name.name() ,event.getBlock().getType() == Material.GLOWING_REDSTONE_ORE ? Material.REDSTONE_ORE.name() : event.getBlock().getType().name());
			
				Bukkit.getScheduler().runTaskAsynchronously(UltrahardcoreStats.getInstance(), new URLRunnable("ore.php", args, true));
			}
		}
	}
}
