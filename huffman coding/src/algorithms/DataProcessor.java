/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import static sun.security.krb5.Confounder.bytes;

/**
 *
 * @author Mariem Adel
 */
public class DataProcessor {

    public Map<Integer, Integer> getFreq(byte data[]) {
        Map<Integer, Integer> freq = new HashMap<Integer, Integer>();
        int temp, temp2;
        for (int i = 0; i < data.length; i++) {
            temp = data[i];

            if (freq.get(temp) != null) {
                temp2 = freq.get(temp);
                freq.replace(temp, temp2 + 1);
                // System.out.println("here");
            } else {
                freq.put(temp, 1);
            }
        }
        System.out.println("# of unique char" + freq.size());

        /*   for (Iterator<Integer> it = freq.keySet().iterator(); it.hasNext();) {
                int name = it.next();
                int value = freq.get(name);
                System.out.println((char)name + " " + value); 
            }*/
        buildHuffmanTree(freq);
        return freq;

    }

    public Node buildHuffmanTree(Map<Integer, Integer> freq) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        PriorityQueue<Node> temp = new PriorityQueue<>();
        PriorityQueue<Node> temp2 = new PriorityQueue<>();
        PriorityQueue<Node> temp3 = new PriorityQueue<>();
        for (Iterator<Integer> it = freq.keySet().iterator(); it.hasNext();) {
            int name = it.next();
            int value = freq.get(name);
            pq.add(new Node(value, name, null, null));

        }
        if (pq.size() == 1) {
            return pq.poll();
        }
        while (pq.size() > 1) {

            Node left = pq.poll();
            Node right = pq.poll();
            Node parent = new Node((left.getFreq() + right.getFreq()), 0, left, right);
            pq.add(parent);
        }
        return pq.poll();
    }

    public Map<Integer, String> bulidLookupTable(Node tree) {
        Map<Integer, String> table = new HashMap<Integer, String>();
        getValueFreq(tree, "", table);
        return table;
    }

    public void getValueFreq(Node node, String s, Map<Integer, String> table) {
        if (!node.isLeaf()) {
            getValueFreq(node.getLeftChild(), s + '0', table);
            getValueFreq(node.getRightChild(), s + '1', table);
        } else {
            table.put(node.getValue(), s);
            System.out.println("value " + (char) node.getValue() + "   new code  " + s);
        }
    }

    public byte[] encodeDataToBytes(Map<Integer, String> table, byte[] data, Filee f) {
        StringBuilder encoded = new StringBuilder();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] result = new byte[data.length];
        String temps;
        int tempi;
        for (int i = 0; i < data.length; i++) {
            int j = data[i];
            encoded.append(table.get(j));
            while (encoded.length() >= 8) {
                temps = encoded.toString();
                temps = temps.substring(0, 8);
                tempi = Integer.parseInt(temps, 2);
                bos.write((byte) tempi);
                temps = encoded.toString();
                temps = temps.substring(8, encoded.length());
                encoded = new StringBuilder();
                encoded.append(temps);

            }

        }
        int left = encoded.length();
        f.setLastByte(left);
        while (encoded.length() != 0) {
            temps = encoded.toString();
            if (encoded.length() >= 8) {
                temps = temps.substring(0, 8);
            }
            if (encoded.length() < 8) {
                temps = temps.substring(0, encoded.length());
            }
            tempi = Integer.parseInt(temps, 2);
            bos.write((byte) tempi);
            temps = encoded.toString();
            if (encoded.length() >= 8) {
                temps = temps.substring(8, encoded.length());
            }
            encoded = new StringBuilder();
            if (encoded.length() > 0) {
                encoded.append(temps);
            }
        }
        // System.out.println("encoded msg" + encoded);
        // System.out.println("new size equals" + encoded.length() / 8);
        //decodeData(table,encoded.toString());
        //dataFromStringToBytes(encoded.toString());
        System.out.println(bos.size());
        System.out.println("done");
        result = bos.toByteArray();
        StringBuffer n = new StringBuffer();

        //testing the encoded data
//        for (int i = 0; i < result.length; i++) {
//
//            n.append(String.format("%8s", Integer.toBinaryString(result[i] & 0xFF)).replace(' ', '0'));
//        }
//        System.out.println("compresse data size" + result.length);
//        decodeData(table, n.toString(), left);
        //testing decoding end

        //System.out.println(n.length());
        return result;
    }

    public Map<String, Integer> invertTable(Map<Integer, String> table) {
        Map<String, Integer> inverseTable = new HashMap<String, Integer>();
        int i = 0;

        for (Integer name : table.keySet()) {

            Integer key = name;
            String value = table.get(name).toString();
            // System.out.println(key + " " + value);  
            inverseTable.put(value, key);
        }

        return inverseTable;
    }
    //my file format
    //size of extension/extension/size of file as string/record separator char '30'
    //table size in one byte//value key for each entry
    //data as parsed before

    public byte[] compressionFileOutput(String fileName, byte[] data, Map<Integer, String> lookupTable, boolean isFileInFolder, Filee ff) throws IOException, InterruptedException {
        // System.out.println("is here");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        String fileType[] = fileName.split("\\.");
        int tempInt;
        byte[] temp = new byte[1000];
        //if file in folder
        if (isFileInFolder) {
            //lenght of file name
            tempInt = fileType[0].length();
            bos.write((byte) tempInt);
            //file name
            temp = fileType[0].getBytes();
            bos.write(temp);
        }
        temp = fileType[1].getBytes();
        //extension size
        bos.write((byte) fileType[1].length());
        //extension
        bos.write(temp);
        tempInt = data.length;
        String tempString = Integer.toString(tempInt);
        temp = tempString.getBytes();
        //last byte
        tempInt = ff.getLastByte();
        bos.write(tempInt);
        //size of file size string
        bos.write((byte) temp.length);
        //size of data as a string in case they are more than 256 they wouldnot fit in one byte
        bos.write(temp);
        //
//        tempInt = 30;
//        //record seprator char
//        bos.write((byte) tempInt);
        tempInt = lookupTable.size();
        //look up table size
        bos.write((byte) tempInt);
        //write look up table
        temp = writeLookupTableInBytesForm(lookupTable);
        bos.write(temp);
        //write encoded data
        bos.write(data);
        byte[] result = bos.toByteArray();
        System.out.println("");
// System.out.println("size of result" + result.length);
//        for(int i=0;i<result.length;i++)
//            System.out.println((char)result[i]);
        ff.setCompressedSize(result.length);
        return result;
    }

    public byte[] writeLookupTableInBytesForm(Map<Integer, String> lookupTable) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int tempInt;
        String tempS;
        int i = 0;
        for (int name : lookupTable.keySet()) {
            int key = name;
            if (lookupTable.get(name) != null) {
                bos.write((byte) name);
                String value = lookupTable.get(name);
                tempInt = value.length();
                bos.write((byte) tempInt);
                while (value.length() > 8) {
                    // System.out.println((char) key + "   i      " + i + "    length      " + value.length());
                    tempS = value.substring(0, 8);
                    value = value.substring(8);
                    //System.out.println("temps   " + tempS.length() + "      value       " + value.length());
                    tempInt = Integer.parseInt(tempS, 2);
                    //System.out.println(tempInt);
                    bos.write((byte) tempInt);
                    i++;
                }
                if (value.length() != 0) {
                    tempInt = Integer.parseInt(value, 2);
                    bos.write((byte) tempInt);
                }
            }
        }
        System.out.println("look up table bytes size" + bos.size());
        return bos.toByteArray();
    }

    public String decodeData(Map<Integer, String> table, String data, int lastByte) {
        Map<String, Integer> inverseTable = invertTable(table);
        int i = 0;
        String s = "";
        char c;
        StringBuilder temp = new StringBuilder();
        StringBuilder tempp = new StringBuilder();
        temp.append("");
        //char tempc;
        int tempi = i;
        int ii = data.length() - 8;
        if (lastByte == 0) {
            ii = data.length();
        }
        for (i = 0; i < ii; i++) {
            c = data.charAt(i);
            s += c;
            if (inverseTable.get(s) != null) {
                tempi = inverseTable.get(s);
                temp.append((char) tempi);

                s = "";
                tempi = i;
            }
        }

        // c=temp.c
        // System.out.println("here    "+tempi);
//        if(s.length()!=0)
//        {
//           i = s.length();
//           c = temp.charAt(temp.length()-1);
//            System.out.println(c);
//           s= table.get((int)c)+s;
//           System.out.println(table.get((int)c));
//          // System.out.println("new sub string   "+s);
//           s = s.substring(i+1);
//            if(inverseTable.get(s)!=null){
//           tempi = inverseTable.get(s);
//           s="";
//           s+=(char)tempi;
//           temp.replace(temp.length()-1, temp.length(),s);
//           //temp = temp.substring(0,temp.length());
//           
//        }}
        String subLastByte;

        //System.out.println("last byte size  "+lastByte+"  left string size  "+s.length());
        //last byte processing
        if (lastByte != 0) {
            subLastByte = data.substring(data.length() - 8);
            subLastByte = subLastByte.substring(8 - lastByte, subLastByte.length());
            //System.out.println(lastByte);
            //System.out.println("TEST"+subLastByte);
            subLastByte = s + subLastByte;
            // System.out.println("TEST 2"+ subLastByte);
            if (inverseTable.get(subLastByte) != null) {
                tempi = inverseTable.get(subLastByte);
                temp.append((char) tempi);

            }
        }
        String result = temp.toString();
        //System.out.println(lastByte);
        System.out.println("result after decoding");
       // System.out.println(result);
        System.out.println("size" + result.length());
        //System.out.println(data.length() / 8);
        //byte[] dataa=result.getBytes();

        //System.out.println("dataaa size"+data.length());
        return result;
    }

}

//    public byte[] dataFromStringToBytes(String data) throws FileNotFoundException, IOException {
//        String temps;
//        int size = (int) Math.ceil(data.length() / 8);
//        byte[] result = new byte[size];
//        int tempi;
//        int i = 0;
//        int j = 0;
//        int index = 0;
//        byte t;
//        for (index = 0; index < Math.ceil(data.length() / 8); index++) {
//            i = index * 8;
//            j = i + 8;
//            if (data.substring(i).length() > 8) {
//                // System.out.println("i   = "+i+"j    ="+j);
//                temps = data.substring(i, j);
//                tempi = Integer.parseInt(temps, 2);
//                t = (byte) tempi;
//                //System.out.println("index");
//                //System.out.println("tempi"+tempi);
//                //System.out.println((byte)tempi);
//                //System.out.println("temps"+temps);
//                result[index] = t;
//            } else {
//                //System.out.println("i   = "+i+"j    ="+j);
//                temps = data.substring(i);
//                tempi = Integer.parseInt(temps, 2);
//                //System.out.println("temps"+temps);
//                t = (byte) tempi;
//                // System.out.println("2   index");
//                result[index] = t;
//            }
//        }
//        System.out.println("size here" + result.length);
////        ByteArrayOutputStream bos = new ByteArrayOutputStream();
////        for (i = 0; i < data.length() / 8; i++) {
////            bos.write(result[i]);
////        }
////        result = bos.toByteArray();
//
//////TESSSSST
////int test =21;//282928;
////String tests = Integer.toString(test);
////        System.out.println("testsss"+tests);
////        byte testt=(byte) test;
////        byte tst[]=tests.getBytes();
////        System.out.println("");
////       // System.out.println((char)tst[0]);
////               // System.out.println((char)tst[1]);
//////System.out.println(tst.toString());
////        //System.out.println("testttt"+testt);
////        char c = (char)tst[0];
////        
////        String s ="";
////        s+=c;
////        c = (char)tst[1];
////        s+=c;
////        test = Integer.parseInt(s);
//        //
//        // int tempp = Integer.parseInt((char)tst[0]);
//        // System.out.println("tempp"+s+test);
//        ///END TESSSSST
//        ///OUTPUT WRITING
////        File someFile = new File("copied.zip");
////        FileOutputStream fos = new FileOutputStream(someFile);
////        fos.write(result);
////        fos.flush();
////        fos.close();
//        System.out.println("done 2");
//        return result;
//    }
