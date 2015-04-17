import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.FileSystem;
import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.LinkOption.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.util.*; 
import java.io.File;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * Example to watch a directory (or tree) for changes to files.
 */
 
public class WatchDir {

   private final WatchService watcher;
   private final Map<WatchKey,Path> keys;
   private final boolean recursive;
   private boolean trace = false;//?
   

   
   @SuppressWarnings("unchecked")
   static <T> WatchEvent<T> cast(WatchEvent<?> event) {
      return (WatchEvent<T>)event;
   }

   /**
    * Register the given directory with the WatchService
    */
   private void register(Path dir) throws IOException {
      WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
      if (trace) {
         Path prev = keys.get(key);//?
         if (prev == null) {
            System.out.format("register: %s\n", dir);//?
         } 
         else {
            if (!dir.equals(prev)) {
               System.out.format("update: %s -> %s\n", prev, dir);//?
            }
         }
      }
      keys.put(key, dir);//?
   }

   /**
    * Register the given directory, and all its sub-directories, with the
    * WatchService.
    */
   private void registerAll(final Path start) throws IOException {
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
   WatchDir(Path dir, boolean recursive) throws IOException {
      this.watcher = FileSystems.getDefault().newWatchService();
      this.keys = new HashMap<WatchKey,Path>();
      this.recursive = recursive;
   
      if (recursive) {
         System.out.format("Scanning %s ...\n", dir);
         registerAll(dir);
         System.out.println("Scanning "+dir+" Done.");
      } 
      else {
         register(dir);
      }
   
      // enable trace after initial registration
      this.trace = true;//?
   }

   /**
    * Process all events for keys queued to the watcher
    */
   void processEvents() {
      for (;;) {
      
         // wait for key to be signalled
         WatchKey key;
         try {
            key = watcher.take();
         } 
         catch (InterruptedException x) {
            return;
         }
      
         Path dir = keys.get(key);
         if (dir == null) {
            System.err.println("WatchKey not recognized!!");//?
            continue;
         }
      
         for (WatchEvent<?> event: key.pollEvents()) {
            WatchEvent.Kind kind = event.kind();
         
            // TBD - provide example of how OVERFLOW event is handled
            if (kind == OVERFLOW) {
               continue;
            }
         
            // Context for directory entry event is the file name of entry????
            WatchEvent<Path> ev = cast(event);
            Path name = ev.context();
            Path child = dir.resolve(name);
         
            // print out event
            System.out.format("%s: %s\n", event.kind().name(), child);
            
            //System.out.println(child.getClass().getSimpleName());
            
            String y=child.toString();
 
            
            if(kind==ENTRY_MODIFY)//?
            {
               File file = new File(y);
               
               if (!file.exists() || !file.isFile()) 
                  System.out.println("File doesn\'t exist");
               else//?
               
               
               {
             /*
               new Thread(new Runnable() 
               {
                 public void run() 
                 {
               
                  File file = new File("c:");//?
                  System.out.println("Filesize in bytes: " + file.length());//?
                  
                  long usableSpace = file.getUsableSpace(); ///unallocated / free disk space in bytes.
                  
                  //long freeSpace = file.getFreeSpace(); //unallocated / free disk space in bytes.
                 
                  //System.out.println("Space free : " + usableSpace + " bytes");
                  //System.out.println("Space free : " + freeSpace + " bytes");
       
                  
                  
               String url = "http://10.11.113.53:3000/device/insert";
               
               HttpClient client = new DefaultHttpClient();
               HttpPost post = new HttpPost(url);
               
               List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
               
                String ipaddr=InetAddress.getLocalHost().getHostAddress();
                String fp=usablespace.toString();
                
               		urlParameters.add(new BasicNameValuePair("ip",ipaddr));
               		urlParameters.add(new BasicNameValuePair("freesize",fp));
               
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
               while ((line = rd.readLine()) != null) {
               result.append(line);
               }
               
               System.out.println(result.toString());
               
               }
               
               }).start();
                  */
               }
                        
            // if directory is created, and watching recursively, then
            // register it and its sub-directories
            
               if (recursive && (kind == ENTRY_CREATE)) {
                  try {
                     if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
                        registerAll(child);
                     }
                  } 
                  catch (IOException x) {
                  // ignore to keep sample readable
                  }
               }
            }
         
         // reset key and remove from set if directory no longer accessible??
            boolean valid = key.reset();
            if (!valid) 
            {
               keys.remove(key);
            
            // all directories are inaccessible
               if (keys.isEmpty()) 
               {
                  break;
               }
            }
         }
      }
   }
   public static void main(String[] args) throws IOException// 
   {
	      long total=0,usableSpace;
	      
	  File[] drives = File.listRoots();
	  
      if (drives != null && drives.length > 0) 
      {
         for (File aDrive : drives)
         {  
         
            final String x;
            x=aDrive.toString().substring(0,2);//
            System.out.println(x);
            
            File file = new File(x);//?
            
      //      System.out.println("Filesize in bytes: " + file.length());
            
           usableSpace = file.getUsableSpace(); ///unallocated / free disk space in bytes.
            
           total+=usableSpace;//?
            
            // register directory and process its events           
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
                           try //?
                           {
                              Thread.sleep(2);
                           } 
                           catch (InterruptedException e1) 
                           {
                           // TODO Auto-generated catch block
                              e1.printStackTrace();
                           }
                           Path dir = Paths.get(x);
                           try 
                           {
                              new WatchDir(dir, true).processEvents();
                           } 
                           catch (IOException e1) 
                           {
                           // TODO Auto-generated catch block
                              e1.printStackTrace();
                           }//?
                        }
                        catch (Exception e) 
                        {
                           e.printStackTrace();
                           System.out.println(e.getMessage());
                        }
                     }
             }).start();
         }
         /*new Thread(new Runnable() 
         {
           public void run() 
           {*/
         
         System.out.println(total/1024);//in KBs
         
        String url = "http://10.11.113.53:3000/device/insert";//
         
        @SuppressWarnings("deprecation")//
         
        HttpClient client = new DefaultHttpClient();//
        HttpPost post = new HttpPost(url);
         
         List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
         
          String ipaddr=InetAddress.getLocalHost().getHostAddress();
          
          String fp=Long.toString(total/1024);// in KBs
          
         		urlParameters.add(new BasicNameValuePair("ip",ipaddr));
         		urlParameters.add(new BasicNameValuePair("freesize",fp));
         
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
         {
         result.append(line);
         }
         
         System.out.println(result.toString());
         
          /*}         
         
         }).start();*/

      }//
   }
}
