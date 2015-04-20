package rtspWithMultipleClients;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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

	public Client() throws IOException, InterruptedException
	{
		try
		{
			this.server = new Socket("localhost", 5555);
			System.out.println("Client socket created");
			this.bufferImageIconList();
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
		
	}

	private void bufferImageIconList()
	{
		DataInputStream inStream = null;
		try
		{
			//inStream = new ObjectInputStream(this.server.getInputStream());
			inStream = new DataInputStream(this.server.getInputStream());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		startBuffer = new ClientImageReciever(inStream);
		startBuffer.start();
		inStream = null;
		
		play();
	}

	public void play()
	{
		new Thread(new Runnable()
		{
			public void run()
			{
			try {
				Thread.sleep(4000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
		int name = 0;
		try
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (name = 0;; name++)
			{
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				File imageFile = new File(
						"E:\\OUT\\" + name
								+ ".jpg");
				if (imageFile.exists())
				{
					System.out.println("File exists:"+name);
					im = ImageIO.read(imageFile);
				}
				else
				{
					name--;
					System.out.println("File doesnt exist:"+name);
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
//					image.setIcon(icon);
//					frame.repaint();
					image.setIcon(icon);
					frame.repaint();
					frame.setIconImage(icon.getImage());
					frame.add(image);
					frame.setVisible(true);
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
		}).start();
	}
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

