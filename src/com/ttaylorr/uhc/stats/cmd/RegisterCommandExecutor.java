package com.ttaylorr.uhc.stats.cmd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ttaylorr.uhc.stats.WebInterface;

public class RegisterCommandExecutor implements CommandExecutor {

	private WebInterface webInterface;
	private ArrayList<String> args = new ArrayList<String>();
	
	public RegisterCommandExecutor(WebInterface plugin) {
		this.webInterface = plugin;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] cmd_args) {
		if (sender instanceof Player) {
			final Player p = (Player) sender;

			if (cmd_args.length == 1) {
				try {
					final URL registerEmail = new URL("http://uhc.ttaylorr.com/stats_php/commands/register_email.php?email=" + cmd_args[0] + "&player=" + p.getName() + "&api=" + WebInterface.getAPI_KEY());
					
					Bukkit.getScheduler().runTaskAsynchronously(WebInterface.getInstance(), new Runnable(){
						@Override
						public void run() {
							try {
								URLConnection connection = registerEmail.openConnection();
								
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
				} catch (Exception e) {
					e.printStackTrace();
				}
	
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}
