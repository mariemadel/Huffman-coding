/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Mariem Adel
 */
public class Algorithms {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO code application logic here
        //FileReaderr f = new FileReaderr();
        //f.readFile("New folder.txt");
        String input;
        System.out.println("Please enter your file or filder name");
        Scanner scanner = new Scanner(System.in);
        input = scanner.nextLine();
        String inputType[] = input.split("\\.");
        switch (inputType.length) {
            case 1:
                System.out.println("you entered a folder name");
                System.out.println("folder name is" + inputType[0]);
                // System.out.println("path    "+absoluteTestPath);
                Folderr fr = new Folderr(inputType[0]);

                break;
            case 2:
                switch (inputType[1]) {
                    case "zip":
                        System.out.println("you entered a compressed file..");
                        Compressed c = new Compressed(input);
                        
                        break;
                    default:
                        System.out.println("you entered a file name");
                        System.out.println("file name is  " + inputType[0] + "  file type is  " + inputType[1]);
                        Filee f = new Filee(input, false);
                        break;
                }
            default:
                //System.out.println("incorect input");
                break;
            // Filee f = new Filee("New folder.txt");
            //String s = f.getClass().toString();
            //String l = "mariem.adel";
            //String words[] = s.split("\\.");
            // System.out.println("class name"+words[1]);
            // String absoluteTestPath = new File(TEST_PATH).getAbsolutePath();
            // String path[] =absoluteTestPath.split("\\.");
//        System.out.println("file path   "+absoluteTestPath);
        }
    }

}
