package rtspWithMultipleClients;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

public class ClientImageReciever extends Thread
{
	DataInputStream	inStream;
	int					i;
	Socket server;

	public ClientImageReciever(DataInputStream inStream2)
	{
		this.inStream = inStream2;
	}

	public void run()
	{
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		ImageIcon icon = null;

		while (true)
		{
			try
			{
				try {
					Thread.sleep(140);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//icon = (ImageIcon) this.inStream.readObject();
				String data = this.inStream.readUTF();
				File f = new File("E:\\OUT\\"+ (i++) + ".jpg");
				FileWriter fileWriter = new FileWriter(f);
	            fileWriter.write(data);
	            fileWriter.close();
				System.out.println("In client image receiver.");

				//FileOutputStream fout = new FileOutputStream(f);
				//fout.write(data);
//				String data;
//				int ch;
//				do
//	            {
//	                data=this.inStream.readUTF();
//	                ch=Integer.parseInt(data);
//	                if(ch!=-1)
//	                {
//	                    fout.write(ch);                    
//	                }
//	            }while(ch!=-1);
//	            fout.close();

//				BufferedImage image = new BufferedImage(
//					    icon.getIconWidth(),
//					    icon.getIconHeight(),
//					    BufferedImage.TYPE_INT_RGB);
//					Graphics g = image.createGraphics();
//					// paint the Icon to the BufferedImage.
//					//icon.paintIcon(null, g, 0,0);
//					g.drawImage(icon.getImage(),0,0,null);
//					g.dispose();
//				RenderedImage img = image;
//				
//			ImageIO.write(img, "jpg", new File("E:\\TEMP\\"
//						+ (i++) + ".jpg"));
			}
//			catch (ClassNotFoundException e)
//			{
//				e.printStackTrace();
//			}
			catch (IOException e)
			{
				System.out.println(e);
//				e.printStackTrace();
			}

			// if (this.list.size() >= 200)
			// {
			// this.clear();
			// System.gc();
			// }
			icon = null;
		}
	}
	
	public void byteToImage(byte[] bytes,int i) throws IOException
	{
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator<?> readers = ImageIO.getImageReadersByFormatName("jpg");
 
        //ImageIO is a class containing static methods for locating ImageReaders
        //and ImageWriters, and performing simple encoding and decoding. 
 
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis; 
        ImageInputStream iis = ImageIO.createImageInputStream(source); 
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
 
        Image image = reader.read(0, param);
        //got an image file
 
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        //bufferedImage is the RenderedImage to be written
 
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, null, null);
 
        File imageFile = new File("E:\\OUT\\"+ i + ".jpg");
        ImageIO.write(bufferedImage, "jpg", imageFile);
 
	}

	// protected synchronized void clear()
	// {
	// for (int i = 0; i < 75; i++)
	// {
	// this.list.remove(i);
	// }
	// if (Client.picCount >= 75)
	// {
	// Client.picCount = Client.picCount - 75;
	// }
	// }
	//
	// protected synchronized void reset()
	// {
	// Client.picCount = 0;
	// }
}
