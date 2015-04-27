/**************************************************************************
 SOFTWARE ENGINEERING PROJECT - 12CS354 - VI SEM BE (PESIT)
*
*       NETWORK STORAGE - SE PROJECT TEAM 1
*
*       JOB     - Graphics User Interface
*
*       AUTHORS - Keshav Pandey
*
*       TASK    - To provide an easy to use interface to provide
*                   all the features available in our SE Project.
*               - To integrate various modules and provide a single
*                 access point
*               - To update tracker on various operations performed
* 

****************************************************************************/

package localnetworkdrive;

import RTP.FTPClientAPI;
import RTP.pathtranslator;
import com.findServer.Main;
import java.awt.HeadlessException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.json.*;
import rtspMirror.RTSPClient;
import rtspVideo.RunRTSPClientPython;

/**
 *
 * @author keshav pandey
 */
public class HomePage extends javax.swing.JFrame {
public String jsonresp;
public JSONArray json;
public static String trackerip;
    /**
     * Creates new form HomePage
     */
    public HomePage() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        file = new javax.swing.JDialog();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        Refbut = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        downloadbut = new javax.swing.JButton();
        uploadbut = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        filelist = new javax.swing.JTable();
        back1 = new javax.swing.JLabel();
        videostream = new javax.swing.JDialog();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        Refbut1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        downloadbut1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        filelist1 = new javax.swing.JTable();
        back2 = new javax.swing.JLabel();
        helpmenu = new javax.swing.JDialog();
        jLabel5 = new javax.swing.JLabel();
        privacybut = new javax.swing.JButton();
        privacybut1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel2 = new javax.swing.JLabel();
        opt1 = new javax.swing.JButton();
        opt2 = new javax.swing.JButton();
        opt3 = new javax.swing.JButton();
        opt4 = new javax.swing.JButton();
        opt5 = new javax.swing.JButton();
        back = new javax.swing.JLabel();

        file.setTitle("Remote File Transfer");
        file.setMinimumSize(new java.awt.Dimension(715, 470));
        file.setName("RTPDialog"); // NOI18N

        jLayeredPane2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Refbut.setFont(new java.awt.Font("Segoe UI Emoji", 3, 18)); // NOI18N
        Refbut.setText("Refresh");
        Refbut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefbutActionPerformed(evt);
            }
        });
        jLayeredPane2.add(Refbut, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 50, 140, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 3, 24)); // NOI18N
        jLabel3.setText("Remote File Transfer");
        jLayeredPane2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 250, -1));

        downloadbut.setFont(new java.awt.Font("Segoe UI Emoji", 3, 18)); // NOI18N
        downloadbut.setText("Download");
        downloadbut.setEnabled(false);
        downloadbut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadbutActionPerformed(evt);
            }
        });
        jLayeredPane2.add(downloadbut, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 100, 140, 30));

        uploadbut.setFont(new java.awt.Font("Segoe UI Emoji", 3, 18)); // NOI18N
        uploadbut.setText("Upload");
        uploadbut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadbutActionPerformed(evt);
            }
        });
        jLayeredPane2.add(uploadbut, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 150, 140, 30));

        jScrollPane2.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        jScrollPane2.setName(""); // NOI18N

        filelist.setFont(new java.awt.Font("Segoe UI Emoji", 3, 18)); // NOI18N
        filelist.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Icon", "File Name", "File Type"
            }
        ));
        filelist.setRowHeight(150);
        jScrollPane2.setViewportView(filelist);

        jLayeredPane2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 500, 360));

        back1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/localnetworkdrive/back.jpg"))); // NOI18N
        jLayeredPane2.add(back1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 430));

        javax.swing.GroupLayout fileLayout = new javax.swing.GroupLayout(file.getContentPane());
        file.getContentPane().setLayout(fileLayout);
        fileLayout.setHorizontalGroup(
            fileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane2)
        );
        fileLayout.setVerticalGroup(
            fileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane2)
        );

        videostream.setTitle("Video Streaming");
        videostream.setMinimumSize(new java.awt.Dimension(715, 470));

        jLayeredPane3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Refbut1.setFont(new java.awt.Font("Segoe UI Emoji", 3, 18)); // NOI18N
        Refbut1.setText("Refresh");
        Refbut1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Refbut1ActionPerformed(evt);
            }
        });
        jLayeredPane3.add(Refbut1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 50, 140, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 3, 24)); // NOI18N
        jLabel4.setText("Video Streaming");
        jLayeredPane3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 250, -1));

        downloadbut1.setFont(new java.awt.Font("Segoe UI Emoji", 3, 18)); // NOI18N
        downloadbut1.setText("Stream");
        downloadbut1.setEnabled(false);
        downloadbut1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadbut1ActionPerformed(evt);
            }
        });
        jLayeredPane3.add(downloadbut1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 100, 140, 30));

        jScrollPane3.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        jScrollPane3.setName(""); // NOI18N

        filelist1.setFont(new java.awt.Font("Segoe UI Emoji", 3, 18)); // NOI18N
        filelist1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        filelist1.setRowHeight(150);
        jScrollPane3.setViewportView(filelist1);

        jLayeredPane3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 500, 360));

        back2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/localnetworkdrive/back.jpg"))); // NOI18N
        jLayeredPane3.add(back2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 430));

        javax.swing.GroupLayout videostreamLayout = new javax.swing.GroupLayout(videostream.getContentPane());
        videostream.getContentPane().setLayout(videostreamLayout);
        videostreamLayout.setHorizontalGroup(
            videostreamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane3)
        );
        videostreamLayout.setVerticalGroup(
            videostreamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane3)
        );

        helpmenu.setMinimumSize(new java.awt.Dimension(670, 380));

        jLabel5.setFont(new java.awt.Font("Segoe UI Emoji", 3, 24)); // NOI18N
        jLabel5.setText("Local Storage Drive V 1 build 10077");

        privacybut.setFont(new java.awt.Font("Segoe UI Emoji", 3, 18)); // NOI18N
        privacybut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/localnetworkdrive/Keys-icon.png"))); // NOI18N
        privacybut.setText("Privacy Policy");

        privacybut1.setFont(new java.awt.Font("Segoe UI Emoji", 3, 18)); // NOI18N
        privacybut1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/localnetworkdrive/File-New-icon.png"))); // NOI18N
        privacybut1.setText("Owner Manual");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("TEXT ABOUT OUR AWESOME PROJECT");
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout helpmenuLayout = new javax.swing.GroupLayout(helpmenu.getContentPane());
        helpmenu.getContentPane().setLayout(helpmenuLayout);
        helpmenuLayout.setHorizontalGroup(
            helpmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, helpmenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(116, 116, 116))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, helpmenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(helpmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(helpmenuLayout.createSequentialGroup()
                        .addComponent(privacybut1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                        .addComponent(privacybut)))
                .addGap(34, 34, 34))
        );
        helpmenuLayout.setVerticalGroup(
            helpmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, helpmenuLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(helpmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(privacybut, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(privacybut1))
                .addGap(24, 24, 24))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Local Storage Drive");

        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI Emoji", 3, 24)); // NOI18N
        jLabel2.setText("Local Storage Drive");
        jLayeredPane1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 240, -1));

        opt1.setBackground(new java.awt.Color(255, 255, 255));
        opt1.setFont(new java.awt.Font("Segoe UI Emoji", 3, 18)); // NOI18N
        opt1.setForeground(new java.awt.Color(51, 0, 204));
        opt1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/localnetworkdrive/play.png"))); // NOI18N
        opt1.setText("Video Streaming");
        opt1.setToolTipText("");
        opt1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        opt1.setContentAreaFilled(false);
        opt1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        opt1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        opt1.setName("op1"); // NOI18N
        opt1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        opt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opt1ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(opt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 170, 240));

        opt2.setBackground(new java.awt.Color(255, 255, 255));
        opt2.setFont(new java.awt.Font("Segoe UI Emoji", 3, 18)); // NOI18N
        opt2.setForeground(new java.awt.Color(51, 0, 204));
        opt2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/localnetworkdrive/CinemaDisplay-icon.png"))); // NOI18N
        opt2.setText("Screen Mirroring");
        opt2.setToolTipText("");
        opt2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        opt2.setContentAreaFilled(false);
        opt2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        opt2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        opt2.setName("op1"); // NOI18N
        opt2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        opt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opt2ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(opt2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 50, 170, 240));

        opt3.setBackground(new java.awt.Color(255, 255, 255));
        opt3.setFont(new java.awt.Font("Segoe UI Emoji", 3, 18)); // NOI18N
        opt3.setForeground(new java.awt.Color(51, 0, 204));
        opt3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/localnetworkdrive/Button-Help-icon.png"))); // NOI18N
        opt3.setText("Help");
        opt3.setToolTipText("");
        opt3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        opt3.setContentAreaFilled(false);
        opt3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        opt3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        opt3.setName("op1"); // NOI18N
        opt3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        opt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opt3ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(opt3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 170, 240));

        opt4.setBackground(new java.awt.Color(255, 255, 255));
        opt4.setFont(new java.awt.Font("Segoe UI Emoji", 3, 18)); // NOI18N
        opt4.setForeground(new java.awt.Color(51, 0, 204));
        opt4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/localnetworkdrive/Button-Close-icon.png"))); // NOI18N
        opt4.setText("Exit");
        opt4.setToolTipText("");
        opt4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        opt4.setContentAreaFilled(false);
        opt4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        opt4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        opt4.setName("op1"); // NOI18N
        opt4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        opt4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opt4ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(opt4, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 50, 170, 240));

        opt5.setBackground(new java.awt.Color(255, 255, 255));
        opt5.setFont(new java.awt.Font("Segoe UI Emoji", 3, 18)); // NOI18N
        opt5.setForeground(new java.awt.Color(51, 0, 204));
        opt5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/localnetworkdrive/Hard-Disk-Wifi-icon.png"))); // NOI18N
        opt5.setText("Remote Storage");
        opt5.setToolTipText("");
        opt5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        opt5.setContentAreaFilled(false);
        opt5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        opt5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        opt5.setName("op1"); // NOI18N
        opt5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        opt5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opt5ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(opt5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 170, 240));

        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/localnetworkdrive/back.jpg"))); // NOI18N
        jLayeredPane1.add(back, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 910, 310));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void opt4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opt4ActionPerformed
        // Exit the application
        System.exit(0);
    }//GEN-LAST:event_opt4ActionPerformed

    private void opt5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opt5ActionPerformed
        // To show the RTP GUI module
        file.setVisible(true);
        
    }//GEN-LAST:event_opt5ActionPerformed

    private void RefbutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefbutActionPerformed
        // To fetch latest data on Remote files from tracker and populate the gui containers
        trackerip = constants.Constants.serverIp+":3000";
        JOptionPane.showMessageDialog(null, trackerip);
        //Initialize tracker interface
        fetchfiles f = new fetchfiles();
        //Create image icons for display
        ImageIcon jv = new ImageIcon(HomePage.class.getResource("java.png"));
        ImageIcon py = new ImageIcon(HomePage.class.getResource("python.png"));
        ImageIcon mp = new ImageIcon(HomePage.class.getResource("mp4.png"));
        ImageIcon def = new ImageIcon(HomePage.class.getResource("def.png"));
        ImageIcon pdf = new ImageIcon(HomePage.class.getResource("pdf.png"));
        //Carry out the fetch operation
        try
        {
            jsonresp = f.getHTML("http://"+trackerip+"/files/get/");
            if(jsonresp.equals(""))
            {
                JOptionPane.showMessageDialog(null,"Oops. Looks like the Server and I aren't connected.");
                return;
            }
            //Convert data into a convenient file format
            json = new JSONArray(jsonresp);
            //Create a data model for the table display
            DefaultTableModel model = new DefaultTableModel()
             {
                    @Override
                    public Class getColumnClass(int column) 
                    {
                        return getValueAt(0, column).getClass();
                    };
             };
            //Add initial columns
            model.addColumn("Icon");
            model.addColumn("FileName");
            model.addColumn("Remote Location");
            //Check if data isnt empty
            if(json.length()>0)
                downloadbut.setEnabled(true);
            //Iterate through the data fetched and populate the table display
            for(int i=0;i<json.length();i++)
            {JOptionPane.showMessageDialog(null,json.getJSONObject(i).getString("absolutepath"));
                String switcher = new pathtranslator().decode(json.getJSONObject(i).getString("absolutepath"));
                switch (switcher.split("[.]")[switcher.split("[.]").length-1]) {
                    case "py":
                        model.addRow(new Object[]{py,switcher,json.getJSONObject(i).getString("ip")});
                        break;
                    case "java":
                        model.addRow(new Object[]{jv,switcher,json.getJSONObject(i).getString("ip")});
                        break;
                    case "mp4":
                        model.addRow(new Object[]{mp,switcher,json.getJSONObject(i).getString("ip")});
                        break;
                    case "pdf":
                        model.addRow(new Object[]{pdf,switcher,json.getJSONObject(i).getString("ip")});
                        break;
                    default:
                        model.addRow(new Object[]{def,switcher,json.getJSONObject(i).getString("ip")});
                        break;
                }
            }
            //Set the table display with updated data
            filelist.setModel(model);
        }
        catch(HeadlessException | JSONException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        
    }//GEN-LAST:event_RefbutActionPerformed

    private void downloadbutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadbutActionPerformed
        // To fetch the selected file from remote server
        
        //Fetch selected file from gui component
        String file = (String)filelist.getValueAt(filelist.getSelectedRow(), 1);
        int i = 0;
        //Match the selected file with local data index
        for(i=0;i<jsonresp.length()-1;i++)
        {
            if(file.equals(json.getJSONObject(i).getString("absolutepath")))
                break;
        }
        //Retrieve Variables
        String ip = json.getJSONObject(i).getString("ip");
        String srcip = json.getJSONObject(i).getString("originip");
        String filename = json.getJSONObject(i).getString("absolutepath");
        //Initialize variable for API call
        FTPClientAPI fc = new FTPClientAPI(ip);
    try {
        //API call
        fc.ReceiveFile(filename);
    }
    catch(Exception e)
    {
        JOptionPane.showMessageDialog(null,e.getMessage());
    }
    finally
            {
                System.out.println("Done");
            }
        
        
    }//GEN-LAST:event_downloadbutActionPerformed

    private void uploadbutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadbutActionPerformed
        FTPClientAPI fc = new FTPClientAPI("192.168.0.9");        
        try
        {
            fc.SendFile("C:\\Users\\kesha\\Desktop\\z.java");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_uploadbutActionPerformed

    private void opt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opt1ActionPerformed
        // To show RTSP GUI Module
        videostream.setVisible(true);
    }//GEN-LAST:event_opt1ActionPerformed

    private void Refbut1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Refbut1ActionPerformed
        // To fetch latest data on Remote files from tracker and populate the gui containers
        trackerip = constants.Constants.serverIp+":3000";
        //Initialize tracker interface
        fetchfiles f = new fetchfiles();
        //Create image icons for table display
        ImageIcon jv = new ImageIcon(HomePage.class.getResource("java.png"));
        ImageIcon py = new ImageIcon(HomePage.class.getResource("python.png"));
        ImageIcon mp = new ImageIcon(HomePage.class.getResource("mp4.png"));
        ImageIcon def = new ImageIcon(HomePage.class.getResource("def.png"));
        ImageIcon pdf = new ImageIcon(HomePage.class.getResource("pdf.png"));
        
        //Fetch latest data on video files available for stream from tracker
        try
        {
            jsonresp = f.getHTML("http://"+trackerip+"/files/get/");
            if(jsonresp.equals(""))
            {
                JOptionPane.showMessageDialog(null,"Oops. Looks like the Server and I aren't connected.");
                return;
            }
            //Convert data into a convenient format
            json = new JSONArray(jsonresp);
            //Create a data model for the table display
            DefaultTableModel model = new DefaultTableModel()
             {
                    @Override
                    public Class getColumnClass(int column) 
                    {
                        return getValueAt(0, column).getClass();
                    };
             };
            //Populate initial columns on table display
            model.addColumn("Icon");
            model.addColumn("FileName");
            model.addColumn("Remote Location");
            //Check if data isnt empty
            if(json.length()>0)
                downloadbut1.setEnabled(true);
            //Iterate through data to populate the table display
            for(int i=0;i<json.length();i++)
            {
                String switcher = new pathtranslator().decode(json.getJSONObject(i).getString("absolutepath"));
                switch (switcher.split("[.]")[switcher.split("[.]").length-1]) {
                    case "mp4":
                        model.addRow(new Object[]{mp,switcher,json.getJSONObject(i).getString("ip")});
                        break;
                    default:
                        break;
                }
            }
            filelist1.setModel(model);
        }
        catch(HeadlessException | JSONException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }//GEN-LAST:event_Refbut1ActionPerformed

    private void downloadbut1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadbut1ActionPerformed
        //Get selected file from GUI component
        String file = (String)filelist1.getValueAt(filelist1.getSelectedRow(), 1);
        int i = 0;
        //Find the index of the selected file from local data
        for(i=0;i<jsonresp.length()-1;i++)
        {
            if(file.equals(json.getJSONObject(i).getString("absolutepath")))
                break;
        }
        //Retrieve Variables
        String ip = json.getJSONObject(i).getString("ip");
        String srcip = json.getJSONObject(i).getString("originip");
        String filename = json.getJSONObject(i).getString("absolutepath");
        
    try
    {
        //API CALL
        RunRTSPClientPython.runClient(ip, filename);
    }
    catch(Exception e)
    {
        JOptionPane.showMessageDialog(null,e.getMessage());
    }
    finally
            {
                System.out.println("Done Streaming");
            }
        
    }//GEN-LAST:event_downloadbut1ActionPerformed

    private void opt3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opt3ActionPerformed
        // To open the Help GUI module
        helpmenu.setVisible(true);
    }//GEN-LAST:event_opt3ActionPerformed

    private void opt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opt2ActionPerformed
        // TODO add your handling code here:
        new Thread(new Runnable(){public void run(){
        RTSPClient.startClient("192.168.0.129");
        }}).start();
    }//GEN-LAST:event_opt2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        Main.startDaemonProcess();
        constants.Constants.myName = JOptionPane.showInputDialog(null,"Please enter a name to identify your computer");
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Refbut;
    private javax.swing.JButton Refbut1;
    private javax.swing.JLabel back;
    private javax.swing.JLabel back1;
    private javax.swing.JLabel back2;
    private javax.swing.JButton downloadbut;
    private javax.swing.JButton downloadbut1;
    private javax.swing.JDialog file;
    private javax.swing.JTable filelist;
    private javax.swing.JTable filelist1;
    private javax.swing.JDialog helpmenu;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton opt1;
    private javax.swing.JButton opt2;
    private javax.swing.JButton opt3;
    private javax.swing.JButton opt4;
    private javax.swing.JButton opt5;
    private javax.swing.JButton privacybut;
    private javax.swing.JButton privacybut1;
    private javax.swing.JButton uploadbut;
    private javax.swing.JDialog videostream;
    // End of variables declaration//GEN-END:variables
}
