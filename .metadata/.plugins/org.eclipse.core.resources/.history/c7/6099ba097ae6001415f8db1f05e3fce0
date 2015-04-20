package rtp;

// FTP Server
///////////////////////////////
//     Data Transfer Module  //
//       Author:Keshav       //
///////////////////////////////

//Networking Library
import java.net.*;
//Input/Output Library
import java.io.*;
//Utility Library
import java.util.*;
//File Integrity Library
import java.security.*;

import constants.Constants;

public class FTPServerAPI extends Thread
{
    public void run()
    {
        ServerSocket soc=null;
		try
		{
			soc = new ServerSocket(Constants.ftpPort);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
        System.out.println("FTP Server Started");
        while(true)
        {
            System.out.println("Waiting for Connection ...");
            try
			{
				transferfile t=new transferfile(soc.accept());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
            
        }
    }
}

class transferfile extends Thread
{
    Socket ClientSoc;

    DataInputStream din;
    DataOutputStream dout;
    
    transferfile(Socket soc)
    {
        try
        {
            ClientSoc=soc;                        
            din=new DataInputStream(ClientSoc.getInputStream());
            dout=new DataOutputStream(ClientSoc.getOutputStream());
            System.out.println("FTP Client Connected ...");
            start();
            
        }
        catch(Exception ex)
        {
        }        
    }
/*    //Method to send file --keshav
    void SendFile() throws Exception
    {        
        String filename=din.readUTF();
        File f=new File(filename);
        //Check is file exists --keshav
        if(!f.exists())
        {
            dout.writeUTF("File Not Found");
            return;
        }
        else
        {
            //Send keyword to client --keshav
            dout.writeUTF("READY");
            FileInputStream fin=new FileInputStream(f);
            int ch;
            do
            {
                ch=fin.read();
                dout.writeUTF(String.valueOf(ch));
            }
            while(ch!=-1);    
            fin.close();    
            dout.writeUTF("File Receive Successfully");
            //Show File integrity details -- keshav
            System.out.println("Please verify file integrity.");
            filecheck(filename);                            
        }
    }
*/    //MD5 Checksum Generator 
    //Author:Keshav
    //Lang:JAVA
    //Date:28/02/15
    String filecheck(String filename) throws Exception
    {
        MessageDigest md = MessageDigest.getInstance("MD5");
        try (InputStream is = new FileInputStream(filename))
        {
          DigestInputStream dis = new DigestInputStream(is, md);
          //Buffer Size assign
          byte[] buffer = new byte[1024];
          //Read variable
          int numRead;
          do {
            numRead = is.read(buffer);
              if (numRead > 0) {
              //Update MessageDigest to reflect new data
               md.update(buffer, 0, numRead);
           }
            } while (numRead != -1);   
            is.close();
        byte[] digest = md.digest();
        String result = "";
        //Convert to HEX String
        for (int i=0; i < digest.length; i++) {
           result += Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
       }
        System.out.println("MD5 Hash Value for "+filename+": "+result);
        return result;
      }
      
    }
    //Method to receive files
    void ReceiveFile() throws Exception
    {
        String filename=din.readUTF();
        if(filename.compareTo("File not found")==0)
        {
            return;
        }
        File f=new File(filename);
        String option;
        
        if(f.exists())
        {
            dout.writeUTF("File Already Exists");
            option=din.readUTF();
			System.out.println(f.length());
			String strsize = String.valueOf(f.length());
			dout.writeUTF(strsize);
        }
        else
        {
            dout.writeUTF("SendFile");
            option="Y";
        }
            
            if(option.compareTo("Y")==0)
            {
                FileOutputStream fout=new FileOutputStream(f,true);
                int ch;
                String temp;
                do
                {
                    temp=din.readUTF();
                    ch=Integer.parseInt(temp);
                    if(ch!=-1)
                    {
                        fout.write(ch);                    
                    }
                }while(ch!=-1);
                fout.close();
                dout.writeUTF("File Send Successfully");
                //Automatically generate MD5 checksum after file transfer complete  -- Keshav
                filecheck(filename);
            }
            else
            {
                return;
            }
            
    }


	public void run()
    {
		//int test = 0;
        //Handle user requests
        while(true)
        {
            try
            {
            System.out.println("Waiting for Command ...");
            String Command=din.readUTF();
			//test = Integer.parseInt(Command);
			//System.out.println("The value of test is : " + test);
			//System.out.println("The command is : "+ Command);
 /*           if(Command.compareTo("GET")==0)
            {
                System.out.println("\tGET Command Received ...");
                SendFile();
                continue;
            }
            else */
			if(Command.compareTo("SEND")==0)
            {
                System.out.println("\tSEND Command Receiced ...");                
                ReceiveFile();
                continue;
            }
            else if(Command.compareTo("DISCONNECT")==0)
            {
                System.out.println("\tDisconnect Command Received ...");
                System.exit(1);
            }
            else if(Command.compareTo("CHECK")==0)
            {
                System.out.println("\tChecking test file ...");
				String newf = din.readUTF();
                filecheck(newf);
            }
            }
            catch(Exception ex)
            {
				System.out.println(ex);
				System.out.println("Connection closed at Client....\n Closing the server...");
				System.exit(1);
            }
        }
    }
}