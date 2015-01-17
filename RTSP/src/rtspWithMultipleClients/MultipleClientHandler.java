package rtspWithMultipleClients;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;

public class MultipleClientHandler implements Runnable {
	Socket client;
	int count=0;

	public MultipleClientHandler(Socket client) {
		this.client = client;
	}

	public void run() {
		ObjectOutputStream clientStream = null;
		try {
			clientStream = new ObjectOutputStream(this.client.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ImageIcon img = ;
		//clientStream.write();
	}
}
