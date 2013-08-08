package com.ttaylorr.uhc.stats;

//import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginHandler implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerLogin(final PlayerLoginEvent event) throws Exception {

		final ArrayList<String> args = new ArrayList<String>();
		args.add(event.getPlayer().getName());
		args.add(event.getAddress().getHostAddress());
		args.add(Long.toString(System.currentTimeMillis()));

		if (event.getResult() == PlayerLoginEvent.Result.ALLOWED) {
			try {
				URL playerEntryURL = new URL("http://uhc.ttaylorr.com/stats_php/login.php?api=" + WebInterface.getAPI_KEY() + "&player=" + args.get(0) + "&hostname=" + args.get(1) + "&time=" + args.get(2));
				Bukkit.getScheduler().scheduleAsyncDelayedTask(WebInterface.getInstance(), new URLRunnable(playerEntryURL));
			} catch (Exception e) {

			}
		}
		
		boolean hasTracker = false;
		
		for(PlayerProjectileTracker p : WebInterface.getPlayerProjectiles()) {
			if(p.getShooter().equals(event.getPlayer())) {
				hasTracker = true;
				break;
			}
		}
		
		if(!hasTracker) {
			WebInterface.getPlayerProjectiles().add(new PlayerProjectileTracker(event.getPlayer(), 0, 0));
		}
	}
}