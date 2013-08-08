package com.ttaylorr.uhc.stats;

import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerKillstreakHandler implements Listener {

	@EventHandler
	public void onPlayerDeath(final PlayerDeathEvent event) {
		if(event.getEntity() instanceof Player) {
			for (Killstreak k : WebInterface.getKillstreaks()) {
				if(k.getPlayer().equals(event.getEntity())) {
					k.setKills(0);
					break;
				}
			}
		}
		
		if (event.getEntity().getKiller() instanceof Player) {
			boolean hasKillstreak = false;
			Killstreak ks = null;
			
			for (Killstreak k : WebInterface.getKillstreaks()) {
				if (k.getPlayer().equals(event.getEntity().getKiller())) {
					ks = k;  
					hasKillstreak = true;
					break;
				}
			}
			
			if(!hasKillstreak) {
				WebInterface.getKillstreaks().add(new Killstreak(event.getEntity().getKiller(), 1));
				try {
					URL playerKilstreakURL = new URL("http://uhc.ttaylorr.com/stats_php/killstreak.php?api=" + WebInterface.getAPI_KEY() + "&player=" + event.getEntity().getKiller().getName() + "&ks=1");
					Bukkit.getScheduler().scheduleAsyncDelayedTask(WebInterface.getInstance(), new URLRunnable(playerKilstreakURL));
				} catch (Exception e) {

				}
			} else {
				ks.addKill();
				try {
					URL playerKilstreakURL = new URL("http://uhc.ttaylorr.com/stats_php/killstreak.php?api=" + WebInterface.getAPI_KEY() + "&player=" + event.getEntity().getKiller().getName() + "&ks=" + ks.getKills());
					Bukkit.getScheduler().scheduleAsyncDelayedTask(WebInterface.getInstance(), new URLRunnable(playerKilstreakURL));
				} catch (Exception e) {

				}

			}
		}

	}

}
