package com.ttaylorr.uhc.stats;

import java.net.URL;

import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerItemConsumeHandler implements Listener {

	boolean isValuable;

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerItemConsume(final PlayerItemConsumeEvent event) {
		
		if(event.getItem().getType() == Material.POTION) {
		}
		
		else {
			isValuable = false;
			
			for(Material food : WebInterface.getValuableConsumables()) {
				if(food == event.getItem().getType()) {
					isValuable = true;
					break;
				}
			}
			
			if(isValuable) {
//				event.getPlayer().sendMessage(ChatColor.GOLD + "[Food] " + event.getItem().getType().name() + ChatColor.RESET);
				try {
					URL playerEatURL = new URL("http://uhc.ttaylorr.com/stats_php/food_item.php?api=" + WebInterface.getAPI_KEY() + "&type=" + foodFormat(event.getItem()) + "&player=" + event.getPlayer().getName());
					Bukkit.getScheduler().scheduleAsyncDelayedTask(WebInterface.getInstance(), new URLRunnable(playerEatURL));
				} catch(Exception e) {
					
				}
			} else {
//				event.getPlayer().sendMessage(ChatColor.GOLD + "Your food was not valuable D:" + ChatColor.RESET);
			}			
		}
		

		
	}

	public String foodFormat(ItemStack s) {
		switch (s.getType()) {
		case CARROT_ITEM:
			return "CARROT";
		case GOLDEN_APPLE:
			if (s.getDurability() == 0) return "GOLDEN_APPLE_REG";
			else if (s.getDurability() == 1) return "GOLDEN_APPLE_SPEC";
			break;
		case GRILLED_PORK:
			return "COOKED_PORK";
		case PORK:
			return "RAW_PORK";
		}
		
		return s.getType().name();
	}
}