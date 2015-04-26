package com.findServer;
/***************************************************************************
*
*   SOFTWARE ENGINEERING PROJECT - 12CS354 - VI SEM BE (PESIT)
*
*       NETWORK STORAGE - SE PROJECT TEAM 1
*
*       JOB     - DAEMON PROCESS
*
*       AUTHORS - ARVIND SRIKANTAN
*               - ANISH NARANG
*
*       TASK    - To start tracker server

****************************************************************************/
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import constants.Constants;

import utils.Addressing;
/**
 *   Start tracker server
 */
public class TrackerServer implements Runnable
{
	public void run()
	{
		DatagramSocket drcv = null;
		try
		{
			drcv = new DatagramSocket(Constants.daemonServerPort);
			System.out.println("Server started");
		}
		catch (SocketException e)
		{
			e.printStackTrace();
		}
		
		DatagramSocket dsend = null;
		try
		{
			dsend = new DatagramSocket();
		}
		catch (SocketException e)
		{
			e.printStackTrace();
		}
		
		byte[] buf = new byte[1024];
		DatagramPacket dp = new DatagramPacket(buf, 1024); 
		while(true)
		{
			try
			{
				drcv.receive(dp);
				System.out.println("received");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			String myIp = Addressing.getIpAddress();
			dp = new DatagramPacket(myIp.getBytes(),
					myIp.length(), dp.getAddress(), Constants.daemonClientPort);
			
			try
			{
				System.out.println("sending");
				dsend.send(dp);
				System.out.println("sent");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
