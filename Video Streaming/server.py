import os,time,socket as soc

first=open('1.mp4','rb').read()
s=soc.socket(soc.AF_INET,soc.SOCK_STREAM)
s.bind(("localhost",1264))
s.listen(5)

def send_byte(secs):
	"""
	Returns the requested byte number
	"""
	sec = secs	# Time in seconds to seek to
	abr = 132000	# Audio bit rate (bps)
	vbr = 812000	# Video bit rate (bps)
	ovrb = abr + vbr	# Overall bit rate
	seek = sec * ovrb	# Get the bit to seek to
	return seek

client,add=s.accept()
for i in range(0,os.path.getsize('1.mp4'),10*1024*1024):
	s1 = client.recv(15)
	# if("GET" in s1):
	# 	sec = s1.strip("\n").split(":")[1].strip(" ")
	# 	print sec
	# 	req = send_byte(int(sec))
	# 	i = req
	client.send(first[i:i+10*1024*1024])
client.close()
print("Sent Video!")
	
