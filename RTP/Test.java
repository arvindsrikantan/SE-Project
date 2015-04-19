public class Test
{
	public static void main(String args[])
	{
		FTPClientAPI fca = new FTPClientAPI("127.0.0.1");
		fca.SendFile("he/abc.txt");
	}
}