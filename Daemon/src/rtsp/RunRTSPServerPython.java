package rtsp;

import java.io.*;

import constants.Constants;
public class RunRTSPServerPython implements Runnable
{
	public static void runServer(String ip, int port)
	{
		try
		{
			StringBuffer output = new StringBuffer();
			Runtime rt = Runtime.getRuntime();
                        System.out.println(System.getProperty("user.dir"));
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