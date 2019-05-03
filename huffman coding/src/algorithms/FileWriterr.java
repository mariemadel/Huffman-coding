/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Mariem Adel
 */
public class FileWriterr {
    public void write(String name,String extension,byte[] bytes) throws FileNotFoundException, IOException
    {
        File someFile = new File(name+extension);
        FileOutputStream fos = new FileOutputStream(someFile);
        fos.write(bytes);
        fos.flush();
        fos.close();
    }
    public void writeToPath(String path,byte[] bytes) throws FileNotFoundException, IOException
    {
        File someFile = new File(path);
        FileOutputStream fos = new FileOutputStream(someFile);
        fos.write(bytes);
        fos.flush();
        fos.close();
    }
    
}
