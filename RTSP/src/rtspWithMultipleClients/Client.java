package rtspWithMultipleClients;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Client
{
	Socket				server;
	static int			picCount;
	ClientImageReciever	startBuffer;

	public Client()
	{
		try
		{
			this.server = new Socket("localhost", 5555);
			System.out.println("Client socket created");
		}
		catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.bufferImageIconList();
		System.gc();
	}

	private void bufferImageIconList()
	{
		ObjectInputStream inStream = null;
		try
		{
			inStream = new ObjectInputStream(this.server.getInputStream());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		startBuffer = new ClientImageReciever(inStream);
		startBuffer.start();
		inStream = null;
	}

	public void play()
	{
		// Method to start or continue playing
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(dm);
		frame.setUndecorated(true);

		// ImageIcon icon = new ImageIcon(imageListBuffer.get(0));
		BufferedImage im = null;
		ImageIcon icon = null;
		JLabel image = null;
		try
		{
			for (int name = 0;; name++)
			{
				File imageFile = new File(
						"D:/engineering/CSE 6th sem/SE Project/images/" + name
								+ ".jpg");
				if (imageFile.exists())
				{
					im = ImageIO.read(imageFile);
				}
				else
				{
					name--;
				}
				if (name == 0)
				{
					System.out.println("\n\n\n\n\n\n\n\n\n\n\nFirst time\n\n\n\n");
					icon = new ImageIcon(im);
					image = new JLabel(icon);
					frame.add(image);
					frame.setVisible(true);
				}
				else
				{
					icon = new ImageIcon(im);
					image.setIcon(icon);
					frame.repaint();
				}
				// clientStream.writeObject(image);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		int j = 0;
		while (j < 1000)
		{

		}
		frame = null;
		dm = null;
		icon = null;
		image = null;
		// change into thread and keep calling it
		System.out.println("Done");
		System.gc();
	}
	// public void pause()
	// {
	// // Method to pause the video
	// startBuffer.reset();
	// this.play();
	// }
	//
	// public void stop()
	// {
	// try
	// {
	// this.server.close();
	// }
	// catch (IOException e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
}
