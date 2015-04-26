package rtspVideo;

import java.io.*;

import constants.Constants;

/*
 * Start the Video Streaming client to stream a video file from another host
 */

public class RunRTSPClientPython 
{
	public static void runClient(String ip, String fileName)
	{
		try
		{
			StringBuffer output = new StringBuffer();
			Runtime rt = Runtime.getRuntime();
                        System.out.println(System.getProperty("user.dir"));
			Process pr = rt.exec("python client.py "+ip+" "+Constants.videoClientPort+" "+fileName);
			
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
}