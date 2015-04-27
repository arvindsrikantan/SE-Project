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
        String dirName = Constants.subnetId;
        for(int i=0;i<255;i++)
        {
            dirName+=i;
            System.out.println(dirName);
            File dir = new File(dirName);
            if(!dir.exists())
            {
                System.out.println("Dir creation:"+dirName+",Creation success"+dir.mkdirs());
            }
        }
    }
}
