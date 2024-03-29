/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsp;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;

/**
 * 
 * @author arvind
 */
public class Transmit {
	ServerSocket server;

	public Transmit() {
		try {
			this.server = new ServerSocket(5556);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void sendImageIcon(BufferedImage image) throws IOException {
		Socket clientSocket = null;
		try {
			clientSocket = this.server.accept();
			System.out.println("Connection accepted");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		ObjectOutputStream obStream = new ObjectOutputStream(
				clientSocket.getOutputStream());
		obStream.writeObject(new ImageIcon(image));
	}
}
