public class RunPython
{
	public static void main(String args[])throws Exception
	{
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec("python python.py");
		System.out.println("Done");
	}
}