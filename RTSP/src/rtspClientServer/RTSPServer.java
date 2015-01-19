package rtspClientServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import MakeVideo.CreateImages;

public class RTSPServer extends Thread {
	static ArrayList<ImageIcon> imageList;
	static int port = 8006;
	static ServerSocket server;
	static Socket socket;
	
	public static void main(String args[]) {
		while (true) {
			try {
				//InetAddress addr = InetAddress.getByName("192.168.1.227");
				
				server = new ServerSocket(port);
				socket = server.accept();
				System.out.println("Connection established.");
				try {
					CaptureImages img = new CaptureImages();
					imageList = img.captureMultiple(0, 200);
					ObjectOutputStream obStream = new ObjectOutputStream(socket.getOutputStream());
					for (int i = 1; i < imageList.size(); i++) {
						Thread.sleep(140);
						System.out.println("Sending image.");
						obStream.writeObject(imageList.get(i));	
					}
				}
				catch (InterruptedException e) {}
				}
			catch (Exception e) {}

		}
}
}

