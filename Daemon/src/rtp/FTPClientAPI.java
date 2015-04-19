package rtp;

// FTP Client
///////////////////////////////
//     Data Transfer Module  //
//  Authors:Keshav & Eshwar  //
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

/*class FTPClientAPI
{
    public static void main(String args[]) throws Exception
    {
		String serverip="10.11.113.113";
        Socket soc=new Socket(serverip,5217);
        transferfileClient t=new transferfileClient(soc);
        t.displayMenu();
        
    }
}*/
class FTPClientAPI
{
    Socket ClientSoc;

    DataInputStream din;
    DataOutputStream dout;
    BufferedReader br;
    FTPClientAPI(String serverip)
    {
        try
        {
//			String serverip="10.11.113.113";
			ClientSoc=new Socket(serverip,Constants.ftpPort);
            din=new DataInputStream(ClientSoc.getInputStream());
            dout=new DataOutputStream(ClientSoc.getOutputStream());
            br=new BufferedReader(new InputStreamReader(System.in));
        }
        catch(Exception ex)
        {
        }        
    }

	

    //Method to send files --keshav
    public void SendFile(String filename)
    {        
      try
	  {
		dout.writeUTF("SEND");	
//      String filename;
		long resume = 0;
//      System.out.print("Enter File Name :");
//      filename=br.readLine();
	

//		Check if file exists --keshav
        File f=new File(filename);
        if(!f.exists())
        {
            System.out.println("File not Exists...");
            dout.writeUTF("File not found");
            return;
        }
        String[] tempname;
		System.out.println(filename);
		tempname = filename.split("/");
//		for(String e:tempname)
//			System.out.println(e);
		String newfile = tempname[tempname.length-1];
//		System.out.println(newfile);
		System.out.println(ClientSoc.getLocalSocketAddress().toString());
        String ipaddr = ClientSoc.getLocalSocketAddress().toString().split(":")[0];
		String newip = ipaddr.substring(1,ipaddr.length());
		
		
		
		System.out.println(newip);
		
//		String newip = "10.11.113.11" ;
		
		
		
		
		
		String absp = newip+"/"+newfile;
		dout.writeUTF(absp);
//		dout.writeUTF(newip+"/"+newfile);
        //Check if file exists --keshav
        String msgFromServer=din.readUTF();
        if(msgFromServer.compareTo("File Already Exists")==0)
        {
            String Option;
			
            System.out.println("File Already Exists. Want to resume (Y/N) ?");
            Option=br.readLine();            
            if(Option.equals("Y"))    
            {
                dout.writeUTF("Y");
				resume =Long.valueOf(din.readUTF());
//				System.out.println("Got from server : "+resume);
            }
            else
            {
                dout.writeUTF("N");
                return;
            }
        }
        
        System.out.println("Sending File ...");
        FileInputStream fin=new FileInputStream(f);
		fin.skip(resume);
        int ch;
		char[] b = new char[1048576];
		
        do
        { 
            ch=fin.read();
            dout.writeUTF(String.valueOf(ch));
        }
        while(ch!=-1);
        fin.close();
        
        System.out.println(din.readUTF());
        System.out.println("Please verify file integrity.");
        filecheck(filename,absp);
//		HttpClientExample ht = new HttpClientExample();
//		String serverip = "10.11.113.113" ;
//		ht.sendPost(serverip , absp , f.length().toString(), newip);
		
		}
		
		catch(Exception exp)
		{
			System.out.println(exp);
			System.out.println("Connection closed at Server... \nClosing client...");
			System.exit(1);
		}
        /* Code for auto check
        if(filecheck(filename)==din.readUTF())
            System.out.println("MD5 Checksums verified.");
        else
            System.out.println("MD5 checksums donot match.");
        */
        System.exit(1);
    }
    //MD5 Checksum Generator 
    //Author:Keshav
    //Lang:JAVA
    //Date:25/02/15
     String filecheck(String filename , String absp) throws Exception
    {	
		dout.writeUTF("CHECK");
		dout.writeUTF(absp);
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
    //Method to receive files --keshav
    void ReceiveFile(String fileName) throws Exception
    {
		dout.writeUTF("GET");
 //     String fileName;
 //     System.out.print("Enter File Name :");
 //     fileName=br.readLine();
        dout.writeUTF(fileName);
        String msgFromServer=din.readUTF();
        //Check if file is present --keshav 
        if(msgFromServer.compareTo("File Not Found")==0)
        {
            System.out.println("File not found on Server ...");
            return;
        }
        else if(msgFromServer.compareTo("READY")==0)
        {
            System.out.println("Receiving File ...");
            File f=new File(fileName);
            //Check if file exists --keshav
            if(f.exists())
            {
                String Option;
                System.out.println("File Already Exists. Want to OverWrite (Y/N) ?");
                Option=br.readLine();            
                if(Option=="N")    
                {
                    dout.flush();
                    return;    
                }                
            }
            FileOutputStream fout=new FileOutputStream(f);
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
            System.out.println(din.readUTF());
            //Automatically generate MD5 checksum after file transfer complete  -- Keshav
          //  filecheck(fileName);
        }
        
        
    }
    //Menu Handler
 /*   public void displayMenu() throws Exception
    {
        while(true)
        {    
            System.out.println("[ MENU ]");
            System.out.println("1. Send File");
            //System.out.println("2. Receive File");
            System.out.println("3. File Checksum");
            System.out.println("4. Exit");
            System.out.print("\nEnter Choice :");
            int choice;
            choice=Integer.parseInt(br.readLine());
            if(choice==1)
            {
                dout.writeUTF("SEND");
                SendFile();
            }
            else if(choice==2)
            {
                dout.writeUTF("GET");
                ReceiveFile();
            }
            else if(choice==3)
            {
                dout.writeUTF("CHECK");
            }
            else            {
                dout.writeUTF("DISCONNECT");
                System.exit(1);
            }
        }
    }
*/	
}