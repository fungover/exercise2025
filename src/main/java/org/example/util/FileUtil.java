package org.example.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    public static void createFile(String fileName) {
        try {
           File myObj = new File("prices.txt");
           if (myObj.createNewFile()) {
               System.out.println("File created: " + myObj.getName());
           } else {
               System.out.println("File already exists.");
           }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void writeToFile(String fileName, String content) {
        try {
            FileWriter myWriter = new FileWriter(fileName, true);
            myWriter.write(content);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {}
    }
}
