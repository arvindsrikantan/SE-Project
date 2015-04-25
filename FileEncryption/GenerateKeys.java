/***************************************************************************
*
*   SOFTWARE ENGINEERING PROJECT - 12CS354 - VI SEM BE (PESIT)
*
*
*       NETWORK STORAGE - SE PROJECT TEAM 1
*
*
*       JOB     - SECURITY- ENCRYPTION USING AES AND RSA ALGORITHMS
*				- GENERATING KEYS
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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class GenerateKeys {


	
/***************************************************************************
*
*   Create public and private RSA keys and stores in public.der and private.der files
*
****************************************************************************/	

  public void createfiles() {
      try {
          
          KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
          keyPairGenerator.initialize(2048);
          KeyPair keyPair = keyPairGenerator.genKeyPair();
          String publicKeyFilename = "public.der";
          
          byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
          
          FileOutputStream fos = new FileOutputStream(publicKeyFilename);
          fos.write(publicKeyBytes);
          fos.close();
          
          String privateKeyFilename = "private.der";
          
          byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
          
          
          
          fos = new FileOutputStream(privateKeyFilename);
          fos.write(privateKeyBytes);
          fos.close();
      } catch (NoSuchAlgorithmException ex) {
          System.out.println("Something went wrong");
      } catch (FileNotFoundException ex) {
          System.out.println("Something went wrong");
      } catch (IOException ex) {
         System.out.println("Something went wrong");
      }
  }
}