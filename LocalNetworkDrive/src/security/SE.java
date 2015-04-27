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
package security;

/***************************************************************************
*
*   The following statements are used to import the various modules
*   which are required for the project.
*
****************************************************************************/

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SE {

  
//    public static void main(String[] args) throws GeneralSecurityException, IOException {
//       
//        Scanner in =new Scanner(System.in);
//        System.out.println("Enter The file name including path of the file");
//        String path=in.nextLine();
//       SE se=new SE();
//       se.encr(path);
//      se.decr(path);
//        
//}


    
    
    
/***************************************************************************
*
*   To encrypt a file
*
****************************************************************************/
  public void encr(String path) throws NoSuchAlgorithmException, IOException, GeneralSecurityException
    {
     FileEncryption secure = new FileEncryption();
     GenerateKeys gk=new GenerateKeys();
        gk.createfiles();
        String[] ext = path.split("\\.");
        String q= ext[1];
        String mp="mp4";
if(!q.equalsIgnoreCase( mp))
{ 
    
    String name=ext[0];
    for(int i=1;i<ext.length-1;i++)
        name+="."+ext[i];
   // try{
    File a= new File(name+"."+ext[ext.length-1]);
    File b= new File(name+"encrypted."+ext[ext.length-1]);
    File encryptedKeyFile= new File(name+"encryptedKeyFile."+ext[ext.length-1]);
    
    File result=new File(name+"decrypted."+ext[1]);
    File publicKeyFile= new File("public.der");//("F://bin/public.der");
    File privateKeyFile= new File("private.der");//("F:/bin/private.der");
   

secure.makeKey();
secure.saveKey(encryptedKeyFile, publicKeyFile);
secure.encrypt(a,b);
boolean f=a.delete();
if(!f)
{
System.out.println("failed to delete");
}
boolean t=b.renameTo(a);
if(!t)
System.out.println("no success");

try {
                // secure.hide(b);
            } catch (Exception ex) {
                Logger.getLogger(SE.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    }
    
   
 /***************************************************************************
*
*   To decrypt a file
*
****************************************************************************/   
 public void decr(String path) throws NoSuchAlgorithmException, IOException, GeneralSecurityException
    {
     FileEncryption secure = new FileEncryption();
         String[] ext = path.split("\\.");
         String name=ext[0];
        for(int i=1;i<ext.length-1;i++)
            name+="."+ext[i];
        String q= ext[ext.length-1];
        String mp="mp4";
if(!q.equalsIgnoreCase( mp))
{ 
    
    // try{
    //File x= new File(name+"."+ext[1]);
    File a= new File(name+"."+ext[ext.length-1]);
    File b= new File(name+"."+ext[ext.length-1]);
    File encryptedKeyFile= new File(name+"encryptedKeyFile."+ext[ext.length-1]);
    File result=new File(name+"decrypted."+ext[ext.length-1]);
    File publicKeyFile= new File("public.der");//("F://bin/public.der");
    File privateKeyFile= new File("private.der");//("F:/bin/private.der");
   
      
    secure.loadKey(encryptedKeyFile, privateKeyFile);
secure.decrypt(b,result);
boolean f=a.delete();
if(!f)
{
System.out.println("failed to delete");
}
boolean t=result.renameTo(a);
if(!t)
System.out.println("no success");
  
 }
    }
    }

