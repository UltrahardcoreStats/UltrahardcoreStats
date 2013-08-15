package com.ttaylorr.uhc.stats.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ttaylorr.uhc.stats.Killstreak;
import com.ttaylorr.uhc.stats.WebInterface;

public class KillstreakCommandExecutor implements CommandExecutor {

	WebInterface webInterface;
	
	public KillstreakCommandExecutor(WebInterface webInterface) {
		this.webInterface = webInterface;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] cmd_args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			boolean hasKillstreak = false;
			
			for(Killstreak k : WebInterface.getKillstreaks()) {
				if(k.getPlayer().equals(p)) {
					hasKillstreak = true;
					p.sendMessage(ChatColor.GOLD + "[PVP] - " + ChatColor.BLUE + "You're on a " + ChatColor.GREEN + k.getKills() + ChatColor.BLUE + " killstreak!" + ChatColor.RESET);			
					break;
				}
			}
			
			if(!hasKillstreak) {
				p.sendMessage(ChatColor.GOLD + "[PVP] - " + ChatColor.BLUE + "You don't currently have a killstreak!" + ChatColor.RESET);			
			}
			
			return true;
		}
		return false;
	}
	
}
