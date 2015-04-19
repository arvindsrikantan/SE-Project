/***************************************************************************
*
*   SOFTWARE ENGINEERING PROJECT - 12CS354 - VI SEM BE (PESIT)
*
*
*       NETWORK STORAGE - SE PROJECT TEAM 1
*
*
*       JOB     - SECURITY- ENCRYPTION USING AES AND RSA ALGORITHMS
*				- CLIENT FILE
*
*
*       AUTHORS - ABHISHEK KULKARNI
*               - ACHYUT HEGDE
*
*
*       TASK    - To encrypt a file specified in the path using AES algorithm 
*				  and encrypting the AES shared key using RSA algorithm and decrypting 
*				  the encrypted files.
*				  To hide and unhide encrypted and decrypted files.
*
*
*       START   - February 13th
*
*
*
****************************************************************************/
package se;

/***************************************************************************
*
*   The following statements are used to import the various modules
*   which are required for the project.
*
****************************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SE {

  
    public static void main(String[] args) throws GeneralSecurityException, IOException {
        FileEncryption secure = new FileEncryption();
        Scanner in =new Scanner(System.in);
        System.out.println("Enter The file name including path of the file");
        String path=in.nextLine();
       
        GenerateKeys gk=new GenerateKeys();
        gk.createfiles();
        
         
        String[] ext = path.split("\\.");
        String q= ext[1];
        String mp="mp4";
if(!q.equalsIgnoreCase( mp))
{ 
    System.out.println("in if"+ext[1]);
    
    String name=ext[0];
    try{
    File a= new File(name+"."+ext[1]);
    File b= new File(name+"encrypted."+ext[1]);
    File encryptedKeyFile= new File(name+"encryptedKeyFile."+ext[1]);
    File result=new File(name+"decrypted."+ext[1]);
    File publicKeyFile= new File("C://Users//DELL//Documents//NetBeansProjects//SE//public.der");//("F://bin/public.der");
    File privateKeyFile= new File("C://Users//DELL//Documents//NetBeansProjects//SE//private.der");//("F:/bin/private.der");
   
/***************************************************************************
*
*   To encrypt a file
*
****************************************************************************/
secure.makeKey();
secure.saveKey(encryptedKeyFile, publicKeyFile);
secure.encrypt(a,b);
            try {
                secure.hide(b);
            } catch (InterruptedException ex) {
                Logger.getLogger(SE.class.getName()).log(Level.SEVERE, null, ex);
            }
   
/***************************************************************************
*
*   To decrypt it again
*
****************************************************************************/
secure.loadKey(encryptedKeyFile, privateKeyFile);
secure.decrypt(b, result);
 }
    catch(FileNotFoundException f)
    {
    System.out.println("File not found");
    }
 
}
else{

System.out.println("It is a video. Cannot Encrypt");
}
    }
}
