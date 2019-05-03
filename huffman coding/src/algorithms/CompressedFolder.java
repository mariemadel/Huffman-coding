/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Mariem Adel
 */
public final class CompressedFolder {
    private final String name;
    private byte[] rawData;
    private final int filesNumber;
    private CompressedFile[] files;
    private final FileWriterr w = new FileWriterr();

    public CompressedFolder(String name, byte[] rawData) throws IOException {
        this.name = name;
        this.rawData = rawData;
        this.filesNumber = getFilesNumber(); 
        this.files = createFiles();
//        String inputType[] = name.split("\\.");
//        String absoluteTestPath = new File("New "+inputType[0]).getAbsolutePath();
//        new File(absoluteTestPath+"1").mkdirs();
//        for(int i =0; i<filesNumber;i++)
//        {
//            byte[] b=this.files[i].getOriginalData().getBytes();
//            inputType =this.files[i].getName().split("\\.");
//            w.writeToPath(absoluteTestPath+inputType[0]+this.files[i].getExtension(), b);
//        }
        
    }
    public String getFileName()
    {
        String temps="";
        int tempi= rawData[0];
        int i;
        char tempc;
        for(i=0;i<tempi;i++)
        {
            tempc=(char)this.rawData[i+1];
            temps+=tempc;
        }
        this.rawData = updateRawData(i+1);
        return temps;
    }
    public CompressedFile[] createFiles() throws IOException
    {
        CompressedFile[] files = new CompressedFile[filesNumber];
        String inputType[] = name.split("\\.");
        String absoluteTestPath = new File("New "+inputType[0]).getAbsolutePath();
         new File(absoluteTestPath).mkdirs();
       int i =0;
       for(i=0;i<filesNumber;i++)
       {
           files[i]= new CompressedFile(getFileName(),this.rawData,true);
           this.rawData = files[i].getRawData();
           byte[] b=this.files[i].getOriginalData().getBytes();
           inputType =this.files[i].getName().split("\\.");
           w.writeToPath(absoluteTestPath+inputType[0]+this.files[i].getExtension(), b);
           
       }
       return files;
    }
    
    public int getFilesNumber()
    {
       int tempi;
       tempi=(int)rawData[1];
       this.rawData =updateRawData(2);
       return tempi;
    }
    public byte[] updateRawData(int newStart)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(this.rawData, newStart, this.rawData.length-newStart);
        this.rawData = bos.toByteArray();
       return rawData; 
    }

    
}
