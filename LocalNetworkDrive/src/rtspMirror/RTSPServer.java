package rtspMirror;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;

import constants.Constants;

public class RTSPServer extends Thread {
	static ImageIcon image;
	static ServerSocket server;
	static Socket socket;
	
	public static void startServer() {
		while (true) {
			try {
				server = new ServerSocket(Constants.mirrorPort);
				System.out.println("SCreen share server started...");
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