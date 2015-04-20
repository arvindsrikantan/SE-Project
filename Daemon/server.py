import os
import time
import socket as soc
import sys

# Create server socket and bind to local machine
try:
	s=soc.socket(soc.AF_INET,soc.SOCK_STREAM)
	s.bind((sys.argv[1],int(sys.argv[2])))
	s.listen(5)
	print("Started video streaming socket...")
except Exception:
	print("Socket already in use!")
	s.close()
	sys.exit()

# def send_byte(secs):
# 	"""
# 	Returns the requested byte number
# 	"""
# 	sec = secs	# Time in seconds to seek to
# 	abr = 132000	# Audio bit rate (bps)
# 	vbr = 812000	# Video bit rate (bps)
# 	ovrb = abr + vbr	# Overall bit rate
# 	seek = sec * ovrb	# Get the bit to seek to
# 	return seek

# 
client,add=s.accept()
print("Got video stream request...")
s1 = client.recv(15)
if("GET" in s1):
	filename = s1.strip("\n").split(":")[1].strip(" ")
# Open video file and send it to the client in parts
if(filename != ""):
	f = open(filename,'rb')
	s.send("SENT")
	for i in range(0,os.path.getsize(filename),10*1024*1024):
		first = f.read(10*1024*1024)
		# 	print sec
		# 	req = send_byte(int(sec))
		# 	i = req
		client.send(first)
client.close()
s.close()
print("Sent Video!")
	
