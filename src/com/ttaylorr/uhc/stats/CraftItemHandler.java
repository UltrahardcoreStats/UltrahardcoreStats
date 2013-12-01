package com.ttaylorr.uhc.stats;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class CraftItemHandler implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onCraftItem(final CraftItemEvent event) {
		
		if (event.getInventory().getResult() != null) {
			for (ItemStack item : WebInterface.getValuableCraftables()) {
				if(event.getInventory().getResult().getType().equals(item.getType())) {
					if(event.getInventory().getResult().getDurability() == item.getDurability()) {
						System.out.println("GAP: " + event.getInventory().getResult().getDurability());
					}
				}
			}
		}				

		try {
	//		URL playerDeathURL = new URL("http://localhost/projects/uhc_server/death.php?player=" + args.get(0) + "&killer=" + args.get(1) + "&time=" + args.get(2));
	//		Bukkit.getScheduler().scheduleAsyncDelayedTask(WebInterface.getInstance(), new URLRunnable(playerDeathURL));		
		} catch(Exception e) {
			
		}

		
	}

}
