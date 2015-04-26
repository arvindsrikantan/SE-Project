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
import java.net.SocketException;
import constants.Constants;
import utils.Addressing;
/**
 *   Start tracker server
 */
public class TrackerServer implements Runnable
{
	public void run()
	{
		/**
		 * Start tracker server
		 */

//		StringBuffer output = new StringBuffer();
//		Runtime rt = Runtime.getRuntime();
//		Process pr = null;
//		try
//		{
//			pr = rt.exec("python server.py");
//		}
//		catch (IOException e1)
//		{
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));//for error use pr.getErrorStream();
//		String line = "";			
//		try
//		{
//			while ((line = reader.readLine())!= null) 
//			{
//				output.append(line + "\n");
//			}
//		}
//		catch (IOException e1)
//		{
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		System.out.println(output.toString());
//		
//		reader = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
//		line = "";			
//		output = new StringBuffer();
//		try
//		{
//			while ((line = reader.readLine())!= null) 
//			{
//				output.append(line + "\n");
//			}
//		}
//		catch (IOException e1)
//		{
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		System.out.println(output.toString());
//		
//		
//		try
//		{
//			pr.waitFor();
//		}
//		catch (InterruptedException e1)
//		{
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		
		/******************************************************************************************/
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
                            // Wait for client request
				drcv.receive(dp);
				System.out.println("received");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
                        // Reply to the client with tracker server IP if the request is got
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
