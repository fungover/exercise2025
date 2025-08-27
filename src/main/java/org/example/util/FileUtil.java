package org.example.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    public static int createFile(String fileName) {
        Path path = Path.of(fileName);

        try {
            Files.createFile(path);
            System.out.println("File created: " + path.getFileName());
            return 0;
        } catch (IOException e) {
            if (Files.exists(path)) {
                System.out.println("File already exists: " + path.getFileName());
                return -1;
            } else {
                logger.error("File could not be created: {}", fileName, e);
                return -2;
            }
        }
    }

    public static void writeToFile(String fileName, String content) {
        Path path = Path.of(fileName);
        try {
            Files.writeString(
                    path,
                    content,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            logger.error("File could not be written to: {}", fileName, e);
        }
    }
}
