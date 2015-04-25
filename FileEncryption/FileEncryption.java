/***************************************************************************
*
*   SOFTWARE ENGINEERING PROJECT - 12CS354 - VI SEM BE (PESIT)
*
*
*       NETWORK STORAGE - SE PROJECT TEAM 1
*
*
*       JOB     - SECURITY- ENCRYPTION USING AES AND RSA ALGORITHMS
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
import java.io.*;
import java.security.*;
import java.security.spec.*;

import javax.crypto.*;
import javax.crypto.spec.*;


public class FileEncryption {
	
	public static final int AES_Key_Size = 256;
	
	Cipher pkCipher, aesCipher;
	byte[] aesKey;
	SecretKeySpec aeskeySpec;

	
/***************************************************************************
*
*   Constructor: creates ciphers
*
****************************************************************************/	

	public FileEncryption() throws GeneralSecurityException {
		// create RSApublic key cipher
		pkCipher = Cipher.getInstance("RSA");
	    // create AES shared key cipher
	    aesCipher = Cipher.getInstance("AES");
	}

/***************************************************************************
*
*   Creates a new AES key
*
****************************************************************************/	
	public void makeKey() throws NoSuchAlgorithmException {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
	    kgen.init(AES_Key_Size);
	    SecretKey key = kgen.generateKey();
	    aesKey = key.getEncoded();
	    aeskeySpec = new SecretKeySpec(aesKey, "AES");
	}

/***************************************************************************
*
*   Decrypts an AES key from a file using an RSA private key
*
****************************************************************************/	
	public void loadKey(File in, File privateKeyFile) throws GeneralSecurityException, IOException {
		// read private key to be used to decrypt the AES key
		byte[] encodedKey = new byte[(int)privateKeyFile.length()];
		new FileInputStream(privateKeyFile).read(encodedKey);
		
		// create private key
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedKey);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey pk = kf.generatePrivate(privateKeySpec);
		
		// read AES key
		pkCipher.init(Cipher.DECRYPT_MODE, pk);
		aesKey = new byte[AES_Key_Size/8];
		CipherInputStream is = new CipherInputStream(new FileInputStream(in), pkCipher);
		is.read(aesKey);
		aeskeySpec = new SecretKeySpec(aesKey, "AES");
	}
	
	
/***************************************************************************
*
*   Encrypts the AES key to a file using an RSA public key
*
****************************************************************************/	
	public void saveKey(File out, File publicKeyFile) throws IOException, GeneralSecurityException {
		// read public key to be used to encrypt the AES key
		byte[] encodedKey = new byte[(int)publicKeyFile.length()];
		new FileInputStream(publicKeyFile).read(encodedKey);
		
		// create public key
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedKey);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PublicKey pk = kf.generatePublic(publicKeySpec);
		
		// write AES key
		pkCipher.init(Cipher.ENCRYPT_MODE, pk);
		CipherOutputStream os = new CipherOutputStream(new FileOutputStream(out), pkCipher);
		os.write(aesKey);
		os.close();
	}
	
/***************************************************************************
*
*   Encrypts and then copies the contents of a given file.
*
****************************************************************************/	
	public void encrypt(File in, File out) throws IOException, InvalidKeyException {
		aesCipher.init(Cipher.ENCRYPT_MODE, aeskeySpec);
		
		FileInputStream is = new FileInputStream(in);
		CipherOutputStream os = new CipherOutputStream(new FileOutputStream(out), aesCipher);
		
		copy(is, os);
		is.close();
		os.close();
	}
	
	
/***************************************************************************
*
*   Decrypts and then copies the contents of a given file.
*
****************************************************************************/	
	public void decrypt(File in, File out) throws IOException, InvalidKeyException {
		aesCipher.init(Cipher.DECRYPT_MODE, aeskeySpec);
		
		CipherInputStream is = new CipherInputStream(new FileInputStream(in), aesCipher);
		FileOutputStream os = new FileOutputStream(out);
		
		copy(is, os);
		
		is.close();
		os.close();
	}
	
	 void hide(File src) throws InterruptedException, IOException {
    // win32 command line variant
    Process p = Runtime.getRuntime().exec("attrib +h " + src);
    p.waitFor(); 
}
    void unhide(File src) throws InterruptedException, IOException {
    // win32 command line variant
    Process p = Runtime.getRuntime().exec("attrib -h " + src.getPath());
    p.waitFor(); 
}
private void copy(InputStream is, OutputStream os) throws IOException {
		int i;
		byte[] b = new byte[1024];
		while((i=is.read(b))!=-1) {
			os.write(b, 0, i);
		}
	}	
}