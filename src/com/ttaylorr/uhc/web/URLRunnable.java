package com.ttaylorr.uhc.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;

import com.ttaylorr.uhc.UltrahardcoreStats;

public class URLRunnable implements Runnable {

	private URL url;
	private boolean doVerboseOutput;
	private String URL_PATH;
	
	public URLRunnable(String appendURL, HashMap<String, String> args, boolean doVerboseOutput) throws MalformedURLException {
		StringBuffer buffer = new StringBuffer();

		buffer.append(URLManager.BASE_URL + appendURL);
		buffer.append("?api=" + UltrahardcoreStats.getAPI_KEY());

		Set<String> keys = args.keySet();
		for (String key : keys) {
			buffer.append("&" + key + "=" + args.get(key));
		}

		URL_PATH = buffer.toString();
		
		this.url = new URL(buffer.toString());
		this.doVerboseOutput = doVerboseOutput;
	}

	@Override
	public void run() {
		try {
			URLConnection connection = url.openConnection();

			if (doVerboseOutput) {
				System.out.println(URL_PATH);
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;

				while ((inputLine = reader.readLine()) != null) {
					Bukkit.getServer().getLogger().info(inputLine);
				}

				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
