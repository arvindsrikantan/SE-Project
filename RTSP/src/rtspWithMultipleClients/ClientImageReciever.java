package rtspWithMultipleClients;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ClientImageReciever extends Thread
{
	ArrayList<BufferedImage> list;
	ObjectInputStream inStream;
	int i;

	public ClientImageReciever(ArrayList<BufferedImage> list,
			ObjectInputStream inStream)
	{
		this.list = list;
		this.inStream = inStream;
	}

	public void run()
	{
		BufferedImage image = null;

		while (true)
		{
			// System.out.println("taken");
			try
			{
				image = (BufferedImage) this.inStream.readObject();
				ImageIO.write(image,"jpg",new File("D:\\receivedImages\\"+(i++)+".jpg"));
			}
			catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//			if (this.list.size() >= 200)
//			{
//				this.clear();
//				System.gc();
//			}
			image = null;
		}
	}

//	protected synchronized void clear()
//	{
//		for (int i = 0; i < 75; i++)
//		{
//			this.list.remove(i);
//		}
//		if (Client.picCount >= 75)
//		{
//			Client.picCount = Client.picCount - 75;
//		}
//	}
//
//	protected synchronized void reset()
//	{
//		Client.picCount = 0;
//	}
}
