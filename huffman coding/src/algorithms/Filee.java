/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static jdk.nashorn.tools.ShellFunctions.input;

/**
 *
 * @author Mariem Adel
 */
public final class Filee {

    private final String fileName;
    private byte[] data;
    private Map<Integer, Integer> freq = new HashMap<>();
    private final FileReaderr f = new FileReaderr();
    private final FileWriterr w = new FileWriterr();
    private final DataProcessor p = new DataProcessor();
    private Node huffmanTree;
    private Map<Integer, String> LookupTable;
    private byte[] encodedDataBytes;
    private byte[] compressedFileOutput;
    private final boolean isFileInFolder;
    private int lastByte;
    private int originalSize;
    private int compressedSize;

    public Filee(String FileName, boolean isFileInFolder) throws IOException, InterruptedException {
        long startTime = System.nanoTime();
        this.fileName = FileName;
        this.isFileInFolder = isFileInFolder;
        setData();
        setFreq();
        setHuffmanTree();
        setLookupTable();
        setEncodedDataBytes();
        setCompressedFileOutput();
        if(!isFileInFolder){
         String name[] = fileName.split("\\.");
        w.write(name[0], ".zip", compressedFileOutput);
        }
        System.out.println("compression ratio" + (float) compressedSize / originalSize);
        long endTime = System.nanoTime();
        long totalTime = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);
        System.out.println("file "+fileName+" took "+totalTime+" msec to compress");
        
    }

    public int getCompressedSize() {
        return compressedSize;
    }

    public void setCompressedSize(int compressedSize) {
        this.compressedSize = compressedSize;
    }

    public int getOriginalSize() {
        return originalSize;
    }

    public void setOriginalSize(int originalSize) {
        this.originalSize = originalSize;
    }

    public void setLastByte(int lastByte) {
        this.lastByte = lastByte;
    }

    public byte[] getCompressedFileOutput() {
        return compressedFileOutput;
    }

    public int getLastByte() {
        return lastByte;
    }

    public void setCompressedFileOutput() throws IOException, InterruptedException {
        this.compressedFileOutput = p.compressionFileOutput(fileName, encodedDataBytes, LookupTable, isFileInFolder, this);
    }

    public void setEncodedDataBytes() throws IOException {
        this.encodedDataBytes = p.encodeDataToBytes(LookupTable, data, this);
    }

    public void setLookupTable() {
        this.LookupTable = p.bulidLookupTable(huffmanTree);
    }

    public void setHuffmanTree() {
        this.huffmanTree = p.buildHuffmanTree(freq);
    }

    public void setFreq() {
        this.freq = p.getFreq(data);
    }

    public void setData() throws IOException {
        this.data = f.readFile(fileName, this);
    }

}
