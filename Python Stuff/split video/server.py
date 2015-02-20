import os,time,socket as soc
first=open('1.mp4','rb').read()
s=soc.socket(soc.AF_INET,soc.SOCK_STREAM)
s.bind(("192.168.1.2",2345))
s.listen(1)
client,add=s.accept()
for i in range(0,os.path.getsize('1.mp4'),1024*1024):
	client.send(first[i:i+1024*1024])
client.close()
print("finally done")
	