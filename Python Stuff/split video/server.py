import os
import time
import socket as soc

# Create server socket and bind to local machine
s=soc.socket(soc.AF_INET,soc.SOCK_STREAM)
s.bind(("localhost",2345))
s.listen(5)

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

# Open video file and send it to the client in parts
f = open('1.mp4','rb')
for i in range(0,os.path.getsize('1.mp4'),10*1024*1024):
	first = f.read(10*1024*1024)
	s1 = client.recv(15)
	# if("GET" in s1):
	# 	sec = s1.strip("\n").split(":")[1].strip(" ")
	# 	print sec
	# 	req = send_byte(int(sec))
	# 	i = req
	client.send(first)
client.close()
print("Sent Video!")
	
