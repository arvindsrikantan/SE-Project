/***************************************************************************
*
*       SOFTWARE ENGINEERING PROJECT - 12CS354 - VI SEM BE (PESIT)
*
*
*       NETWORK STORAGE - SE PROJECT TEAM 1
*
*
*       JOB     - HOOKS
*
*
*       AUTHORS - SURAJ PAI(1PI12CS064)
*               - AKASH AGARWAL(1PI12CS017)
*
*
*       TASK    - To recursively register a "watch" on certain directories 
*                 as a whole ,present on any given system, connected onto the 
*                 same common network as the Tracker Server,looking for any kind of 
*                 changes in the file systems & POST(reflect) the same back
*                 to the tracker for the corresponding updations
*                 required in it,for any future references.
*                 
*                
*       START   - February 13th,2015
*
****************************************************************************/

package hooks;

/***************************************************************************
*
*   The following statements are used to import the various modules
*   which are required for the prooject.
*
****************************************************************************/

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystem;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.LinkOption.*;

import java.nio.file.attribute.*;
import java.io.*;
import java.security.Timestamp;
import java.util.*; 
import java.io.File;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import constants.Constants;

/**
 *  To watch a directory (or tree) for changes to files.
 */
 
public class WatchDir 
{

   private final WatchService watcher;
   private final Map<WatchKey,Path> keys;
   
   private final boolean recursive;
   private boolean trace = false;
      
   @SuppressWarnings("unchecked")
   static <T> WatchEvent<T> cast(WatchEvent<?> event) 
   {
      return (WatchEvent<T>)event;
   }

   /**
    * Register the given directory with the WatchService
    */
    
   private void register(Path dir) throws IOException 
   {
      WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
      if (trace) 
      {
         Path prev = keys.get(key);
         
         if (prev == null) 
            System.out.format("register: %s\n", dir);
            
         else 
         {
            if (!dir.equals(prev)) 
               System.out.format("update: %s -> %s\n", prev, dir);
         }
      }
      keys.put(key, dir);
   }

   /**
    * Register the given directory, and all its sub-directories, with the
    * WatchService.
    */
    
   private void registerAll(final Path start) throws IOException 
   {
      // register directory and sub-directories
      Files.walkFileTree(start,
            new SimpleFileVisitor<Path>() 
            {
               @Override
               public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)throws IOException
               {
                  register(dir);
                  return FileVisitResult.CONTINUE;
               }
            });
   }

   /**
    * Creates a WatchService and registers the given directory
    */
   
   WatchDir(Path dir, boolean recursive) throws IOException 
   {
      this.watcher = FileSystems.getDefault().newWatchService();
      this.keys = new HashMap<WatchKey,Path>();
      this.recursive = recursive;
   
      if (recursive) 
      {
         System.out.format("Scanning %s ...\n", dir);
         registerAll(dir);
         System.out.println("Scanning "+dir+" Done.");
      } 
      
      else 
         register(dir);

      // enable trace after initial registration
      this.trace = true;
   }

   /**
    * Process all events for keys queued to the watcher
    */
    
   void processEvents() 
   {
      for (;;) //Infinite loop
      {
         // wait for key to be signalled
         WatchKey key;
         
         try 
         {
            key = watcher.take();
         } 
         catch (InterruptedException x) 
         {
            return;
         }
      
         Path dir = keys.get(key);
         if (dir == null) 
         {
            System.err.println("WatchKey not recognized!!");
            continue;
         }
      
         for (WatchEvent<?> event: key.pollEvents()) 
         {
            WatchEvent.Kind kind = event.kind();
         
            // TBD - provide example of how OVERFLOW event is handled
            if (kind == OVERFLOW) 
               continue;
               
            // Context for directory entry event is the file name of entry????
            WatchEvent<Path> ev = cast(event);
            Path name = ev.context();
            Path child = dir.resolve(name);
         
            // print out event
            System.out.format("%s: %s\n", event.kind().name(), child);
            
            //System.out.println(child.getClass().getSimpleName());
            
            String y=child.toString();
         
            /*ENTRY_MODIFY->
             * 				updation of the file contents->
             * 				just renaming->
             * If the old & the new absolute paths are the same,then updation=>send the new free size,new file size,Absolute path & IP Address
             * else just send the Absolute path of the file renamed
             */
            
            if(kind==ENTRY_MODIFY)
            {
                
               File file = new File(y);
               
               if (!file.exists() || !file.isFile()) 
                  System.out.println("File doesn\'t exist");
               
               else
               {
                  long fl=file.length();
                  String fl1=Long.toString(fl);
                  
                  try 
                  {
                     postAbsolutePath(file.getAbsolutePath(),fl1,"updatesize");
                     postFreeAvailableSize("update");						
                  }
                  
                  catch (Exception e) 
                  {
                  // TODO Auto-generated catch block
                     e.printStackTrace();
                  }
               }
            }
            
 //on ENTRY_DELETE, post the Absolute path of the file which got deleted alongwith the IP Address,& the total free size now available
               
            else if(kind==ENTRY_DELETE)
            {
                   
               File file = new File(y);
                  
               if (!file.exists() || !file.isFile())
                  System.out.println("File doesn\'t exist");
                  
               else  
               {
                  try 
                  {
                     postAbsolutePath(file.getAbsolutePath(),"0","delete");
                     postFreeAvailableSize("update");
                  }
                  catch (Exception e) 
                  {
                  // TODO Auto-generated catch block
                     e.printStackTrace();
                  }
                	  
               }
            }
                        
            // If directory is created & watching recursively, then register it & its sub-directories
            
            if (recursive && (kind == ENTRY_CREATE)) 
            {
               try
               {
                  if (Files.isDirectory(child, NOFOLLOW_LINKS)) 
                     registerAll(child);
   
                  File file = new File(y);
                     
                  if (!file.exists() || !file.isFile())
                     System.out.println("File doesn\'t exist");
                     
                  else
                  {
                  
                     String ipaddr=InetAddress.getLocalHost().getHostAddress();
                         
                     Path f1 =Paths.get(y);
                         
                     BasicFileAttributes attr = Files.readAttributes(f1, BasicFileAttributes.class);
                     String ts=attr.creationTime().toString();
                         
                     String c="0,"+ts+","+ipaddr;
                         
                     postAbsolutePath(file.getAbsolutePath(),c,"insert");
                  }   
               } 
               catch (Exception x) 
               {
                  // ignore
               }
            }
         }
         
         // reset key & remove from set if directory no longer accessible
         
         boolean valid = key.reset();
         if (!valid) 
         {
            keys.remove(key);
            
            // all directories are inaccessible
            
            if (keys.isEmpty()) 
               break;
         }
      }
   }
   
   public static void postAbsolutePath(String ap,String s,String u) throws Exception
   {
      String url="http://"+Constants.serverIp+":3000/files/";
   
      HttpClient client = new DefaultHttpClient();
    
      HttpPost post = new HttpPost(url);
     
      List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
     
      String ipaddr=InetAddress.getLocalHost().getHostAddress();
      
      urlParameters.add(new BasicNameValuePair("ip",ipaddr));
      urlParameters.add(new BasicNameValuePair("absolutepath",ap));
      
      if(u.equals("insert"))
      {
         String a[]=s.split(",");
      			
         urlParameters.add(new BasicNameValuePair("size",a[0]));
         urlParameters.add(new BasicNameValuePair("timestamp",a[1]));
         urlParameters.add(new BasicNameValuePair("originip",a[2]));
      }
      
      else
         urlParameters.add(new BasicNameValuePair("size",s));
         
      post.setEntity(new UrlEncodedFormEntity(urlParameters));
     
      HttpResponse response = client.execute(post);
      System.out.println("\nSending 'POST' request to URL : " + url);
      System.out.println("Post parameters : " + post.getEntity());
      System.out.println("Response Code : " + 
                          response.getStatusLine().getStatusCode());
     
      BufferedReader rd = new BufferedReader(
              new InputStreamReader(response.getEntity().getContent()));
     
      StringBuffer result = new StringBuffer();
      String line = "";
      
      while ((line = rd.readLine()) != null) 
         result.append(line);
         
      System.out.println(result.toString());
   }
   
   public static void postFreeAvailableSize(String u) throws Exception
   {
   
      //String url="http://IP_ADDRESS:3000/device/"+u;
      
      String url = "http://"+Constants.serverIp+":3000/device/"+u;
      
      HttpClient client = new DefaultHttpClient();
       
      HttpPost post = new HttpPost(url);
        
      List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        
      String ipaddr=InetAddress.getLocalHost().getHostAddress();
      String fp=getTotalFreeAvailableSize();
         
      urlParameters.add(new BasicNameValuePair("ip",ipaddr));
      urlParameters.add(new BasicNameValuePair("freesize",fp));
      urlParameters.add(new BasicNameValuePair("hash",constants.Constants.myName));
       
      post.setEntity(new UrlEncodedFormEntity(urlParameters));
        
      HttpResponse response = client.execute(post);
      System.out.println("\nSending 'POST' request to URL : " + url);
      System.out.println("Post parameters : " + post.getEntity());
      System.out.println("Response Code : " + 
                             response.getStatusLine().getStatusCode());
        
      BufferedReader rd = new BufferedReader(
                 new InputStreamReader(response.getEntity().getContent()));
        
      StringBuffer result = new StringBuffer();
      String line = "";
      
      while ((line = rd.readLine()) != null) 
         result.append(line);
         
      System.out.println(result.toString());
   }
   
   public static String getTotalFreeAvailableSize()
   {
      long total=0,usableSpace;
         
      File[] drives = File.listRoots();
   	  
      if (drives != null && drives.length > 0) 
      {
         for (File aDrive : drives)
         {  
            
            final String x;
            x=aDrive.toString().substring(0,2);
            System.out.println(x);
               
            File file = new File(x);
               
            usableSpace = file.getUsableSpace(); ///unallocated / free disk space in bytes.
               
            total+=usableSpace;
         }
      }
      total/=1024;//in KBs
      
      return Long.toString(total);
   }
   
   public static void startHooks() throws IOException,Exception
   {
      System.out.println("Started hooks service...");
      
      postFreeAvailableSize("insert");
      
      File[] drives = File.listRoots();
     
      if (drives != null && drives.length > 0) 
      {
         for (File aDrive : drives)
         {  
         
            final String x;
            x=aDrive.toString().substring(0,2);
            System.out.println(x);
               
            // Register directory & process its events
            
            new Thread(
                  new Runnable() 
                  {
                     public void run() 
                     {
                        try
                        {
                           Path dir = Paths.get(x);
                           new WatchDir(dir, true).processEvents();
                        }
                        catch (AccessDeniedException e) 
                        {
                           /*try
                           {
                              Thread.sleep(2);
                           } 
                           catch (InterruptedException e1) 
                           {
                              //TODO Auto-generated catch block
                              //e1.printStackTrace();
                           }
                           Path dir = Paths.get(x);
                           try 
                           {
                              new WatchDir(dir, true).processEvents();
                           } 
                           catch (IOException e1) 
                           {
                              //TODO Auto-generated catch block
                              //e1.printStackTrace();
                           }*/
                        }
                        catch (Exception e) 
                        {
                           //e.printStackTrace();
                           //System.out.println(e.getMessage());
                        }
                     }
                  }).start();
         }
      }
   }
}
