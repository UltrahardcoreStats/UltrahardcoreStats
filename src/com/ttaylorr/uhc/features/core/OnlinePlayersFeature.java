package com.ttaylorr.uhc.features.core;

import java.net.MalformedURLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.ttaylorr.uhc.UltrahardcoreStats;
import com.ttaylorr.uhc.features.StatsFeature;
import com.ttaylorr.uhc.web.KeyManager;
import com.ttaylorr.uhc.web.URLRunnable;

public class OnlinePlayersFeature extends StatsFeature {

	public OnlinePlayersFeature(boolean enabled) {
		super(enabled);
		this.setTitle("online-players");
		this.setDescription("Displays all players online on the website");
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) throws IllegalArgumentException, MalformedURLException {
		if (this.isEnabled()) {
			HashMap<String, String> args = new HashMap<String, String>();

			args.put("player", event.getPlayer().getName());
			args.put("hostname", event.getAddress().getHostAddress());
			
			Bukkit.getScheduler().runTaskAsynchronously(UltrahardcoreStats.getInstance(), new URLRunnable("login.php", args, true));
		}
	}
	
	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent event) throws IllegalArgumentException, MalformedURLException {
		if(this.isEnabled()) {
			HashMap<String, String> args = new HashMap<String, String>();
			
			args.put(KeyManager.player.name(), event.getPlayer().getName());
			args.put(KeyManager.hostname.name(), event.getPlayer().getAddress().getAddress().getHostAddress());
			
			Bukkit.getScheduler().runTaskAsynchronously(UltrahardcoreStats.getInstance(), new URLRunnable("logout.php", args, true));
		}
	}

}
