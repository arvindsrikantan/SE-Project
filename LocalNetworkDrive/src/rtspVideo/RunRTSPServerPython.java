package rtspVideo;
/***************************************************************************
*
*   SOFTWARE ENGINEERING PROJECT - 12CS354 - VI SEM BE (PESIT)
*
*       NETWORK STORAGE - SE PROJECT TEAM 1
*
*       JOB     - VIDEO STREAMING
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
            /**
             * Start python server using a new Java process
             */
		try
		{
                    // Start the python server using a new process
			StringBuffer output = new StringBuffer();
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec("python server.py "+ip+" "+port);
			
                    // Get the input stream of the process
			BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));//for error use pr.getErrorStream();
			String line = "";			
			while ((line = reader.readLine())!= null) 
			{
				output.append(line + "\n");
			}
			System.out.println(output.toString());
                    // Get the error stream of the process
			reader = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
			line = "";			
			output = new StringBuffer();
			while ((line = reader.readLine())!= null) 
			{
				output.append(line + "\n");
			}
			System.out.println(output.toString());
			
			//Wait for the process to complete
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
            /**
             * Start server in a new thread
             */
		runServer(Constants.myIp,Constants.videoServerPort);
		new Thread(new RunRTSPServerPython()).start();
	}
}