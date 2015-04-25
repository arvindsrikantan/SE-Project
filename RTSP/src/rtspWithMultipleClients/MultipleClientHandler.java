package rtspWithMultipleClients;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.ImageIcon;

public class MultipleClientHandler implements Runnable
{
	Socket	client;
	int		count	= 0;

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
					File imageFile = new File("E:\\TEMP\\" + name + ".jpg");
					FileInputStream fi = new FileInputStream(imageFile);
					// Path path = Paths.get("E:\\TEMP\\"+ name + ".jpg");
					System.out.println(imageFile.length());
					if (imageFile.exists())
					{
						int ch;
			            do
			            {
			                ch=fi.read();
			                clientStream.writeUTF(String.valueOf(ch));			                
			            }
			            while(ch!=-1);
						

			            fi.close();
//						int r = 0;
//						byte buffer[] = new byte[160000];
//						int read = fi.read(buffer);
//						for(int i=0;i<=read;i+=65000)
//						{
//							byte b[] = new byte[65000];
//							for(int j=0;j<65000 && i+65000<read;j++)
//							{
//								b[j] = buffer[i+j];
//							}
//							clientStream.write(b);
//
//						}
//						 while (true)
//						 {
//						 byte buffer[] = new byte[160000];
//						 int read = fi.read(buffer);
//						 if (read < 0)
//						 {
//						 break;
//						 }
//						 clientStream.write(buffer,0,read);
//						 }
//						fi.close();
					}

					else
					{
						name--;
					}

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
