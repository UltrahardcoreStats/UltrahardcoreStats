package com.ttaylorr.uhc.features;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;

import com.ttaylorr.uhc.UltrahardcoreStats;
import com.ttaylorr.uhc.exceptions.FeatureException;
import com.ttaylorr.uhc.exceptions.FeatureNotFoundException;

public class FeatureList {

	private UltrahardcoreStats plugin;
	private PluginManager pluginManager;
	public ArrayList<StatsFeature> features = new ArrayList<StatsFeature>();

	public FeatureList(UltrahardcoreStats plugin, PluginManager manager) {
		this.plugin = plugin;
		this.pluginManager = manager;
	}

	public StatsFeature getFeatureFor(String featureName) {
		for (StatsFeature feature : features) {
			this.plugin.getLogger().info("Found feature: " + feature.getTitle());
			if (feature.getTitle().equalsIgnoreCase(featureName.trim())) {
				return feature;
			}
		}

		return null;
	}

	public List<StatsFeature> getFeatures() {
		return this.features;
	}

	public List<StatsFeature> getActiveFeatures() {
		ArrayList<StatsFeature> l = new ArrayList<StatsFeature>();
		for (StatsFeature feature : features) {
			if (feature.isEnabled()) {
				l.add(feature);
			}
		}

		return l;
	}

	public void addFeature(StatsFeature feature) throws FeatureNotFoundException {
		// Bukkit.getPlayer("ttaylorr").sendMessage("Adding feature: " +
		// feature.getTitle() + " with state: " + feature.isEnabled());

		for (StatsFeature foundFeature : this.features) {
			if (foundFeature.equals(feature)) {
				if (foundFeature.isEnabled()) {
					System.out.println("Feature already enabled!");
					return;
				} else {
					try {
						foundFeature.setState(true);
						this.pluginManager.registerEvents(feature, UltrahardcoreStats.getInstance());
						this.plugin.getLogger().info(ChatColor.GREEN + "Succesfully enabled feature: " + feature.getTitle() + "!");
					} catch (Exception e) {
						this.plugin.getLogger().warning(ChatColor.GREEN + "Unable to enable feature: " + feature.getTitle() + "!");
					} 
					return;
				}
			}
		}
		
		//We haven't found the feature, so we add it to the list...
		this.features.add(feature);
	}

	public void removeFeature(StatsFeature feature) throws FeatureException {
		for(StatsFeature foundFeature : this.features) {
			if(foundFeature.equals(feature)) {
				if(!foundFeature.isEnabled()) {
					System.out.println("Feature already disabled!");
					return;
				} else {
					foundFeature.setState(false);
					this.plugin.getLogger().info(ChatColor.GREEN + "Succesfully disabled feature: " + feature.getTitle() + "!");
				
					return;
				}
			}
		}
	}
	
//	public void removeFeature(StatsFeature feature) throws FeatureNotFoundException {
//		try {
//			if (feature.isEnabled()) {
//				this.getActiveFeatures().remove(feature);
//				this.plugin.getLogger().info(ChatColor.RED + "Succesfully disabled feature: " + feature.getTitle() + "!");
//			} else {
//				this.plugin.getLogger().warning(ChatColor.RED + "Tried to disable feature: " + feature.getTitle() + "(already disabled)...");
//			}
//		} catch (Exception e) {
//			this.plugin.getLogger().warning(ChatColor.RED + "Unable to disable feature: " + feature.getTitle() + "!");
//			throw new FeatureNotFoundException();
//		}
//	}

}
