package com.example.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Tailor888 on 18.11.2017.
 */
public class SearchVisitor implements FileVisitor<Path> {

    private String FILE_NAME;
    private Path START_DIR;

    public SearchVisitor(String FILE_NAME, Path START_DIR) {
        this.FILE_NAME = FILE_NAME;
        this.START_DIR = START_DIR;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String fileName = file.getFileName().toString();
        if (FILE_NAME.equals(fileName)) {
            System.out.println("File found: " + file.toString());
            return FileVisitResult.TERMINATE;
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println("Failed to access file: " + file.toString());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        boolean finishedSearch = Files.isSameFile(dir, START_DIR);
        if (finishedSearch) {
            System.out.println("File:" + FILE_NAME + " not found");
            return FileVisitResult.TERMINATE;
        }
        return FileVisitResult.CONTINUE;
    }
}
