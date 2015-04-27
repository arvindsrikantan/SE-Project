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
*       TASK    - To run the screen share server
****************************************************************************/
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;

import constants.Constants;

public class RTSPServer extends Thread {
    /**
     *  Start the screen share server and wait for request
     */
	static ImageIcon image;
	static ServerSocket server;
	static Socket socket;
	
	public static void startServer() {
		while (true) {
			try {
				server = new ServerSocket(Constants.mirrorPort);
				System.out.println("Screen share server started...");
				socket = server.accept();
				System.out.println("Connection established.");
				try {					
					ObjectOutputStream obStream = new ObjectOutputStream(socket.getOutputStream());
					while(true)
					{
						CaptureImages img = new CaptureImages();
						image = img.captureMultiple();
//						Thread.sleep(140);
						System.out.println("In server...");
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