package rtspWithMultipleClients;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import MakeVideo.CreateImages;

public class ServerThread extends Thread {
	ServerSocket server;

	public ServerThread(ServerSocket server) {
		this.server = server;
		CreateImages startCapture = new CreateImages();
		startCapture.start();
		startCapture=null;
	}

	public void run() {
		Socket client = null;
		
		try {
			
			System.out.println("Server waiting");
			client = this.server.accept();
			System.out.println("Connection accepted from:"+client.getLocalAddress());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MultipleClientHandler runnable = new MultipleClientHandler(client);
		Thread serverHandle = new Thread(runnable);
		serverHandle.start();
		ServerThread restartAccepting = new ServerThread(this.server);
		restartAccepting.start();
		runnable=null;
		serverHandle=null;
		restartAccepting=null;
		client=null;
		System.gc();
	}
}
