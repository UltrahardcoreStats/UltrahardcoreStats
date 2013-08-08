package com.ttaylorr.uhc.stats;

//import org.bukkit.Bukkit;
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
//		Bukkit.getServer().broadcastMessage("EDBEE fired!");
		if ((event.getDamager().getType() == EntityType.ARROW) /*&& (event.getEntity().getType() == EntityType.PLAYER)*/) {
			if(WebInterface.getLiveArrows().contains(event.getDamager().getUniqueId())) {
//				Bukkit.getServer().broadcastMessage("Arrow fired is live");
				for (PlayerProjectileTracker pt : WebInterface.getPlayerProjectiles()) {
					if(pt.getShooter().equals(((Player)(((Projectile)event.getDamager()).getShooter())))) {
//						Bukkit.getServer().broadcastMessage("found shooter");
						WebInterface.getLiveArrows().remove(((Projectile)event.getDamager()).getUniqueId());
//						Bukkit.getServer().broadcastMessage("Shooter: " + ((Player)((Projectile)event.getDamager()).getShooter()).getDisplayName());
						pt.setHit(pt.getHit() + 1);
//						Bukkit.getServer().broadcastMessage("Updated hit count");
						break;
					}
				}
			}
		}
	}

}
