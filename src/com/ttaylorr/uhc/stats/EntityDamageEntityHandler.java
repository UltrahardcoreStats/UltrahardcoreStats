package com.ttaylorr.uhc.stats;

//import org.bukkit.Bukkit;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageEntityHandler implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDamageEvent(EntityDamageByEntityEvent event) {
		// Bukkit.getServer().broadcastMessage("EDBEE fired!");
		if ((event.getDamager().getType() == EntityType.ARROW) /*
																 * &&
																 * (event.getEntity
																 * ().getType()
																 * ==
																 * EntityType.
																 * PLAYER)
																 */) {
			if (WebInterface.getLiveArrows().contains(event.getDamager().getUniqueId())) {
				// Bukkit.getServer().broadcastMessage("Arrow fired is live");
				for (PlayerProjectileTracker pt : WebInterface.getPlayerProjectiles()) {
					if (pt.getShooter().equals(((Player) (((Projectile) event.getDamager()).getShooter())))) {
						// Bukkit.getServer().broadcastMessage("found shooter");
						WebInterface.getLiveArrows().remove(((Projectile) event.getDamager()).getUniqueId());
						// Bukkit.getServer().broadcastMessage("Shooter: " +
						// ((Player)((Projectile)event.getDamager()).getShooter()).getDisplayName());
						pt.setHit(pt.getHit() + 1);
						// Bukkit.getServer().broadcastMessage("Updated hit count");
						break;
					}
				}
			}
		}
	}

	@EventHandler
	public void onArrowLand(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player) {
			Arrow arrow = (Arrow) event.getDamager();

			if (arrow.getShooter() != null && arrow.getShooter() instanceof Player) {
				int dist = distance(event.getEntity().getLocation(), arrow.getShooter().getLocation());

				if (dist >= 50) {
					if(WebInterface.getANNOUNCE_LONGSHOTS()) {
						Bukkit.broadcastMessage(ChatColor.GOLD + "[Stats] - " + ChatColor.GREEN + ((Player) arrow.getShooter()).getName() + ChatColor.BLUE + " got a longshot of " + ChatColor.GREEN + dist + ChatColor.BLUE + " blocks!" + ChatColor.RESET);						
					
						final Player shooter = (Player) arrow.getShooter();
						final Player target = (Player) event.getEntity();
						
						try {
							URL playerLongshotURL = new URL("http://uhc.ttaylorr.com/stats_php/longshot.php?api=" + WebInterface.getAPI_KEY() + "&shooter=" + shooter.getName() + "&target=" + target.getName() + "&dist=" + dist);
							Bukkit.getScheduler().scheduleAsyncDelayedTask(WebInterface.getInstance(), new URLRunnable(playerLongshotURL));
						} catch (Exception e) {

						}

					}
				}
			}
		}
	}

	private int distance(Location l1, Location l2) {
		double x1 = l1.getX();
		double y1 = l1.getY();
		double z1 = l1.getZ();
		double x2 = l2.getX();
		double y2 = l2.getY();
		double z2 = l2.getZ();

		return (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
	}

}
