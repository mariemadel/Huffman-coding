/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Mariem Adel
 */
public final class Folderr {

    private final String name;
    private final List<Filee> files = new ArrayList<>();
    private final byte[] folderEncodedData;
    private final FileWriterr w = new FileWriterr();
    private int outputSize;


    public Folderr(String name) throws IOException, InterruptedException {
        long startTime = System.nanoTime();
        this.name = name;
        String absoluteTestPath = new File(name).getAbsolutePath();
        
        ///new File(absoluteTestPath+"1").mkdirs();
        File folder = new File(absoluteTestPath);
        listFilesForFolder(folder);
        folderEncodedData = writeCompressedFolderOutPut(files);
        w.write(name, ".zip", folderEncodedData);
        
        long endTime = System.nanoTime();
        long totalTime = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);
        System.out.println("folder "+name+" took "+totalTime+" msec to compress");
        System.out.println("compression ratio" + (float) folderEncodedData.length /calcTotalSize( files));
        
    }
    

    public void listFilesForFolder(File folder) throws IOException, InterruptedException {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                Filee f = new Filee(fileEntry.getName(),true);
                files.add(f);
                System.out.println(fileEntry.getName());
            }
        }
        System.out.println("# of filespro test" + files.size());
    }
    public int calcTotalSize(List<Filee> files)
    {
        int temp=0;
        for(Filee f : files) {    
            //write each file data
           temp+=f.getOriginalSize();
        }
        return temp;
    }
    public byte[] writeCompressedFolderOutPut(List<Filee> files) throws IOException
    {
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //
        bos.write((byte)30);
        bos.write((byte)files.size());
        for(Filee f : files) {    
            //write each file data
            bos.write(f.getCompressedFileOutput());
        }
        return bos.toByteArray();
    }
    
}
