package com.ttaylorr.uhc.stats.cmd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ttaylorr.uhc.stats.WebInterface;

public class ReportCommandExecutor implements CommandExecutor {

	private WebInterface plugin;
	private ArrayList<String> args = new ArrayList<String>();	
	
	public ReportCommandExecutor(WebInterface plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] cmd_args) {
		args.clear();
		
		if(sender instanceof Player) {
			final Player p = (Player) sender;
			
			if(cmd_args.length < 2) {
				return false;
			} else {
				String reason = "";
				for(int i=1; i < cmd_args.length; i++) {
					if(i != cmd_args.length-1) reason += cmd_args[i] + "_";
					else reason += cmd_args[i];
				}
				
				args.add(p.getName());
				args.add(cmd_args[0]);
				args.add(reason);

				try {
					final URL reportURL = new URL("http://uhc.ttaylorr.com/stats_php/commands/report.php?reporter=" + args.get(0) + "&subject=" + args.get(1) + "&reason=" + args.get(2) + "&api=" + WebInterface.getAPI_KEY());
					
					Bukkit.getScheduler().runTaskAsynchronously(WebInterface.getInstance(), new Runnable(){
						@Override
						public void run() {
							try {
								URLConnection connection = reportURL.openConnection();
								
								BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
								String inputLine;
								
								while((inputLine = in.readLine()) != null) {
									p.sendMessage(inputLine);
								}
								
								in.close();
							} catch (Exception f) {
								f.printStackTrace();
							}
							
						}
					});
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				return true;
			}
		}
		return false;
	}
}
