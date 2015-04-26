package rtspClientServer;
/***************************************************************************
*
*   SOFTWARE ENGINEERING PROJECT - 12CS354 - VI SEM BE (PESIT)
*
*       NETWORK STORAGE - SE PROJECT TEAM 1
*
*       JOB     - DAEMON PROCESS
*
*       AUTHORS - ARVIND SRIKANTAN
*               - ANISH NARANG
*
*       TASK    - To start screen share server

****************************************************************************/
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class RTSPServer extends Thread {
	/**
	 *  Starts the screen share server on each host to listen for a connection
	 */
	static ImageIcon image;
	static int port = 8000;
	static ServerSocket server;
	static Socket socket;
	
	public static void main(String args[]) {
		while (true) {
			try {
				server = new ServerSocket(port);
                                System.out.println("Started mirroring server...");
				socket = server.accept();
				System.out.println("Connection established.");
				try {					
					ObjectOutputStream obStream = new ObjectOutputStream(socket.getOutputStream());
					while(true)
					{
						CaptureImages img = new CaptureImages();
						image = img.captureMultiple();
//						Thread.sleep(140);
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