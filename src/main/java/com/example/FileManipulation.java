package com.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;

/**
 * Created by Tailor888 on 18.11.2017.
 */
public class FileManipulation {

    private static final String HOME = "temp/";

    public static void main(String[] args) throws IOException {

        Path homeDir = Files.createDirectory(Paths.get(HOME));

        Path p = Paths.get(HOME);
        System.out.println("Directory: " + p.toString());

        //BASIC OPERATIONS
        System.out.println("exists: " + Files.exists(p));
        System.out.println("not exists: " + Files.notExists(p));

        System.out.println("is regular file: " + Files.isRegularFile(p));
        System.out.println("is directory: " + Files.isDirectory(p));

        System.out.println("is readable: " + Files.isReadable(p));
        System.out.println("is writable: " + Files.isWritable(p));
        System.out.println("is executable: " + Files.isExecutable(p));
        //and many others

        //CREATE FILE
        System.out.println();
        Path newFile = Paths.get(HOME + "newFileTest.txt");
        System.out.println("New file exists:" + Files.exists(newFile));
        Files.createFile(newFile);
        System.out.println("New file exists:" + Files.exists(newFile));
        Files.deleteIfExists(newFile);
        System.out.println("New file exists:" + Files.exists(newFile));

        //COPY
        System.out.println();
        Path copyFile = Paths.get(HOME + "copyFile.txt");
        Files.createFile(copyFile);
        Files.copy(copyFile, copyFile, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("File copied");

        //TEMP
        System.out.println();
        String prefix = "log_";
        String suffix = ".txt";
        Path tempFile = Files.createTempFile(homeDir, prefix, suffix);
        System.out.println("temp file: " + tempFile.toString());

        //CREATE DIR
        System.out.println();
        Path newDirectory = Paths.get(HOME + "test1/test2/test3");
        //Files.createDirectory(newDirectory); //will fail
        System.out.println("New directory exists:" + Files.exists(newDirectory));
        Files.createDirectories(newDirectory);
        System.out.println("New directory exists:" + Files.exists(newDirectory));

        System.out.println();
        deleteAll(homeDir);
    }

    private static void deleteAll(Path homeDir) throws IOException {
        System.out.println("Deleting");
        Files.walk(homeDir)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .peek(System.out::println)
                .forEach(File::delete);
    }
}
