import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.LinkOption.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.util.*;
 
public class HttpClientExample 
{
 
	private final String USER_AGENT = "Mozilla/5.0";
 
	public static void main(String[] args) throws Exception 
	{
 
		HttpClientExample http = new HttpClientExample();
 
		// System.out.println("Testing 1 - Send Http GET request");
		// http.sendGet();
 
		//System.out.println("\nTesting 2 - Send Http POST request");
		http.sendPost();
	}
 
	// HTTP GET request
	private void sendGet() throws Exception {
 
		String url = "http://www.google.com/search?q=developer";
 
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
 
		// add request header
		request.addHeader("User-Agent", USER_AGENT);
 
		HttpResponse response = client.execute(request);
 
		System.out.println("\nSending 'GET' request to URL : " + url);
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
 
	// HTTP POST request
	private void sendPost() throws Exception {
 /*
		String url = "http://10.11.113.53:3000/device/insert";
 
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
				
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		//urlParameters.add(new BasicNameValuePair("ip",ipaddr));
		
		//urlParameters.add(new BasicNameValuePair("freesize",fp));
 
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
 
		System.out.println(result.toString());*/
 
	}
}
