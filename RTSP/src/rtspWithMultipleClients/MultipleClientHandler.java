package rtspWithMultipleClients;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class MultipleClientHandler implements Runnable
{
	Socket client;
	int count = 0;

	public MultipleClientHandler(Socket client)
	{
		this.client = client;
	}

	public void run()
	{
		DataOutputStream clientStream = null;
		try
		{
			clientStream = new DataOutputStream(this.client.getOutputStream());
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// change true to a condition variable
		try
		{
			// ImageIcon icon = null;
			ImageIcon image = null;
			while (true)
			{
				// icon = CreateImages.getScreenShot();
				// clientStream.writeObject(icon);
				// icon = null;
				// Read from file and write to stream
				for (int name = 0;; name++)
				{
					//File imageFile = new File(
					//		"E:\\TEMP\\"
					//				+ name + ".jpg");
					Path path = Paths.get("E:\\TEMP\\"+ name + ".jpg");
					byte img[] = null;
					if (path.toFile().exists())
					{
						img = Files.readAllBytes(path);
						//image = new ImageIcon(ImageIO.read(imageFile));
					}
					else
					{
						name--;
					}
					String temp = new String(img);
					for(int i=0;i<temp.length();i+=65000)
					{
						clientStream.writeChars(temp.substring(i,i+64999));
//						clientStream.writeUTF(temp.substring(i,i+64999));
					}
//					clientStream.write(img);
						
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				clientStream.close();
				this.client.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
