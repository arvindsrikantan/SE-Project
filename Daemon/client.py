import wx
import os
import time
import socket as soc
import subprocess
import multiprocessing
import MplayerCtrl as mpc
import wx.lib.buttons as buttons
import re
import sys

# Setup the diectories and bitmap images.
dirName = os.path.dirname(os.path.abspath(__file__))
bitmapDir = os.path.join(dirName, 'bitmaps')

class Frame(wx.Frame):
    """
    Main media player class.
    """
    def __init__(self, parent, id, title, mplayer,filename,q):
        wx.Frame.__init__(self, parent, id, title)
        self.panel = wx.Panel(self)

        sp = wx.StandardPaths.Get()
        self.currentFolder = sp.GetDocumentsDir()
        self.currentVolume = 50
        self.t_len = None

        # create sizers
        mainSizer = wx.BoxSizer(wx.VERTICAL)
        controlSizer = self.build_controls()
        sliderSizer = wx.BoxSizer(wx.HORIZONTAL)
        self.panel.SetSizer(mainSizer)
        mainSizer.Fit(self.panel)

        self.mplayer = mpc.MplayerCtrl(self.panel, -1, mplayer,filename)

        self.playbackSlider = wx.Slider(self.panel, size=wx.DefaultSize)
        sliderSizer.Add(self.playbackSlider, 1, wx.ALL|wx.EXPAND, 5)
        self.playbackSlider.Bind(wx.EVT_SCROLL_CHANGED, self.seek_setter)
        
        # create volume control
        self.volumeCtrl = wx.Slider(self.panel)
        self.volumeCtrl.SetRange(0, 100)
        self.volumeCtrl.SetValue(self.currentVolume)
        self.volumeCtrl.Bind(wx.EVT_SLIDER, self.on_set_volume)
        controlSizer.Add(self.volumeCtrl, 0, wx.ALL, 5)

        # create track counter
        self.trackCounter = wx.StaticText(self.panel, label="00:00")
        sliderSizer.Add(self.trackCounter, 0, wx.ALL|wx.CENTER, 5)
        
        # set up playback timer
        self.playbackTimer = wx.Timer(self)
        self.Bind(wx.EVT_TIMER, self.on_update_playback)
        

        self.panel.Bind(mpc.EVT_MEDIA_STARTED, self.on_media_started)
        self.Bind(mpc.EVT_MEDIA_FINISHED, self.on_media_finished)
        self.Bind(mpc.EVT_PROCESS_STARTED, self.on_process_started)
        self.Bind(mpc.EVT_PROCESS_STOPPED, self.on_process_stopped)
        
        mainSizer.Add(self.mplayer, 1, wx.ALL|wx.EXPAND, 5)
        mainSizer.Add(sliderSizer, 0, wx.ALL|wx.EXPAND, 5)
        mainSizer.Add(controlSizer, 0, wx.ALL|wx.CENTER, 5)
                
        self.Show()
        self.panel.Layout()

    def build_btn(self, btnDict, sizer):
        """
        Builds the buttons
        """
        bmp = btnDict['bitmap']
        handler = btnDict['handler']
                
        img = wx.Bitmap(os.path.join(bitmapDir, bmp))
        btn = buttons.GenBitmapButton(self.panel, bitmap=img,name=btnDict['name'])
        btn.SetInitialSize()
        btn.Bind(wx.EVT_BUTTON, handler)
        sizer.Add(btn, 0, wx.LEFT, 3)
        
    def build_controls(self):
        """
        Builds the audio bar controls
        """
        controlSizer = wx.BoxSizer(wx.HORIZONTAL)
        
        btnData = [{'bitmap':'player_pause.png', 
                    'handler':self.on_pause, 'name':'pause'},
                   {'bitmap':'player_prev.png',
                    'handler':self.on_stop, 'name':'stop'}]
        for btn in btnData:
            self.build_btn(btn, controlSizer)
            
        return controlSizer
        
    def on_media_started(self, event):
        """
        Fired on media started event
        """
        self.t_len = self.mplayer.GetTimeLength()
        self.playbackSlider.SetRange(0, self.t_len)
        self.playbackTimer.Start(100)
        print("Media started!")
        
        # self.videoRate = self.mplayer.GetVideoBitrate()
        # self.audioRate = self.mplayer.GetAudioBitrate()
        # ar = int(re.search(r"(.*?) kbps",self.audioRate).group(1))
        # vr = int(re.search(r"(.*?) kbps",self.videoRate).group(1))
        # videoSize = self.t_len * vr/8
        # audioSize = self.t_len * ar/8

        # filesize = float(os.path.getsize(filename))
        # print str(filesize) + "," + str(audioSize+videoSize)
        # self.headerLength = filesize - videoSize - audioSize
        # print "----------------------------------------------------"+str(self.headerLength)
        
        
    def on_media_finished(self, event):
        """
        Fired on media end event
        """
        print 'Media finished!'
        self.playbackTimer.Stop()
        
    def on_pause(self, event):
        """
        Fired on pause event
        """
        if(self.t_len==None):
            self.t_len = self.mplayer.GetTimeLength()
            self.playbackSlider.SetRange(0,self.t_len)
            print("tlen",self.t_len)
        if self.playbackTimer.IsRunning():
            print "pausing..."
            self.mplayer.Pause()
            self.playbackTimer.Stop()
        else:
            print "unpausing..."
            self.mplayer.Pause()
            self.playbackTimer.Start()
        
    def on_process_started(self, event):
        """
        Fired on process started event
        """
        print("Process started!")
        
    def on_process_stopped(self, event):
        """
        Fired on process stopped event 
        """
        print("Process stopped!")
        
    def on_set_volume(self, event):
        """
        Sets the volume of the music player
        """
        self.currentVolume = self.volumeCtrl.GetValue()
        self.mplayer.SetProperty("volume", self.currentVolume)

    def seek_setter(self, event):
        """
        Fired on seek event
        """
        currentSeek = self.playbackSlider.GetValue()
        print("change seek",currentSeek)
        q.put(currentSeek)
        self.mplayer.Seek(currentSeek,2)
                
    def on_stop(self, event):
        """
        Fired on stop event
        """
        print("Stopping...")
        self.mplayer.Stop()
        self.playbackTimer.Stop()
        ## Reload file
        self.mplayer.Loadfile("2.mp4")

    def on_update_playback(self, event):
        """
        Updates playback slider and track counter
        """
        try:
            offset = self.mplayer.GetTimePos()
        except:
            return
        # print offset
        mod_off = str(offset)[-1]
        if mod_off == '0':
            # print "mod_off"
            offset = int(offset)
            self.playbackSlider.SetValue(offset)
            secsPlayed = time.strftime('%M:%S', time.gmtime(offset))
            self.trackCounter.SetLabel(secsPlayed)        

    def find_header_length(self):
        """
        Find header length of video
        """
        length = self.mpc.GetTimeLength()
        self.videoRate = self.mpc.GetVideoBitate()
        self.audioRate = self.mpc.GetAudioBitrate()
        videoSize = length * videoRate/8
        audioSize = length * audioRate/8
        filesize = os.path.getsize("Hitman.avi")
        self.headerLength = filesize - videoSize - audioSize
        return self.headerLength
    
def MediaHandler(q):
    """
    Handler to create a new process to play media file
    param : q, is the Queue object shared between client process and media player process that will contain the seek request.
    """
    paths = [r'C:\Program Files (x86)\MPlayer for Windows\mplayer.exe',
             r'/usr/bin/mplayer']
    mplayerPath = None
    for path in paths:
        if os.path.exists(path):
            mplayerPath = path
        
    if not mplayerPath:
        print "mplayer not found!"
        sys.exit()
            
    
    app = wx.App(redirect=False)
    frame = Frame(None, -1, 'Media Player', mplayerPath,sys.argv[3],q)
    app.MainLoop() 


class ClientHandler():
    """
    Handler class to set up client socket connection
    """
    def main(self,q):
		"""
		Main method to create client process
		"""
		s = soc.socket(soc.AF_INET,soc.SOCK_STREAM)
		s.connect((sys.argv[1],int(sys.argv[2])))
		
		s.send("GET:"+sys.argv[3])	# Get filename
		cmd = s.recv(512)
		if(cmd == "SENT"):
			second=open(sys.argv[3],'wb')
			count=0
			first = True
			while(True):#create thread
				if(q.empty() == True):
					s.send("\n")
				else:
					s.send("GET:"+str(q.get()))
				inp = s.recv(10*1024*1024)
				count+=1
				if(inp==""):
					print("Got video!")
					break    
				else:
					second.write(inp)
					#Start mediaplayer only 1st time
					if(first and count>3):#change condition to count>header_length
						first = False
						p2 = multiprocessing.Process(target=MediaHandler,args=(q,))
						p2.start()     
		else:
			raise Exception("Server didnt respond!")
		second.close()    
		s.close()

if __name__=="__main__":
    # Start the main process
    q = multiprocessing.Queue()
    ch = ClientHandler()
    ch.main(q)
