package com.ttaylorr.uhc.stats;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathHandler implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDeath(final PlayerDeathEvent event) throws MalformedURLException {

		ArrayList<String> args = new ArrayList<String>();
		args.add(event.getEntity().getName());
		args.add(Long.toString(System.currentTimeMillis()));

		boolean pvptype = true;

		if (WebInterface.isUSE_MULTIVERSE()) {
			if (event.getEntity().getWorld().getName().equals(WebInterface.getMULTIVERSE_NAME())) pvptype = true;
			else pvptype = false;
		} else {
			if (WebInterface.getDEFAULT_KILL_TYPE().equals("uhc")) pvptype = false;
			else if (WebInterface.getDEFAULT_KILL_TYPE().equals("pvp")) pvptype = true;
		}
		
		args.add("" + pvptype);

		if(WebInterface.getUsingJuggernaut() && event.getEntity().hasMetadata("juggernaut-playing")) args.set(2, "juggernaut");
		
		// System.out.println(args.get(2));

		// if (event.getEntity().getWorld().getName().equals("spawn"))
		// args.add("true");
		// else args.add("false");

		boolean isEntity = true;

		for (DamageCause cause : EntityDamageEvent.DamageCause.values()) {
			if (getKillerName(event).equals(cause.name())) {
				if (cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
					// event.getEntity().sendMessage("it's an entity!");
					isEntity = true;
					break;
				} else {
					// event.getEntity().sendMessage("it's not an entity!");
					isEntity = false;
					args.add("false");
					args.add("0");
					args.add(cause.name());
					break;
				}
			}
		}

		if (isEntity) {
			args.add("true");
			args.add(getKillerName(event));
			// args.add(event.getEntity().getKiller().getDisplayName());

			try {
				args.add(event.getEntity().getKiller().getInventory().getItemInHand().getType().name());
			} catch (Exception e) {
				args.add("NO_ITEM");
			}

		}

		args.add("" + event.getEntity().getLocation().getX());
		args.add("" + event.getEntity().getLocation().getY());
		args.add("" + event.getEntity().getLocation().getZ());
		
		if(event.getEntity().getKiller() instanceof Player) { // isEntity
			args.add("" + (event.getEntity().getKiller()).getLocation().getX());
			args.add("" + (event.getEntity().getKiller()).getLocation().getY());
			args.add("" + (event.getEntity().getKiller()).getLocation().getZ());
			args.add("" + event.getEntity().getKiller().getHealth());			
		} else {
			for(int i = 0; i < 4; i++) {
				args.add("null");
			}
		}
		
		/*
		 * ARGS: the player's name that died the time of the event if the killer
		 * was an entity (true if entity) the killer's name (0 if enviroment)
		 * the cause or the item in hand
		 */

		try {
			URL playerDeathURL = new URL("http://uhc.ttaylorr.com/stats_php/death.php?player=" + args.get(0) + "&entity=" + args.get(3) + "&killer=" + args.get(4) + "&item=" + args.get(5) + "&pvp=" + args.get(2) + "&api=" + WebInterface.getAPI_KEY() + "&x1=" + args.get(6)+ "&y1=" + args.get(7)+ "&z1=" + args.get(8)+ "&x2=" + args.get(9)+ "&y2=" + args.get(10)+ "&z2=" + args.get(11) + "&player_health=" + args.get(12));
			Bukkit.getScheduler().runTaskAsynchronously(WebInterface.getInstance(), new URLRunnable(playerDeathURL));
		} catch (Exception e) {
		}
	}

	private String getKillerName(PlayerDeathEvent playerDeathEvent) {
		Player player = playerDeathEvent.getEntity();
		if (player.getKiller() != null) {
			if (player.getKiller().equals(playerDeathEvent.getEntity())) {
				return "SELF_KILL";
			}
			// return player.getKiller().getName();
			// return ChatColor.stripColor(player.getKiller().getName());
			return player.getKiller().getName();
		}
		EntityDamageEvent entityDamageEvent = player.getLastDamageCause();
		if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent entityDamageByEvent = (EntityDamageByEntityEvent) entityDamageEvent;
			Entity damager = entityDamageByEvent.getDamager();
			if (damager instanceof Projectile) {
				damager = ((Projectile) damager).getShooter();
			}
			if (damager != null) {
				return getEntityNameFor(damager.getType());
			} else {
				return "HEROBRINE";
			}
		}
		return getDamageNameFor(entityDamageEvent.getCause());
	}

	private String getEntityNameFor(EntityType damager) {

		if (!damager.getName().equals(null)) {
			return damager.getName().toUpperCase();
		} else {
			return "NULL";
		}
	}

	private String getDamageNameFor(DamageCause cause) {
		if (!cause.name().equals(null)) {
			return cause.name();
		} else {
			return "null";
		}
	}
}
