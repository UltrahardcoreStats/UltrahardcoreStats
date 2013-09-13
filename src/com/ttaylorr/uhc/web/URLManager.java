package com.ttaylorr.uhc.web;

public class URLManager {

	final static String BASE_URL;
	
	static {
		BASE_URL = "http://uhc.ttaylorr.com/stats_php/";
	}
	
	public String getBaseURL() {
		return this.BASE_URL;
	}
	
}
