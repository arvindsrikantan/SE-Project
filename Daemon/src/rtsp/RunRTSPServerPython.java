package rtsp;
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
*       TASK    - To start video streaming server

****************************************************************************/
import java.io.*;

import constants.Constants;

/**
 *   Start the Video Streaming server on the host and wait for stream request
 */

public class RunRTSPServerPython implements Runnable
{
	public static void runServer(String ip, int port)
	{
		try
		{
			StringBuffer output = new StringBuffer();
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec("python server.py "+ip+" "+port);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));//for error use pr.getErrorStream();
			String line = "";			
			while ((line = reader.readLine())!= null) 
			{
				output.append(line + "\n");
			}
			System.out.println(output.toString());
			
			reader = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
			line = "";			
			output = new StringBuffer();
			while ((line = reader.readLine())!= null) 
			{
				output.append(line + "\n");
			}
			System.out.println(output.toString());
			
			
			pr.waitFor();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		runServer(Constants.myIp,Constants.videoServerPort);
		new Thread(new RunRTSPServerPython()).start();
	}
}