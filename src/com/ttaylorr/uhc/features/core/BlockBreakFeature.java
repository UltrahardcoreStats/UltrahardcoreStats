package com.ttaylorr.uhc.features.core;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import com.ttaylorr.uhc.features.StatsFeature;

public class BlockBreakFeature extends StatsFeature {

	public BlockBreakFeature(boolean enabled) {
		super(enabled);
		this.setTitle("Ore Break Listener");
		this.setDescription("Communicates all ores being broken to the webserver...");
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (this.isEnabled()) {

		}
	}
}
