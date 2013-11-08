package com.ttaylorr.uhc.features.core;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import com.ttaylorr.uhc.UltrahardcoreStats;
import com.ttaylorr.uhc.features.StatsFeature;
import com.ttaylorr.uhc.web.KeyManager;
import com.ttaylorr.uhc.web.URLRunnable;

public class FoodConsumeFeature extends StatsFeature {

	static ArrayList<Material> valuables;
	
	static {
		valuables = new ArrayList<Material>();
		
		valuables.add(Material.APPLE);
		valuables.add(Material.BREAD);
		valuables.add(Material.CARROT_ITEM);
		valuables.add(Material.COOKED_BEEF);
		valuables.add(Material.COOKED_CHICKEN);
		valuables.add(Material.COOKED_FISH);
		valuables.add(Material.COOKIE);
		valuables.add(Material.GOLDEN_APPLE);
		valuables.add(Material.GOLDEN_CARROT);
		valuables.add(Material.GRILLED_PORK);
		valuables.add(Material.MELON);
		valuables.add(Material.MUSHROOM_SOUP);
		valuables.add(Material.POISONOUS_POTATO);
		valuables.add(Material.PORK);
		valuables.add(Material.POTATO_ITEM);
		valuables.add(Material.PUMPKIN_PIE);
		valuables.add(Material.RAW_BEEF);
		valuables.add(Material.RAW_CHICKEN);
		valuables.add(Material.RAW_FISH);
		valuables.add(Material.ROTTEN_FLESH);
	}
	
	public FoodConsumeFeature(boolean enabled) {
		super(enabled);
		this.setTitle("food-consumed");
		this.setDescription("Monitors food being eaten by a player");
	}
	
	@EventHandler
	public void onPlayerConsumeItem(PlayerItemConsumeEvent event) throws IllegalArgumentException, MalformedURLException {
		if(this.isEnabled()) {
			if(valuables.contains(event.getItem().getType())) {
				HashMap<String, String> args = new HashMap<String, String>();
				
				args.put(KeyManager.player.name(), event.getPlayer().getName());
				args.put(KeyManager.food_type.name(), event.getItem().getType().name());
				
				Bukkit.getScheduler().runTaskAsynchronously(UltrahardcoreStats.getInstance(), new URLRunnable("food_item.php", args, true));
			}
		}
	}

}
