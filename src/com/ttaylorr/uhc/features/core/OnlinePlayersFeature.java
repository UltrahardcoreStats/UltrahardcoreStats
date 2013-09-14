package com.ttaylorr.uhc.features.core;

import java.net.MalformedURLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;

import com.ttaylorr.uhc.UltrahardcoreStats;
import com.ttaylorr.uhc.features.StatsFeature;
import com.ttaylorr.uhc.web.URLRunnable;

public class OnlinePlayersFeature extends StatsFeature {

	public OnlinePlayersFeature(boolean enabled) {
		super(enabled);
		this.setTitle("Online Players Feature");
		this.setDescription("Displays all players online on the website");
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) throws IllegalArgumentException, MalformedURLException {
		if(this.isEnabled()) {
			HashMap<String, String> args = new HashMap<String, String>();
			args.put("player", "ttaylorr");
			
			Bukkit.getScheduler().runTaskAsynchronously(UltrahardcoreStats.getInstance(), new URLRunnable("login.php",args,false));
		}
	}

}
