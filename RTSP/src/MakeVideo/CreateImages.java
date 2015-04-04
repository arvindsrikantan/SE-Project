/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MakeVideo;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * @author arvind
 */
public class CreateImages extends Thread
{
	Robot robo;
	private static BufferedImage image;
	int imgCount;

	public void run()
	{
		CreateImages.image = this.capture(imgCount++);
		while(true)
		{
			try
			{
				Thread.sleep(180);
				this.capture(imgCount++);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		CreateImages reCapture = new CreateImages();
//		reCapture.start();
	}

	public static BufferedImage getScreenShot()
	{
		return CreateImages.image;
	}

	private BufferedImage capture(int name)
	{
		BufferedImage image = null;
		try
		{
			robo = new Robot();
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle scrnRect = new Rectangle(dim);
			image = robo.createScreenCapture(scrnRect);

//			screenShot = new ImageIcon(image);
			System.out.println(name);
			File img = new File("D:/engineering/CSE 6th sem/SE Project/images/" + name + ".jpg");
			ImageIO.write(image, "jpg", img);
		}
		catch (AWTException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return image;
	}

}
