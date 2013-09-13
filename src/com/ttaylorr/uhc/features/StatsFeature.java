package com.ttaylorr.uhc.features;

import org.bukkit.event.Listener;

import com.ttaylorr.uhc.UltrahardcoreStats;
import com.ttaylorr.uhc.exceptions.FeatureNotFoundException;

public class StatsFeature implements Listener {

	boolean enabled = false;
	String title;
	String description;
	
	public StatsFeature(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}
	
	protected void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	protected void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}

	protected StatsFeature setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}
	
	public void setEnabled() throws FeatureNotFoundException {
		UltrahardcoreStats.getFeatureList().addFeature(this);
	}
	
	public void setDisabled() throws FeatureNotFoundException {
		UltrahardcoreStats.getFeatureList().removeFeature(this);
	}
}
