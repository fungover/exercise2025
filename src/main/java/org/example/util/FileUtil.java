package org.example.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    public static int createFile(String fileName) {
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                return 0;
            } else {
                System.out.println("File already exists: " + file.getName());
                return -1;
            }
        } catch (IOException e) {
            logger.error("File could not be created: {}", fileName, e);
            return -2;
        }
    }

    public static void writeToFile(String fileName, String content) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(content);

        } catch (IOException e) {
            logger.error("File could not be written to: {}", fileName, e);
        }
    }
}
