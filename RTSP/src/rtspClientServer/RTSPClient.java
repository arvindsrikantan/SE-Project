package rtspClientServer;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import MakeVideo.CreateImages;
import rtsp.Receive;

public class RTSPClient extends Thread {
	static Socket client;

	public static void main(String args[]) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {
		client = new Socket("localhost", 8006);
		System.out.println("Sent client request.");
		
		ObjectInputStream inStream = new ObjectInputStream(client.getInputStream());
		ImageIcon icon = (ImageIcon) (inStream.readObject());

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(dm);
		frame.setUndecorated(true);
		JLabel image = new JLabel(icon);
		frame.add(image);
		frame.setVisible(true);

		for (int i = 1; i < 200; i++) {
			Thread.sleep(140);
			icon = (ImageIcon) inStream.readObject();
			image.setIcon(icon);
			frame.add(image);
			frame.repaint();
		}
	}

}
