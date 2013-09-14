package com.ttaylorr.uhc;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.ttaylorr.uhc.commands.ActiveFeaturesCommandExecutor;
import com.ttaylorr.uhc.commands.StatsFeatureCommandExecutor;
import com.ttaylorr.uhc.exceptions.FeatureException;
import com.ttaylorr.uhc.features.FeatureList;
import com.ttaylorr.uhc.features.core.BlockBreakFeature;
import com.ttaylorr.uhc.web.URLManager;

public class UltrahardcoreStats extends JavaPlugin {

	private static FeatureList features;
	private static UltrahardcoreStats instance;
	private static URLManager URLmanager;
	private static String API_KEY;
	private Logger log = Bukkit.getServer().getLogger();
	
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

		API_KEY = this.getConfig().getString("API_KEY") != null ? this.getConfig().getString("API_KEY") : "INVALID";
		
		this.loadDefaultModules();
		this.loadDefaultCommands();
		
	}
	
	protected void loadDefaultModules() {
		try {
			features.addFeature(new BlockBreakFeature(this.getConfig().getBoolean("features.ores.enabled")));
		} catch(FeatureException e) {
			e.printStackTrace();
		}
		// TODO: add more features here
	}
	
	protected void loadDefaultCommands() {
		getCommand("statsfeature").setExecutor(new StatsFeatureCommandExecutor(this));
		getCommand("activefeatures").setExecutor(new ActiveFeaturesCommandExecutor(this));
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

	public static String getAPI_KEY() {
		return API_KEY;
	}
}
