package com.ttaylorr.uhc.stats;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.ttaylorr.uhc.stats.cmd.KillstreakCommandExecutor;
import com.ttaylorr.uhc.stats.cmd.RegisterCommandExecutor;
import com.ttaylorr.uhc.stats.cmd.ReportCommandExecutor;

public class WebInterface extends JavaPlugin {

	public final static Logger logger = Logger.getLogger("Minecraft");
	public static PluginManager pluginManager = Bukkit.getServer().getPluginManager();
	public final PlayerLoginHandler loginHandler = new PlayerLoginHandler();

	public static ArrayList<Killstreak> killstreaks = new ArrayList<Killstreak>();

	public final static ArrayList<Material> VALUABLE_ORES = new ArrayList<Material>();
	public final static ArrayList<Material> VALUABLE_CONSUMABLES = new ArrayList<Material>();
	public final static ArrayList<ItemStack> VALUABLE_CRAFTABLES = new ArrayList<ItemStack>();

	public static ArrayList<UUID> liveArrows = new ArrayList<UUID>();
	public static ArrayList<PlayerProjectileTracker> playerProjectiles = new ArrayList<PlayerProjectileTracker>();

	private static WebInterface instance;

	private static String API_KEY = "";
	private static boolean USE_MULTIVERSE = false;
	private static boolean USE_JUGGERNAUT = false;
	private static String MULTIVERSE_NAME = "";
	private static String DEFAULT_KILL_TYPE = "";
	private static boolean ANNOUNCE;
	private static boolean ANNOUNCE_LONGSHOTS = false;;
	private static boolean ANNOUNCE_KILLSTREAK = false;;
	
	private URL serverStopURL = null;

	public static WebInterface getInstance() {
		return instance;
	}

	public static ArrayList<Killstreak> getKillstreaks() {
		return killstreaks;
	}

	public static ArrayList<PlayerProjectileTracker> getPlayerProjectiles() {
		return playerProjectiles;
	}

	public static ArrayList<UUID> getLiveArrows() {
		return liveArrows;
	}

	public final static ArrayList<Material> getValuableOres() {
		return VALUABLE_ORES;
	}

	public final static ArrayList<Material> getValuableConsumables() {
		return VALUABLE_CONSUMABLES;
	}

	public final static ArrayList<ItemStack> getValuableCraftables() {
		return VALUABLE_CRAFTABLES;
	}

	public final static String getAPI_KEY() {
		return API_KEY;
	}

	public static boolean isUSE_MULTIVERSE() {
		return USE_MULTIVERSE;
	}

	public static String getMULTIVERSE_NAME() {
		return MULTIVERSE_NAME;
	}

	public static boolean getANNOUNCE() {
		return ANNOUNCE;
	}

	public static boolean getANNOUNCE_LONGSHOTS() {
		return ANNOUNCE_LONGSHOTS;
	}

	public static boolean getANNOUNCE_KILLSTREAK() {
		return ANNOUNCE_KILLSTREAK;
	}

	public static String getDEFAULT_KILL_TYPE() {
		return DEFAULT_KILL_TYPE;
	}

	public static boolean getUsingJuggernaut() {
		return USE_JUGGERNAUT;
	}

	@Override
	public void onDisable() {
	
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		instance = this;
		
		this.saveDefaultConfig();
		this.getConfig().options().copyDefaults(true);

		API_KEY = getConfig().getString("api_key");
		USE_MULTIVERSE = getConfig().getString("use_multiverse").equals("true") ? true : false;
		MULTIVERSE_NAME = getConfig().getString("pvp_world_name");
		DEFAULT_KILL_TYPE = getConfig().getString("kill_default");
//		USE_JUGGERNAUT = getConfig().getString("use_juggernaut").equals("true") ? true : false;
		USE_JUGGERNAUT = false;
		ANNOUNCE_LONGSHOTS = getConfig().getString("announce_longshots").equalsIgnoreCase("true") ? true : false;
		ANNOUNCE_KILLSTREAK = getConfig().getString("announce_killstreak").equalsIgnoreCase("true") ? true : false;
		ANNOUNCE = getConfig().getString("stats_announce").equals("true") ? true : false;
		
		try {
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new URLRunnable(new URL("http://uhc.ttaylorr.com/stats_php/logout_all.php?api=" + this.getAPI_KEY())));
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			try {
				URL playerEntryURL = new URL("http://uhc.ttaylorr.com/stats_php/login.php?api=" + WebInterface.getAPI_KEY() + "&player=" + p.getName() + "&hostname=" + p.getAddress().getAddress().getHostAddress());
				Bukkit.getScheduler().runTaskAsynchronously(this, new URLRunnable(playerEntryURL));
			} catch (MalformedURLException e2) {
				e2.printStackTrace();
			}
		}
		
		if(ANNOUNCE) {
			Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
				@Override
				public void run() {
					Player[] players = Bukkit.getOnlinePlayers();
					
					for(Player p : players) {
						p.sendMessage(ChatColor.GOLD + "[Stats] - " + ChatColor.GRAY + ChatColor.ITALIC + "See your stats at " + ChatColor.GOLD + "http://uhc.ttaylorr.com/p/" + p.getName() + ChatColor.RESET);
					}
				}
			}, 0, 15*60*20);
		}
		
		if (API_KEY.equals("YOUR_API_HERE")) {
			logger.severe("[UHC Stats] You have not set an API key!");
		}

		getCommand("register").setExecutor(new RegisterCommandExecutor(this));
		getCommand("report").setExecutor(new ReportCommandExecutor(this));
		getCommand("killstreak").setExecutor(new KillstreakCommandExecutor(this));

		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			playerProjectiles.add(new PlayerProjectileTracker(p, 0, 0));
			// try {
			// URL playerEntryURL = new
			// URL("http://uhc.ttaylorr.com/stats_php/login.php?api=" +
			// WebInterface.getAPI_KEY() + "&player=" + args.get(0) +
			// "&hostname=" + args.get(1) + "&time=" + args.get(2));
			// Bukkit.getScheduler().scheduleAsyncDelayedTask(WebInterface.getInstance(),
			// new URLRunnable(playerEntryURL));
			// } catch (Exception e) {
			//
			// }

		}

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				final PlayerProjectileTracker web_pt;
				// getServer().broadcastMessage("Attempting to update with arrow data collected...");

				for (PlayerProjectileTracker pt : WebInterface.getPlayerProjectiles()) {
					if (pt.getHit() != 0 || pt.getMissed() != 0) {
						// getServer().broadcastMessage("Update loop for: " +
						// pt.getShooter().getDisplayName());
						try {
							URL playerProjectileUpdateURL = new URL("http://uhc.ttaylorr.com/stats_php/shots_fired.php?player=" + pt.getShooter().getName() + "&hit=" + pt.getHit() + "&missed=" + pt.getMissed() + "&api=" + WebInterface.getAPI_KEY());
							// getServer().broadcastMessage("http://uhc.ttaylorr.com/stats_php/shots_fired.php?player="+pt.getShooter().getDisplayName()
							// + "&hit=" + pt.getHit() + "&missed=" +
							// pt.getMissed() + "&api=" +
							// WebInterface.getAPI_KEY());
							Bukkit.getScheduler().runTaskAsynchronously(WebInterface.getInstance(), new URLRunnable(playerProjectileUpdateURL));

							pt.setHit(0);
							pt.setMissed(0);
						} catch (Exception e) {

						}
					}
				}
			}

		}, 1 * 60 * 20, 1 * 60 * 20);

		VALUABLE_ORES.add(Material.COAL_ORE);
		VALUABLE_ORES.add(Material.IRON_ORE);
		VALUABLE_ORES.add(Material.GOLD_ORE);
		VALUABLE_ORES.add(Material.LAPIS_ORE);
		VALUABLE_ORES.add(Material.GLOWING_REDSTONE_ORE);
		VALUABLE_ORES.add(Material.REDSTONE_ORE);
		VALUABLE_ORES.add(Material.DIAMOND_ORE);
		VALUABLE_ORES.add(Material.EMERALD_ORE);
		VALUABLE_ORES.add(Material.QUARTZ_ORE);

		VALUABLE_CONSUMABLES.add(Material.APPLE);
		VALUABLE_CONSUMABLES.add(Material.BREAD);
		VALUABLE_CONSUMABLES.add(Material.CARROT_ITEM);
		VALUABLE_CONSUMABLES.add(Material.COOKED_BEEF);
		VALUABLE_CONSUMABLES.add(Material.COOKED_CHICKEN);
		VALUABLE_CONSUMABLES.add(Material.COOKED_FISH);
		VALUABLE_CONSUMABLES.add(Material.COOKIE);
		VALUABLE_CONSUMABLES.add(Material.GOLDEN_APPLE);
		VALUABLE_CONSUMABLES.add(Material.GOLDEN_CARROT);
		VALUABLE_CONSUMABLES.add(Material.GRILLED_PORK);
		VALUABLE_CONSUMABLES.add(Material.MELON);
		VALUABLE_CONSUMABLES.add(Material.MUSHROOM_SOUP);
		VALUABLE_CONSUMABLES.add(Material.POISONOUS_POTATO);
		VALUABLE_CONSUMABLES.add(Material.PORK);
		VALUABLE_CONSUMABLES.add(Material.POTATO_ITEM);
		VALUABLE_CONSUMABLES.add(Material.PUMPKIN_PIE);
		VALUABLE_CONSUMABLES.add(Material.RAW_BEEF);
		VALUABLE_CONSUMABLES.add(Material.RAW_CHICKEN);
		VALUABLE_CONSUMABLES.add(Material.RAW_FISH);
		VALUABLE_CONSUMABLES.add(Material.ROTTEN_FLESH);

		VALUABLE_CRAFTABLES.add(new ItemStack(Material.GOLDEN_APPLE, 1, ((short) 0)));
		VALUABLE_CRAFTABLES.add(new ItemStack(Material.GOLDEN_APPLE, 1, ((short) 1)));

		try {
			URL playerEntryURL = new URL("http://uhc.ttaylorr.com/stats_php/server_validate_apikey.php?api=" + API_KEY + "&online=true");
			Bukkit.getScheduler().runTaskAsynchronously(WebInterface.getInstance(), new URLRunnable(playerEntryURL));
		} catch (Exception e) {
			logger.severe("Error connecting to the server");
		}

		// Bukkit.getScheduler().scheduleAsyncDelayedTask(this, new Runnable() {
		// @Override
		// public void run() {
		// try {
		// // Connect to the ping page of the webserver, see if a response is
		// found
		// URL playerEntryURL = new
		// URL("http://localhost/projects/uhc_server/ping.php");
		// URLConnection playerEntryURLConnection =
		// playerEntryURL.openConnection();
		//
		// BufferedReader in = new BufferedReader(new
		// InputStreamReader(playerEntryURLConnection.getInputStream()));
		// String inputLine;
		//
		// inputLine = in.readLine();
		//
		// if (inputLine.equals("Pong")) {
		// logger.info("Succesfully recieved response from webserver!");
		// in.close();
		// } else {
		// logger.severe("Did not recieve a response from the webserver...");
		// }
		// } catch (Exception e) {
		// logger.severe("Malformed URL Exception!  Features may be disabled...");
		// }
		// }
		//
		// });

		// Add all of the event listeners
		pluginManager.registerEvents(new PlayerLoginHandler(), this);
		pluginManager.registerEvents(new PlayerLeaveHandler(), this);
		pluginManager.registerEvents(new PlayerKickHandler(), this);
		pluginManager.registerEvents(new PlayerDeathHandler(), this);
		pluginManager.registerEvents(new PlayerKillstreakHandler(), this);
		pluginManager.registerEvents(new BlockBreakHandler(), this);
		pluginManager.registerEvents(new CraftItemHandler(), this);
		pluginManager.registerEvents(new PlayerItemConsumeHandler(), this);
		pluginManager.registerEvents(new EntityDeathHandler(), this);
		pluginManager.registerEvents(new EntityDamageEntityHandler(), this);
		pluginManager.registerEvents(new ProjectileLaunchHandler(), this);

	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {

			final Player senderPlayer = (Player) sender;

			if (label.equalsIgnoreCase("stats")) {

				String playername = senderPlayer.getName();
				if (args.length != 0) {
					playername = args[0];
				}

				try {
					final URL playersStats = new URL("http://uhc.ttaylorr.com/stats_php/stats.php/?p=" + playername);
					Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
						@Override
						public void run() {
							try {
								URLConnection connection = playersStats.openConnection();

								BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
								String inputLine;

								while ((inputLine = in.readLine()) != null) {
									senderPlayer.sendMessage(inputLine);
								}

								in.close();
							} catch (Exception e) {
								e.printStackTrace();
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
