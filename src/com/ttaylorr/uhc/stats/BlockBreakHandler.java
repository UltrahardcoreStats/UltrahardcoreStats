package com.ttaylorr.uhc.stats;

import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakHandler implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event) {
		
		String ore_name = null;
		String player_name = null;
		
		for (Material ORE : WebInterface.getValuableOres()) {
			if (event.getBlock().getType().equals(ORE)) {
				ore_name = ORE.name();
				player_name = event.getPlayer().getName();
//				System.out.println("Ore " + ORE.name() + " broken by " + event.getPlayer().getDisplayName());
				break;
			}
		}
		
		if(ore_name != null && player_name != null) {
			try {
				URL playerEntryURL = new URL("http://uhc.ttaylorr.com/stats_php/ore.php?player=" + player_name + "&ore_name=" + ore_name + "&api=" + WebInterface.getAPI_KEY());
				Bukkit.getScheduler().runTaskAsynchronously(WebInterface.getInstance(), new URLRunnable(playerEntryURL));
			} catch(Exception e) {
				
			}		
		}
		
	}
}