/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author arvind
 */
public class Receive
{
    Socket server;
    ObjectInputStream inStream;
    public Receive() {
        try {
            this.server = new Socket("localhost",3333);
            inStream = new ObjectInputStream(server.getInputStream());
        } catch (UnknownHostException ex) {
            Logger.getLogger(Receive.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Receive.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void getImage()
    {
        
        try {
            BufferedImage image = (BufferedImage)(inStream.readObject());
            try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Receive.class.getName()).log(Level.SEVERE, null, ex);
        }
            ImageIO.write(image, "jpg", new File("../../image1.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Receive.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Receive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
