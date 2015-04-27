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
*       TASK    - To setup the daemon process

****************************************************************************/
package com.findServer;
import hooks.WatchDir;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rtp.FTPServerAPI;
import rtsp.RunRTSPServerPython;
import utils.Addressing;
import constants.Constants;
import mirroring.RTSPServer;
/**
 *   Creates a UDP socket to receive a message from the tracker server.
 *   If a message is not received in 7 tries,the current host becomes the tracker server.
 *   Sets all required constants.
 *   Get IP address of tracker server if already running.
 *   Starts server processes for all services.
 */
public class TrackerClient implements Runnable
{
	DatagramPacket dp;
	public void run()
	{
		DatagramSocket dsend = null;
		try
		{
			dsend = new DatagramSocket();
		}
		catch (SocketException e)
		{
			e.printStackTrace();
		}
		DatagramSocket drcv = null;
		try
		{
			drcv = new DatagramSocket(Constants.daemonClientPort);
			drcv.setSoTimeout(2000);
		}
		catch (SocketException e)
		{
			e.printStackTrace();
		}
		byte[] buf = new byte[1024];
		for(int tries=0;tries<7;tries++)
		{
			//send first and wait for reply
			String ipad = Addressing.getIpAddress();

			System.out.println("try no:"+tries);
			String serverIP = ipad;
			System.out.println(ipad);
			String ipParts[] = ipad.split("[.]");
			Constants.subnetId = ipParts[0] + "." + ipParts[1] + "." + ipParts[2];
			String inetName = Constants.subnetId + ".255";

			InetAddress ip = null;
			try
			{
				ip = InetAddress.getByName(inetName);
			}
			catch (UnknownHostException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			dp = new DatagramPacket(serverIP.getBytes(),
					serverIP.length(), ip, Constants.daemonServerPort);
			try
			{
				dsend.send(dp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			
			
			//waiting for reply
			dp = new DatagramPacket(buf, 1024);
			try
			{
				drcv.receive(dp);
				System.out.println("Breaking");
				break;
			}
			catch(SocketTimeoutException ex)
			{
				if(tries == 6)
				{
					//Start tracker server since no reply was got 
					new Thread(new TrackerServer()).start();
					
				}
				continue;
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
//			ds.close();
		}
		String str = new String(dp.getData(), 0, dp.getLength());
		if(str.trim().equals(""))
			str = Addressing.getIpAddress();
		System.out.println("final server ip:"+str);
		Constants.serverIp = str;
		
		/**
		 *  Starting all Server processes
		 */
		// Set myIP
		Constants.myIp = Addressing.getIpAddress();
		
		// Start hooks
		try
		{
			WatchDir.startHooks();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		// Start RTSP Server
//		RunRTSPServerPython rtspServer = new RunRTSPServerPython();
//		Thread startRtspServer = new Thread(rtspServer);
//		startRtspServer.start();
		
		// Start  FTP Server
		FTPServerAPI ftpServer = new FTPServerAPI();
		ftpServer.start();
		
		// Start Screen Share Server
		RTSPServer.startServer();
		
	}
}
