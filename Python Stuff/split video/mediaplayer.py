import os
import time
import wx
import MplayerCtrl as mpc
import wx.lib.buttons as buttons
import re,sys

dirName = os.path.dirname(os.path.abspath(__file__))
bitmapDir = os.path.join(dirName, 'bitmaps')

class Frame(wx.Frame):
    
    #----------------------------------------------------------------------
    def __init__(self, parent, id, title, mplayer,filename):
        wx.Frame.__init__(self, parent, id, title)
        self.panel = wx.Panel(self)
        
        sp = wx.StandardPaths.Get()
        self.currentFolder = sp.GetDocumentsDir()
        self.currentVolume = 50
        self.t_len = None

        #self.create_menu()
        
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
        #self.Bind(mpc.EVT_MEDIA_STARTED, self.media_started)
        
        #-- Uncomment to start playing video automatically instead of button click --#
        # wx.CallAfter(self.loadFile)


    # def loadFile(self):
    #     self.mplayer.Loadfile("./2.mp4")
    #     self.t_len = self.mplayer.GetTimeLength()
    #     print("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",self.t_len)
    #     # self.playbackSlider.SetRange(0, t_len)
    #     # self.playbackTimer.Start(100)
        
    # def media_started(self,evt):
    #     print("hopefully works")
        
    #----------------------------------------------------------------------
    def build_btn(self, btnDict, sizer):
        """"""
        bmp = btnDict['bitmap']
        handler = btnDict['handler']
                
        img = wx.Bitmap(os.path.join(bitmapDir, bmp))
        btn = buttons.GenBitmapButton(self.panel, bitmap=img,
                                      name=btnDict['name'])
        btn.SetInitialSize()
        btn.Bind(wx.EVT_BUTTON, handler)
        sizer.Add(btn, 0, wx.LEFT, 3)
        
    #----------------------------------------------------------------------
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
    
    #----------------------------------------------------------------------
    # def create_menu(self):
    #     """
    #     Creates a menu
    #     """
    #     menubar = wx.MenuBar()
    #     fileMenu = wx.Menu()
    #     add_file_menu_item = fileMenu.Append(wx.NewId(), "&Add File", "Add Media File")
    #     menubar.Append(fileMenu, '&File')
        
    #     self.SetMenuBar(menubar)
    #     self.Bind(wx.EVT_MENU, self.on_add_file, add_file_menu_item)
        
    # #----------------------------------------------------------------------
    # def on_add_file(self, event):
    #     """
    #     Add a Movie and start playing it
    #     """
    #     #help(self.mplayer)
    #     wildcard = "Media Files (*.*)|*.*"
    #     dlg = wx.FileDialog(
    #         self, message="Choose a file",
    #         defaultDir=self.currentFolder, 
    #         defaultFile="",
    #         wildcard=wildcard,
    #         style=wx.OPEN | wx.CHANGE_DIR
    #         )
    #     if dlg.ShowModal() == wx.ID_OK:
    #         path = dlg.GetPath()
    #         self.currentFolder = os.path.dirname(path[0])
    #         trackPath = '"%s"' % path.replace("\\", "/")
    #         self.mplayer.Loadfile(trackPath)
    #         #time.sleep(5)
        
    #----------------------------------------------------------------------
    def on_media_started(self, event):
        self.t_len = self.mplayer.GetTimeLength()
        self.playbackSlider.SetRange(0, self.t_len)
        self.playbackTimer.Start(100)
        print 'Media started!'
        
        self.videoRate = self.mplayer.GetVideoBitrate()
        self.audioRate = self.mplayer.GetAudioBitrate()
        ar = int(re.search(r"(.*?) kbps",self.audioRate).group(1))
        vr = int(re.search(r"(.*?) kbps",self.videoRate).group(1))
        videoSize = self.t_len * vr/8
        audioSize = self.t_len * ar/8

        filesize = float(os.path.getsize(filename))
        print str(filesize) + "," + str(audioSize+videoSize)
        self.headerLength = filesize - videoSize - audioSize
        print "----------------------------------------------------"+str(self.headerLength)
        
        
    #----------------------------------------------------------------------
    def on_media_finished(self, event):
        print 'Media finished!'
        self.playbackTimer.Stop()
        
    #----------------------------------------------------------------------
    def on_pause(self, event):
        """"""
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
        
    #----------------------------------------------------------------------
    def on_process_started(self, event):
        print 'Process started!'
        
    #----------------------------------------------------------------------
    def on_process_stopped(self, event):
        print 'Process stopped!'
        
    #----------------------------------------------------------------------
    def on_set_volume(self, event):
        """
        Sets the volume of the music player
        """
        self.currentVolume = self.volumeCtrl.GetValue()
        self.mplayer.SetProperty("volume", self.currentVolume)

    #----------------------------------------------------------------------
    def seek_setter(self, event):
        """
        Sets the seek
        """
        currentSeek = self.playbackSlider.GetValue()
        print("change seek",currentSeek)
        #call self.request_from_byte_value(currentSeek)
        self.mplayer.Seek(currentSeek,2)
                
    #----------------------------------------------------------------------
    def on_stop(self, event):
        """"""
        print "stopping..."
        self.mplayer.Stop()
        self.playbackTimer.Stop()
        ## Reload file
        self.mplayer.Loadfile("2.mp4")

    #----------------------------------------------------------------------
    def on_update_playback(self, event):
        """
        Updates playback slider and track counter
        """
        try:
            offset = self.mplayer.GetTimePos()
        except:
            return
        print offset
        mod_off = str(offset)[-1]
        if mod_off == '0':
            print "mod_off"
            offset = int(offset)
            self.playbackSlider.SetValue(offset)
            secsPlayed = time.strftime('%M:%S', time.gmtime(offset))
            self.trackCounter.SetLabel(secsPlayed)        

#----------------------------------------------------------------------            
    def find_header_length(self):
        """
        self explanatory
        """
        length = self.mpc.GetTimeLength()
        self.videoRate = self.mpc.GetVideoBitate()
        self.audioRate = self.mpc.GetAudioBitrate()
        videoSize = length * videoRate/8
        audioSize = length * audioRate/8
        filesize = os.path.getsize("Hitman.avi")
        self.headerLength = filesize - videoSize - audioSize
        print "----------------------------------------------------"+self.headerLength
        return self.headerLength
    
    #----------------------------------------------------------------------
    def request_from_byte_value(self,secs):
        """
        Seek from this byte on the server
        """
#        self.headerLength + secs * (self.videoRate + self.audioRate)/8    
    
    #----------------------------------------------------------------------
    def update_seek(self,secs):
        """
        Yet to find the anomality
        """
        self.headerLength + secs * (self.videoRate + self.audioRate)/8   

if __name__ == "__main__":
    import os, sys
    
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
    frame = Frame(None, -1, 'MediaPlayer Using MplayerCtrl', mplayerPath,"2.mp4")
    app.MainLoop()
