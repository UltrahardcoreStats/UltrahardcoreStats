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
		for (StatsFeature foundFeature : this.features) {
			if (foundFeature.equals(feature)) {
				if (foundFeature.isEnabled()) {
					// Feature already enabled, nothing to do here...
					return;
				} else {
					try {
						foundFeature.setState(true);
						Bukkit.broadcastMessage(ChatColor.GOLD + "[Stats] - " + ChatColor.BLUE + "Feature " + ChatColor.GREEN + feature.getTitle() + ChatColor.BLUE + " has been globally enabled!");
					} catch (Exception e) {
						this.plugin.getLogger().warning(ChatColor.GREEN + "Unable to enable feature: " + feature.getTitle() + "!");
					}
					return;
				}
			}
		}

		// We haven't found the feature, so we add it to the list...
		this.features.add(feature);
		this.pluginManager.registerEvents(feature, UltrahardcoreStats.getInstance());
	}

	public void removeFeature(StatsFeature feature) throws FeatureException {
		for (StatsFeature foundFeature : this.features) {
			if (foundFeature.equals(feature)) {
				if (!foundFeature.isEnabled()) {
					// Already disabled, nothing to do here...
					return;
				} else {
					foundFeature.setState(false);
					Bukkit.broadcastMessage(ChatColor.GOLD + "[Stats] - " + ChatColor.BLUE + "Feature " + ChatColor.GREEN + feature.getTitle() + ChatColor.BLUE + " has been globally disabled!");

					return;
				}
			}
		}
	}
}
