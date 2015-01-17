package rtspClientServer;

import java.io.IOException;
import java.net.UnknownHostException;

public class Main {
	public static void main(String args[]) throws UnknownHostException,
			IOException, ClassNotFoundException, InterruptedException {
		Thread t = new RTSPServer();
		t.start();
		RTSPClient c = new RTSPClient();
		c.showImage();
	}
}