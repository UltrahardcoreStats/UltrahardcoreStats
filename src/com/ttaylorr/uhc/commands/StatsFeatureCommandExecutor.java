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

			if (feature != null) {
				try {
					if (args[1].equalsIgnoreCase("enable")) {
						feature.setEnabled();
					} else if (args[1].equalsIgnoreCase("disable")) {
						feature.setDisabled();
					} else {
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
