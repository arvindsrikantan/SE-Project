package rtspClientServer;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

//import MakeVideo.CreateImages;
//import rtsp.Receive;

import java.awt.*;
public class RTSPClient extends Thread {
	static Socket client;

	public static void main(String args[]) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {
		client = new Socket("192.168.0.100", 8006);
		System.out.println("Sent client request.");

	    try {
	     Thread.sleep(50000);
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
			image.setIcon(icon);
			frame.repaint();
			frame.setIconImage(icon.getImage());
			frame.add(image);
			frame.setVisible(true);
			icon = null;
		}
	}
}