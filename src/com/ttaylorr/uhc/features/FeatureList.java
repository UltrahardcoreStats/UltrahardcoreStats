package com.ttaylorr.uhc.features;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.PluginManager;

import com.ttaylorr.uhc.UltrahardcoreStats;
import com.ttaylorr.uhc.exceptions.FeatureNotFoundException;

public class FeatureList {

	private UltrahardcoreStats plugin;
	private PluginManager pluginManager;
	private ArrayList<StatsFeature> features = new ArrayList<StatsFeature>();
	
	public FeatureList(UltrahardcoreStats plugin, PluginManager manager) {
		this.plugin = plugin;
		this.pluginManager = manager;
	}

	public StatsFeature getFeatureFor(String featureName) {
		for (StatsFeature feature : features) {
			if (feature.getTitle().equals(featureName)) {
				return feature;
			}
		}

		return null;
	}

	public List<StatsFeature> getFeatures() {
		return this.getFeatures();
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

	public void addFeature(StatsFeature feature) {
		try {
			if(feature.isEnabled()) {
				this.pluginManager.registerEvents(feature, UltrahardcoreStats.getInstance());
				this.getActiveFeatures().add(feature);
				this.plugin.getLogger().info("Succesfully enabled feature: " + feature.getTitle() + "!");
			}
		} catch (Exception e) {
			this.plugin.getLogger().warning("Unable to enable feature: " + feature.getTitle() + "!");
		}
	}
	
	public void removeFeature(StatsFeature feature) throws FeatureNotFoundException {
		try {
			if(feature.isEnabled()) {
				this.getActiveFeatures().remove(feature);
				this.plugin.getLogger().info("Succesfully disabled feature: " + feature.getTitle() + "!");
			} else {
				this.plugin.getLogger().warning("Tried to disable feature: " + feature.getTitle() + "(already disabled)...");
			}
		} catch(Exception e) {
			this.plugin.getLogger().warning("Unable to disable feature: " + feature.getTitle() + "!");
			throw new FeatureNotFoundException();
		}
	}

}
