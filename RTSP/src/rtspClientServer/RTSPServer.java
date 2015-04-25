package rtspClientServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class RTSPServer extends Thread {
	static ImageIcon image;
	static int port = 8006;
	static ServerSocket server;
	static Socket socket;
	
	public static void main(String args[]) {
		while (true) {
			try {
				server = new ServerSocket(port);
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