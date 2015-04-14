/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class GenerateKeys {
  public void createfiles() {
      try {
          String password = "password";
          
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