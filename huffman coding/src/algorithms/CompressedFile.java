/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Mariem Adel
 */
public class CompressedFile {
    private String name;
    private byte[] rawData;
    private final byte[] tempData;
    private final boolean isFileInFolder;
    private final String extension;
    private final int lastByte;
    private final int dataSize;
    private final Map<Integer, String> LookupTable;
    private final byte[] encodedData;
    private final DataProcessor p = new DataProcessor();
    private final String originalData; 
    private final FileWriterr w = new FileWriterr();

    public CompressedFile(String name, byte[] rawData, boolean isFileInFolder) throws IOException {
        long startTime = System.nanoTime();
        String inputType[] = name.split("\\.");
        this.name = inputType[0];
        this.rawData = rawData;
        this.tempData = rawData;
        this.isFileInFolder = isFileInFolder;
        this.extension = getExtension();
        this.lastByte = getLastByte();
        this.dataSize = getDataSize();
        this.LookupTable = getLookupTable();
        this.encodedData =this.rawData;
        this.originalData = p.decodeData(LookupTable, convertBytesToString(encodedData), lastByte);
        if(!isFileInFolder)
        {
            w.write("New "+this.name, "."+extension, originalData.getBytes());
        }
        long endTime = System.nanoTime();
        long totalTime = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);
        System.out.println("file "+this.name+" took "+totalTime+" msec to decompress");
        
        
    }

    public String getOriginalData() {
        return originalData;
    }

    public byte[] getRawData() {
        return rawData;
    }
    
    public String convertBytesToString(byte[] data)
    {
        StringBuffer n = new StringBuffer();

        //testing the encoded data
        for (int i = 0; i < this.dataSize; i++) {

            n.append(String.format("%8s", Integer.toBinaryString(data[i] & 0xFF)).replace(' ', '0'));
        }
        
        return n.toString();
    }
    public  Map<Integer, String> getLookupTable()
    {
       Map<Integer, String> table = new HashMap<Integer, String>();
       String temps="",tempss;
       char tempc;
       int tempi,x;
       int i,index=0;
       int key;
       String value;
       tempi = (int)this.rawData[0];
       index++;
       if(tempi==0)
           tempi=256;
        System.out.println("iterations "+tempi);
       for(i = 0 ; i<tempi ; i++)
       {
           key =(int)this.rawData[index];
           index++;
           x=(int)this.rawData[index];
           index++;
           while(x!=0)
           {
               if(x>=8){
               temps+=String.format("%8s", Integer.toBinaryString(this.rawData[index] & 0xFF)).replace(' ', '0');
               index++;
               x=x-8;
               }
               if(x<8&&x!=0)
               {
                   tempss=String.format("%8s", Integer.toBinaryString(this.rawData[index] & 0xFF)).replace(' ', '0');
                   index++;
                   tempss = tempss.substring(8-x, 8);
                   temps+=tempss;
                   x=0;
               }               
           }
           value = temps;
           temps="";
           //System.out.println("key "+(char)key+"   value   "+value);
           table.put(key, value);
       }
       this.rawData = updateRawData(index);
      //  System.out.println(index);
       //System.out.println(" raw size after all"+rawData.length);
       return table;
    }
    public int getDataSize()
    {
        int tempi;
        char tempc;
        String temps="";
        int i;
        tempi = (int)rawData[0];
        for(i=0;i<tempi;i++)
        {
            tempc= (char)rawData[i+1];
            temps+=tempc;
        }
        this.rawData = updateRawData ( i+1);
        tempi = Integer.parseInt(temps);
       // System.out.println("length "+tempi);
        return tempi;
    }

    public String getName() {
        return name;
    }
    
    public byte[] updateRawData(int newStart)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(this.rawData, newStart, this.rawData.length-newStart);
        this.rawData = bos.toByteArray();
       return rawData; 
    }
    public int getLastByte()
    {
        int tempi=(int)rawData[0];
        this.rawData = updateRawData (1);
        return tempi;
    }
   public  String getExtension()
    {
        String temps="";
        char tempc;
        int tempi;
        tempi = (int)rawData[0];
        int i=0;
        for( i =0 ; i<tempi;i++)
        {
            tempc=(char)rawData[i+1];
            temps+=tempc;
        }
        System.out.println(rawData.length);
        this.rawData = updateRawData ( i+1);
        System.out.println(rawData.length);

        return temps;
    }
    

   
 
}
