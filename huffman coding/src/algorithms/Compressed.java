/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.io.IOException;

/**
 *
 * @author Mariem Adel
 */
public class Compressed {

    private String Name;
    private final FileReaderr f = new FileReaderr();
    //private final FileWriterr w = new FileWriterr();
    private final byte[] data;
    private boolean isFile;

    public Compressed(String Name) throws IOException {
        this.Name = Name;
        data = f.readFile(Name, null);
        //CompressedFile cf = new CompressedFile();
        setIsFile();
        createObjects(data);
    }
    
    public void createObjects(byte[] data) throws IOException {
        if (isFile) {
            CompressedFile cf = new CompressedFile(Name, data,!isFile);
        } else {
            CompressedFolder cf = new CompressedFolder(Name ,data);
        }
    }

    public void setIsFile() {
        this.isFile = (int) data[0] != 30;
    }

}
