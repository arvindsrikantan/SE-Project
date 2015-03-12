import java.io.*;
public class RunPython
{
	public static void main(String args[])throws Exception
	{
		StringBuffer output = new StringBuffer();
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec("python python.py");
		pr.waitFor();
		BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line = "";			
		while ((line = reader.readLine())!= null) 
		{
			output.append(line + "\n");
		}
		System.out.println(output.toString());
	}
}