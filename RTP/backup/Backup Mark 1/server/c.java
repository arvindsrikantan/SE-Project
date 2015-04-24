import java.io.*;
import java.net.*;

public class c {

   public String getHTML(String urlToRead) {
      URL url;
      HttpURLConnection conn;
      BufferedReader rd;
      String line;
      String result = "";
      try {
         url = new URL(urlToRead);
         conn = (HttpURLConnection) url.openConnection();
         conn.setRequestMethod("GET");
         conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded"); 
         rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
         while ((line = rd.readLine()) != null) {
            result += line;
         }
         rd.close();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
      return result;
   }

   public static void main(String args[]) throws Exception
   {
      c C = new c();
      System.out.println(C.getHTML("http://10.11.113.53:3000/files/get/192.168.1.1"));
      //C.sendPost();
   }
   private void sendPost() throws Exception {
 
      String url = "http://10.11.113.53:3000/files/insert";
      URL obj = new URL(url);
      HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
      //add reuqest header
      con.setRequestMethod("POST");
      //con.setRequestProperty("User-Agent", USER_AGENT);
      con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
      String urlParameters = "absolutepath=sam&ip=192.168.1.1&timestamp=lol&size=1000&originip=someip";
 
      // Send post request
      con.setDoOutput(true);
      DataOutputStream wr = new DataOutputStream(con.getOutputStream());
      wr.writeBytes(urlParameters);
      wr.flush();
      wr.close();
 
      int responseCode = con.getResponseCode();
      System.out.println("\nSending 'POST' request to URL : " + url);
      System.out.println("Post parameters : " + urlParameters);
      System.out.println("Response Code : " + responseCode);
 
      BufferedReader in = new BufferedReader(
              new InputStreamReader(con.getInputStream()));
      String inputLine;
      StringBuffer response = new StringBuffer();
 
      while ((inputLine = in.readLine()) != null) {
         response.append(inputLine);
      }
      in.close();
 
      //print result
      System.out.println(response.toString());
 
   }

}