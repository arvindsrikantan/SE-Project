/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsp;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

/**
 * 
 * @author arvind
 */
public class RTSP {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) throws IOException {
		new Thread(new Runnable() {
			public void run() {
				try {
					new Transmit().sendImageIcon(ImageIO.read(new File(
							"C:\\Users\\Anish Narang.ANISHNARANG\\git\\SE-Project\\RTSP\\src\\rtsp\\1.jpg")));
				} catch (IOException ex) {
					Logger.getLogger(RTSP.class.getName()).log(Level.SEVERE,
							null, ex);
				}
			}
		}).start();
		System.out.println("Final output");
		new Receive().getImage();
	}
}
