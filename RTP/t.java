import java.io.*;
import java.net.*;
import java.util.Enumeration;
class t
{
	public static void main(String args[])
	{
	String ip;
    try {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface iface = interfaces.nextElement();
         //filters out 127.0.0.1 and inactive interfaces
              if (iface.isLoopback() || !iface.isUp())
                  continue;

            Enumeration<InetAddress> addresses = iface.getInetAddresses();
            while(addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                ip = addr.getHostAddress();
                System.out.println(ip);
                break;
                              }
           break;
           }
        
    } catch (SocketException e) {
        throw new RuntimeException(e);
    }
}
}