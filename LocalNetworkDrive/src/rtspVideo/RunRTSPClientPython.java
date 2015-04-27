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
*       TASK    - To start video streaming client

****************************************************************************/
import java.io.*;

import constants.Constants;

public class RunRTSPClientPython 
{
/*
 * Start the Video Streaming client to stream a video file from another host
 */
	public static void runClient(String ip, String fileName)
	{
		try
		{
                        //Start a new process
                    System.out.println(fileName);
			StringBuffer output = new StringBuffer();
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec("python client.py "+ip+" "+Constants.videoClientPort+" "+fileName);
			
                        //Get the input stream of the process
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
			
			// Wait for the python server to complete
			pr.waitFor();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}