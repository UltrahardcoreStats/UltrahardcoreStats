package com.ttaylorr.uhc.stats;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProjectileLaunchHandler implements Listener {

	@EventHandler
	public void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
		if (event.getEntity().getShooter() instanceof Player && event.getEntityType() == EntityType.ARROW) {
			final UUID uuid = event.getEntity().getUniqueId();
			final Player shooter = (Player) event.getEntity().getShooter();

			WebInterface.getLiveArrows().add(event.getEntity().getUniqueId());

			Bukkit.getScheduler().scheduleSyncDelayedTask(WebInterface.getInstance(), new Runnable() {

				@Override
				public void run() {
					if (WebInterface.getLiveArrows().contains(uuid)) {
						for (PlayerProjectileTracker pt : WebInterface.getPlayerProjectiles()) {
							if (pt.getShooter().equals(shooter)) {
								pt.setMissed(pt.getMissed() + 1);
								break;
							}
						}
						WebInterface.getLiveArrows().remove(uuid);
					}
				}

			}, 10 * 20);
		}
	}

}
