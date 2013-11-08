package com.ttaylorr.uhc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.ttaylorr.uhc.UltrahardcoreStats;
import com.ttaylorr.uhc.features.StatsFeature;

public class ActiveFeaturesCommandExecutor implements CommandExecutor {

	private UltrahardcoreStats plugin;

	public ActiveFeaturesCommandExecutor(UltrahardcoreStats plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		arg0.sendMessage("Active Feature(s): [" + plugin.getFeatureList().getFeatures().size() + "]");
		for (StatsFeature feature : plugin.getFeatureList().getFeatures()) {
			arg0.sendMessage(feature.getTitle() + ": " + feature.getDescription() + ", " + feature.isEnabled());
		}
		return true;
	}

}
