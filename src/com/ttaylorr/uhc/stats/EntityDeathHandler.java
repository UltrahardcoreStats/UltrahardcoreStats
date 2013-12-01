package com.ttaylorr.uhc.stats;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathHandler implements Listener {

	Player killer = null;
	String weaponType = null;
	String entityType = null;
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDeath(final EntityDeathEvent event) {
		if(event.getEntity() instanceof Player) {
			((Player)event.getEntity()).setLevel(0);
		}
		
		if (event.getEntityType() != EntityType.PLAYER) {
			
			if (event.getEntity().getKiller() != null) {
				killer = event.getEntity().getKiller();
				switch(killer.getItemInHand().getType()) {
				case AIR:
					weaponType = "HAND";
					break;
				default:
					weaponType = killer.getItemInHand().getType().name();
					break;
				}
			} else return;
			
			if (event.getEntityType() != null) {
				entityType = event.getEntityType().name();
			} else return;
			
			try {
	//			URL playerDeathURL = new URL("http://localhost/projects/uhc_server/death.php?player=" + args.get(0) + "&killer=" + args.get(1) + "&time=" + args.get(2));
	//			Bukkit.getScheduler().scheduleAsyncDelayedTask(WebInterface.getInstance(), new URLRunnable(playerDeathURL));				
			} catch(Exception e) {
				
			}
		}
		
	}
}
