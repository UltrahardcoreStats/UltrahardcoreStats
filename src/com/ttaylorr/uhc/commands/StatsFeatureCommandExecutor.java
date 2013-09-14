package com.ttaylorr.uhc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.ttaylorr.uhc.UltrahardcoreStats;
import com.ttaylorr.uhc.exceptions.FeatureException;
import com.ttaylorr.uhc.exceptions.FeatureNotFoundException;
import com.ttaylorr.uhc.features.StatsFeature;

public class StatsFeatureCommandExecutor implements CommandExecutor {

	private UltrahardcoreStats plugin;

	public StatsFeatureCommandExecutor(UltrahardcoreStats plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length != 2) return false;
		
		else {
			StatsFeature feature = plugin.getFeatureList().getFeatureFor(args[0]);

			if(feature != null) {
				System.out.println("feature not null");
				try {
					if (args[1].equalsIgnoreCase("enable")) {
						System.out.println("tyring to enable feature");
						feature.setEnabled();
					} else if (args[1].equalsIgnoreCase("disable")) {
						System.out.println("tyring to disable feature");
						feature.setDisabled();
					} else {
						System.out.println("o fuck");
						return false;
					}
				} catch (FeatureException e) {
					e.printStackTrace();
				}
			} else {
				sender.sendMessage("Could not find feature: " + args[0]);
			}
			

			return true;
		}
	}

}
