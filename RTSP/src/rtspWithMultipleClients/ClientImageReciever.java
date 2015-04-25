package rtspWithMultipleClients;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;

public class ClientImageReciever extends Thread
{
	DataInputStream	inStream;
	int					i;

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
		int i =0;
		while (true)
		{
			try
			{
//				try {
//					Thread.sleep(140);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}

				
				File f = new File("E:\\OUT\\"+ (i++) + ".jpg");
//				FileWriter fw = new FileWriter(f,true);
				FileOutputStream fout = new FileOutputStream(f,true);
                int r;
                int ch;
                String temp;
                do
                {
                	
                    temp=this.inStream.readUTF();
                    ch=Integer.parseInt(temp);
                    if(ch!=-1)
                    {
                        fout.write(ch);                    
                    }
                }while(ch!=-1);
				fout.close();

//				do
//				{
//					byte img[] = new byte[65000];
//					this.inStream.read(img);
//					String s = new String(img).trim();
//					r =s.length();
//					fw.write(s);
//					System.out.println("R,i:"+r+","+i);
//					//System.out.println(s);
//				}while(r==65000);
                                
                                
//                                while (true)
//                                {
//                                    byte buffer[] = new byte[160000];
//                                    int read = this.inStream.read(buffer);
//                                    if (read < 0)
//                                    {
//                                        break;
//                                    }
//                                    System.out.println("read:"+read+","+i);
//                                    fout.write(buffer);
//                                }
                                
				System.out.println("In client image receiver.");
			}	

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
//				ImageIO.write(img, "jpg", new File("E:\\TEMP\\"
//						+ (i++) + ".jpg"));
//			catch (ClassNotFoundException e)
//			{
//				e.printStackTrace();
//			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			// if (this.list.size() >= 200)
			// {
			// this.clear();
			// System.gc();
			// }
		}
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
