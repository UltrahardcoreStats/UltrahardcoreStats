package com.ttaylorr.uhc.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.Bukkit;

public class URLRunnable implements Runnable {

	private URL url;
	private boolean doVerboseOutput;
	
	public URLRunnable(URL url) throws MalformedURLException {
		try {
			this.url = url;
		} catch(Exception e) {
			throw new MalformedURLException();
		}
	}
	
	public URLRunnable(URL url, boolean doVerboseOutput) throws MalformedURLException {
		try {
			this.url = url;
			this.doVerboseOutput = doVerboseOutput;
		} catch(Exception e) {
			throw new MalformedURLException();
		}
	}
	
	@Override
	public void run() {
		try {
			URLConnection connection = url.openConnection();
			
			if(doVerboseOutput) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				
				while((inputLine = reader.readLine()) != null) {
					Bukkit.getServer().getLogger().info(inputLine);
				}
				
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
