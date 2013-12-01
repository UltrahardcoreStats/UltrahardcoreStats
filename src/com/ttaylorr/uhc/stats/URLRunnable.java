package com.ttaylorr.uhc.stats;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class URLRunnable implements Runnable {

	private URL url;
	
	public URLRunnable(URL url) {
		this.url = url;
	}
	
	@Override
	public void run() {
		try {
			URLConnection URLConnection = url.openConnection();

			BufferedReader in = new BufferedReader(new InputStreamReader(URLConnection.getInputStream()));
	        String inputLine;
	
	        while ((inputLine = in.readLine()) != null) {        	
	        	System.out.println(inputLine);
	        }
	        
	        in.close();
	        
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}