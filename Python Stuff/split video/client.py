import wx,os,time,socket as soc,mediaplayer as mp
import subprocess
s=soc.socket(soc.AF_INET,soc.SOCK_STREAM)
s.connect(("192.168.1.2",2345))
second=open('2.mp4','wb')
count=0
first = True
while(True):#create thread
    inp = s.recv(1024*1024)
    count+=1
    if(inp==""):
        print("jai")
        break
    else:
        second.write(inp)
        #Start mediaplayer only 1st time
        print("received",count)
        if(first and count>15):#change condition to count>header_length(header_length will be received from tracker
            first = False
            subprocess.Popen("python mediaplayerAnish.py",shell=True)

second.close() 


'''
MplayerCtrl:
	GetVideoBitrate
	GetAudioBitrate
	'''
'''def find_header_length(self):
	length = self.mpc.GetTimeLength()
	self.videoRate = self.mpc.GetVideoBitate()
	self.audioRate = self.mpc.GetAudioBitrate()
	videoSize = length * videoRate/8
	audioSize = length * audioRate/8
	
	filesize = os.path.getsize()
	self.headerLength = filesize - videoSize - audioSize
	return self.headerLength
	
def request_from_byte_value(self,secs):
	self.headerLength + secs * (self.videoRate + self.audioRate)/8'''
