import java.io.*;
public class RunRTSPClientPython
{
	public static void runClient(String ip, int port, String fileName)
	{
		try
		{
			StringBuffer output = new StringBuffer();
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec("python server.py "+ip+" "+port+" "+fileName);
			
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