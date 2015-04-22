import os
import time
import socket as soc
import sys


def main():
	"""
	Create server socket and bind to local machine for video streaming
	Uses command line args:
	argv[1] : Host IP
	argv[2] : Port number
	"""
	try:
		s=soc.socket(soc.AF_INET,soc.SOCK_STREAM)
		s.bind((sys.argv[1],int(sys.argv[2])))
		s.listen(5)
		print("Started video streaming socket!")
	except Exception:
		print("Socket already in use!")
		s.close()
		sys.exit()

	# Accept socket connection from client
	client,add=s.accept()
	print("Got video stream request...")
	s1 = client.recv(15)

	# Wait for client to send filename
	if("GET" in s1):
		filename = s1.strip("\n").split(":")[1].strip(" ")

	# Open video file and send it to the client in parts
	if(filename != ""):
		f = open(filename,'rb')
		client.send("SENT")
		for i in range(0,os.path.getsize(filename),10*1024*1024):
			first = f.read(10*1024*1024)
			client.send(first)

	# Close socket connection after the entire file is sent
	client.close()
	s.close()
	print("Sent Video!")

if __name__ == '__main__':
	main()
	
