
package se;

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
       
        System.out.println("Enter The Extension of the file");
        String fileext=in.nextLine();
        GenerateKeys gk=new GenerateKeys();
        gk.createfiles();
        
         

    try{
    File a= new File(path+"."+fileext);
    File b= new File(path+"encrypted."+fileext);
    File encryptedKeyFile= new File(path+"encryptedKeyFile."+fileext);
    File result=new File(path+"decrypted."+fileext);
    File publicKeyFile= new File("C://Users//DELL//Documents//NetBeansProjects//SE//public.der");//("F://bin/public.der");
    File privateKeyFile= new File("C://Users//DELL//Documents//NetBeansProjects//SE//private.der");//("F:/bin/private.der");
   
// to encrypt a file
secure.makeKey();
secure.saveKey(encryptedKeyFile, publicKeyFile);
secure.encrypt(a,b);
            try {
                secure.hide(b);
            } catch (InterruptedException ex) {
                Logger.getLogger(SE.class.getName()).log(Level.SEVERE, null, ex);
            }
// to decrypt it again
secure.loadKey(encryptedKeyFile, privateKeyFile);
secure.decrypt(b, result);
 }
    catch(FileNotFoundException f)
    {
    System.out.println("File not found");
    }
    }
}
