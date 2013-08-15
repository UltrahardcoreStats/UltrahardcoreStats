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
		if(WebInterface.getANNOUNCE_KILLSTREAK()) {
			this.getPlayer().sendMessage(ChatColor.GOLD + "[PVP] - " + ChatColor.BLUE + "You're on a " + ChatColor.GREEN + this.getKills() + ChatColor.BLUE + " killstreak!" + ChatColor.RESET);			
		}
	}
	
}
