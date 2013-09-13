package com.ttaylorr.uhc;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.ttaylorr.uhc.features.FeatureList;
import com.ttaylorr.uhc.features.core.BlockBreakFeature;
import com.ttaylorr.uhc.web.URLManager;

public class UltrahardcoreStats extends JavaPlugin {

	private static FeatureList features;
	private static UltrahardcoreStats instance;
	private static URLManager URLmanager;
	private Logger log = this.getServer().getLogger();
	
	public void onDisable() {
		instance = null;
	}

	public void onEnable() {
		instance = this;
		
		features = new FeatureList(this, this.getServer().getPluginManager());
		URLmanager = new URLManager();
		
		this.saveDefaultConfig();
		this.getConfig().options().copyDefaults(true);
		
		features.getFeatures().clear();

		this.loadDefaultModules();
		this.loadDefaultCommands();
		
	}
	
	protected void loadDefaultModules() {
		features.addFeature(new BlockBreakFeature(this.getConfig().getBoolean("features.ores.enabled")));
		// TODO: add more features here
	}
	
	protected void loadDefaultCommands() {
		// TODO: add commands here
	}
	
	public static UltrahardcoreStats getInstance() {
		return instance;
	}
	
	public static FeatureList getFeatureList() {
		return features;
	}
	
	public static URLManager getURLManager() {
		return URLmanager;
	}
}
