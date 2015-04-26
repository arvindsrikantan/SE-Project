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
*       TASK    - To create a daemon process for the application
*       		  to start and manage all services on a host. 
*       		  Initializes the tracker server if it doesn't 
*       		  exist already, else joins the network as a client.
*
****************************************************************************/
public class Main
{
	/**
	 * Starting point of the Daemon process that will run on every host
	 */
	public static void main(String[] args)
	{
		new Thread(new TrackerClient()).start();
	}

}
