package rtspWithMultipleClients;

import java.io.IOException;

public class MainClient
{
	public static void main(String[] args)
	{
//		final ServerConnectionAcceptor startServer = new ServerConnectionAcceptor();
//		try
//		{
//			Thread.sleep(6000);
//		}
//		catch (InterruptedException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		new Thread(new Runnable()
		{
			public void run()
			{
//				startServer.acceptConnections();
				try {
					Client startClient = new Client();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try
				{
					Thread.sleep(3000);
				}
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// startClient.play();
			}
		}).start();

//		Client startClient2 = new Client();
//
//		startClient2.play();
	}
}
