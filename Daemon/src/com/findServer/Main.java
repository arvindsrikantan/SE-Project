package com.findServer;

public class Main
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		new Thread(new TrackerClient()).start();
	}

}
