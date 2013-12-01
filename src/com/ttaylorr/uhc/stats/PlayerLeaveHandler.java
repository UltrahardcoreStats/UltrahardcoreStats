package com.ttaylorr.uhc.stats;

import java.net.URL;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveHandler implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(final PlayerQuitEvent event) throws Exception {
		ArrayList<String> args = new ArrayList<String>();
		args.add(event.getPlayer().getName());
		args.add(event.getPlayer().getAddress().getAddress().getHostAddress());
		
		try {
			URL playerExitURL = new URL("http://uhc.ttaylorr.com/stats_php/logout.php?api=" + WebInterface.getAPI_KEY() + "&player=" + args.get(0) + "&hostname=" + args.get(1));
			Bukkit.getScheduler().runTaskAsynchronously(WebInterface.getInstance(), new URLRunnable(playerExitURL));
		} catch (Exception e) {

		}
		
		for(PlayerProjectileTracker p : WebInterface.getPlayerProjectiles()) {
			if(p.getShooter().equals(event.getPlayer())) {
				WebInterface.getPlayerProjectiles().remove(p);
				break;
			}
		}
	}

}
