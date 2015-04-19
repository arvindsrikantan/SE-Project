/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RTP;

/**
 *
 * @author kesha
 */
public class pathtranslator {
    /**
     * @param args the command line arguments
     */    
    public String encode(String absolutePath)
    {
        String result=absolutePath;
        result=result.replace("/", "___");
        result=result.replace("\\", "___");
        result=result.replace(":", ";;");
        return result;
    }
    
    public String decode(String absolutePath)
    {
        String result=absolutePath;
        result=result.replace("___", "/");
        result=result.replace("___", "\\");
        result=result.replace(";;", ":");
        return result;
    }

    
}
