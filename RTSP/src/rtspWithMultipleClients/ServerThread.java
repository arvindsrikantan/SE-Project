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
	}

	public void run() {
		Socket client = null;
		try {
			client = this.server.accept();
			System.out.println("Connection accepted");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MultipleClientHandler runnable = new MultipleClientHandler(client);
		Thread serverHandle = new Thread(runnable);
		serverHandle.start();
		ServerThread restartAccepting = new ServerThread(this.server);
		restartAccepting.start();
	}
}
