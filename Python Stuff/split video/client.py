import wx,os,time,socket as soc,mediaplayer as mp
import subprocess
s=soc.socket(soc.AF_INET,soc.SOCK_STREAM)
s.connect(("localhost",1231))
second=open('2.mp4','wb')
first = True
while(True):#create thread
    inp = s.recv(1024*1024)
    if(inp==""):
        print("jai")
        break
    else:
        second.write(inp)
        #Start mediaplayer only 1st time
        print("received")
        if(first):
            first = False
            subprocess.Popen("python mediaplayer.py",shell=True)
        #     paths = [r'C:\Program Files (x86)\MPlayer for Windows\mplayer.exe',
        #     r'/usr/bin/mplayer']
        #     mplayerPath = None
        #     for path in paths:
        #         if os.path.exists(path):
        #             mplayerPath = path

        #     if not mplayerPath:
        #         print "mplayer not found!"
        #         sys.exit()
                    
        #     app = wx.App(redirect=False) 
        #     frame = mp.Frame(None, -1, 'RTSP', mplayerPath)
        #     app.MainLoop()
        # frame.loadFile("./2.mp4")

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
