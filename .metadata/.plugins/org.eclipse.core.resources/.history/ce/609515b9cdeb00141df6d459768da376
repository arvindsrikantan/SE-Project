package mirroring;

import constants.Constants;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class RTSPServer extends Thread {
	static ImageIcon image;
	static ServerSocket server;
	static Socket socket;
	
	public static void startServer() {
		while (true) {
			try {
				server = new ServerSocket(Constants.mirrorPort);
				socket = server.accept();
				System.out.println("Connection established.");
				try {					
					ObjectOutputStream obStream = new ObjectOutputStream(socket.getOutputStream());
					while(true)
					{
						CaptureImages img = new CaptureImages();
						image = img.captureMultiple();
						Thread.sleep(140);
						System.out.println("Sending image.");
						obStream.writeObject(image);
						img = null;
					}
				}
				catch (InterruptedException e) {}
				}
			catch (Exception e) {}
		}
		
}
}