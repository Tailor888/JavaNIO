package com.example;

import com.example.util.SearchVisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Tailor888 on 18.11.2017.
 */
public class FileVisitor {
    public static void main(String[] args) throws IOException {
        Path startingDir = Paths.get(".");
        String fileToSearch = "SearchVisitor.java";
        SearchVisitor crawler = new SearchVisitor(
                fileToSearch, startingDir);
        Files.walkFileTree(startingDir, crawler);
    }
}
