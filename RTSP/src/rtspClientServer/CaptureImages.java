/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtspClientServer; 

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * 
 * @author arvind
 */
public class CaptureImages {
	Robot robo;
	public static ArrayList<ImageIcon> imagesList;
	private BufferedImage capture() {
		BufferedImage image = null;
		try {
			robo = new Robot();
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle scrnRect = new Rectangle(dim);
			image = robo.createScreenCapture(scrnRect);

			// File img = new File("images/"+name+".jpg");
			// ImageIO.write(image, "jpg", img);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		/*
		 * catch (IOException ex) {
		 * Logger.getLogger(CreateVideo.class.getName()).log(Level.SEVERE, null,
		 * ex); }
		 */
		return image;
	}

	public ImageIcon captureMultiple(int start, int number)throws InterruptedException {
//		imagesList = new ArrayList<ImageIcon>();
//		for (int i = start; i < number; i++) {
		ImageIcon image = new ImageIcon(this.capture());
//			imagesList.add(image);
		
    		System.out.println("Capturing image.");			
			Thread.sleep(180);
		
		return image;
	}

}
