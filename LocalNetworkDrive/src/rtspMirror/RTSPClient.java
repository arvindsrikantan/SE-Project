package rtspMirror;
/***************************************************************************
*
*   SOFTWARE ENGINEERING PROJECT - 12CS354 - VI SEM BE (PESIT)
*
*       NETWORK STORAGE - SE PROJECT TEAM 1
*
*       JOB     - SCREEN SHARE
*
*       AUTHORS - ARVIND SRIKANTAN
*               - ANISH NARANG
*
*       TASK    - To run the screen share client
****************************************************************************/
import constants.Constants;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class RTSPClient extends Thread {
    /**
     *  To start the screen share client and send request 
     */
	static Socket client;
	public static void startClient(String serverIP) {
            try {
                client = new Socket(serverIP, Constants.mirrorPort);
            } catch (IOException ex) {
                Logger.getLogger(RTSPClient.class.getName()).log(Level.SEVERE, null, ex);
            }
		System.out.println("Sent client request.");

	    try {
	     Thread.sleep(1000);
	     ShowImage s = new ShowImage();
	     s.Display();
	    } 
	    catch (InterruptedException e) {
	    	}
	    
	  }
}

class ShowImage extends Thread  
{

	public void Display() {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		ObjectInputStream inStream = null;
		try {
			inStream = new ObjectInputStream(RTSPClient.client.getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ImageIcon icon = null;
		try {
			icon = (ImageIcon) (inStream.readObject());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(dm);
		//frame.setUndecorated(true);
		JLabel image = new JLabel(icon);
		frame.add(image);
		frame.setVisible(true);

		while(true) 
		{
//			try {
//				Thread.sleep(140);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			try {
				icon = (ImageIcon) inStream.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                        Image img = icon.getImage();
//                        Image newimg = img.getScaledInstance();
//			image.setIcon(new ImageIcon(newimg));
			frame.repaint();
			frame.setIconImage(icon.getImage());
			frame.add(image);
			frame.setVisible(true);
			icon = null;
		}
	}
}