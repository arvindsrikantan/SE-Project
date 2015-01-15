/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MakeVideo;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author arvind
 */
public class CreateVideo
{
    public static void main(String[]args) throws InterruptedException
    {
        CreateImages img=new CreateImages();        
        ArrayList<BufferedImage> imageList=img.captureMultiple(0,10);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(dm);
        frame.setUndecorated(true);
        
        //frame.toFront();
		ImageIcon icon = new ImageIcon(imageList.get(0));
		JLabel image = new JLabel(icon);
		frame.add(image);
		frame.setVisible(true);
        for(int i=1;i<imageList.size();i++)
        {
            Thread.sleep(140);
            icon.setImage(imageList.get(i));
            image.setIcon(icon);
            frame.repaint();
        }
    }
}