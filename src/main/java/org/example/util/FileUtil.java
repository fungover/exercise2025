package org.example.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private FileUtil() {
        // utility class to prevent accidental instantiation.
    }

    public static int createFile(String fileName) {
        Path path = Path.of(fileName);
        Path parent = path.getParent();

        try {
            if (parent != null) {
                Files.createDirectories(parent);
                logger.info("Ensured parent directory exists: {}", parent);
            }
            Files.createFile(path);
            logger.info("File created: {}", path);
            return 0;
        } catch (FileAlreadyExistsException e) {
            logger.debug("File already exists: {}", path);
            return -1;
        } catch (IOException e) {
            logger.error("File could not be created: {}", path, e);
            return -2;
        }
    }

    public static void writeToFile(String fileName, String content) {
        Path path = Path.of(fileName);

        try {
            Path parent = path.getParent();
            if (content == null) {
                logger.warn("Null content passed to writeToFile; writing newline only: {}", path);
                content = "\n";
            }

            if (parent != null) {
                Files.createDirectories(parent);
            }

            try (BufferedWriter writer = Files.newBufferedWriter(
                    path,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND)) {
                writer.write(content);
                if (!(content.endsWith("\n") || content.endsWith("\r"))) {
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            logger.error("File could not be written to: {}", fileName, e);
        }
    }
}
