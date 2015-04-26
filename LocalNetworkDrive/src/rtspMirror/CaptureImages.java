package rtspMirror; 
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
*       TASK    - Take screenshots 

****************************************************************************/
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
public class CaptureImages {
	/**
	 *  Capture screenshot and return the image to the caller
	 */
	Robot robo;
	public static ArrayList<ImageIcon> imagesList;
	private BufferedImage capture() {
		BufferedImage image = null;
		try {
			robo = new Robot();
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle scrnRect = new Rectangle(dim);
			image = robo.createScreenCapture(scrnRect);

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

	public ImageIcon captureMultiple()throws InterruptedException {
		ImageIcon image = new ImageIcon(this.capture());		
    		System.out.println("Capturing image.");			
			Thread.sleep(180);
		
		return image;
	}

}