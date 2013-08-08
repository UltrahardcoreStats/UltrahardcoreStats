package com.ttaylorr.uhc.stats;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Killstreak {

	Player player;
	int kills;
	
	public Killstreak(Player player, int kills) {
		this.player = player;
		this.kills = kills;
//		player.setLevel(this.getKills());
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}
	
	public void addKill() {
		this.kills++;
		player.sendMessage(ChatColor.GOLD + "[PVP] You're on a " + getKills() + " killstreak!" + ChatColor.RESET);
	}
	
}
