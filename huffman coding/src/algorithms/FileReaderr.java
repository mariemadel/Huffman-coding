/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mariem Adel
 */
public class FileReaderr {

    public byte[] readFile(String s,Filee ff) throws IOException {
        File file = new File(s);

        FileInputStream fis = new FileInputStream(file);
        //System.out.println(file.exists() + "!!");
        //InputStream in = resource.openStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); //no doubt here is 0
                //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
                //System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
            // Logger.getLogger(genJpeg.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error reading file");
        }
        int j = bos.size();
        System.out.println("file :" + file + " was read and it's lenght is :  " + j);
        if(ff!=null)
        ff.setOriginalSize(j);

        byte[] bytes = bos.toByteArray();
        // for (j = 0; j < bos.size(); j++) {
        //     System.out.println("byte #" + (j + 1) + "equals " + ((char) bytes[j]));
        //}

        // System.out.println("doble space byte 1   "+(char)bytes[0]+"   byte 2   "+(char)bytes[1]);
        //System.out.println("test"+(char)13+(char)10+"done");
        //
        //
        //
        //TO COPY FILES AKA WRITE THEM
        //
        //
        //
        /*File someFile = new File("copied"+s);
        FileOutputStream fos = new FileOutputStream(someFile);
        fos.write(bytes);
        fos.flush();
        fos.close();*/
        return bytes;
    }
}
