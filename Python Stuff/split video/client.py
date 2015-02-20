import os,time,socket as soc
s=soc.socket(soc.AF_INET,soc.SOCK_STREAM)
s.connect(("192.168.1.2",2345))
second=open('2.mp4','wb')
while(True):
	second.write(s.recv(1024*1024))
second.close()
print("finally done")


'''
MplayerCtrl:
	GetVideoBitrate
	GetAudioBitrate
	'''
def find_header_length(self):
	length = self.mpc.GetTimeLength()
	self.videoRate = self.mpc.GetVideoBitate()
	self.audioRate = self.mpc.GetAudioBitrate()
	videoSize = length * videoRate/8
	audioSize = length * audioRate/8
	
	filesize = os.path.getsize()
	self.headerLength = filesize - videoSize - audioSize
	return self.headerLength
	
def request_from_byte_value(self,secs):
	self.headerLength + secs * (self.videoRate + self.audioRate)/8