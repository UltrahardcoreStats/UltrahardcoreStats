package com.ttaylorr.uhc.stats;

import org.bukkit.entity.Player;

public class PlayerProjectileTracker {

	Player shooter;
	int missed;
	int hit;
	
	public PlayerProjectileTracker(Player shooter, int hit, int missed) {
		this.shooter = shooter;
		this.hit = hit;
		this.missed = missed;
	}

	public Player getShooter() {
		return shooter;
	}

	public void setShooter(Player shooter) {
		this.shooter = shooter;
	}

	public int getMissed() {
		return missed;
	}

	public void setMissed(int missed) {
		this.missed = missed;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}
	
}
