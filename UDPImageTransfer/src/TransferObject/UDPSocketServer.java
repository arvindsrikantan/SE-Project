package TransferObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.swing.ImageIcon;

import utils.Addressing;

public class UDPSocketServer
{
	DatagramSocket	socket	= null;

	public UDPSocketServer()
	{

	}

	public void createAndListenSocket()
	{
		try
		{
			socket = new DatagramSocket();
			// byte[] incomingData = new byte[1024];
			int count = 0;
			while (true)
			{
				ImageStructure is = new ImageStructure(count++, new ImageIcon());
				// Replace with the
				// screenshot number and
				// image
				String ip = Addressing.getIpAddress();
				System.out.println(ip);
				String ipParts[] = ip.split("[.]");
				String inetName = ipParts[0] + "." + ipParts[1] + "."
						+ ipParts[2] + ".255";
				System.out.println(inetName);
				InetAddress IPAddress = InetAddress.getByName(inetName);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(outputStream);
				os.writeObject(is);
				byte[] data = outputStream.toByteArray();
				DatagramPacket sendPacket = new DatagramPacket(data,
						data.length, IPAddress, 9876);
				socket.send(sendPacket);
				System.out.println("sending");
			}

		}
		catch (SocketException e)
		{
			e.printStackTrace();
		}
		catch (IOException i)
		{
			i.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		UDPSocketServer server = new UDPSocketServer();
		server.createAndListenSocket();
	}
}
