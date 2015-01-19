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
	static ImageIcon image;
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
					ObjectOutputStream obStream = new ObjectOutputStream(socket.getOutputStream());
					for (int i = 1; i < 200; i++) {
						CaptureImages img = new CaptureImages();
						image = img.captureMultiple(0, 200);
						Thread.sleep(140);
						System.out.println("Sending image.");
						obStream.writeObject(image);	
					}
				}
				catch (InterruptedException e) {}
				}
			catch (Exception e) {}
		}
		
}
}

