/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.findServer;

import constants.Constants;
import java.io.File;

/**
 *
 * @author arvind
 */
public class CreateDirectories 
{
    public static void createDirectories()
    {
        String dirName = "LocalNetwork/"+Constants.subnetId+".";
        File dir = new File("LocalNetwork");
        if(!dir.exists())
        {
           dir.mkdir();
        }
        for(int i=1;i<255;i++)
        {
            dirName+=i;
//            System.out.println(dirName);
            dir = new File(dirName);
            if(!dir.exists())
            {
                dir.mkdir();
            }
            dirName="LocalNetwork/"+Constants.subnetId+".";
        }
    }
}
