package com.example;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * Created by Tailor888 on 18.11.2017.
 */
public class Watcher {
    public static void main(String[] args) throws IOException, InterruptedException {
        WatchService watchService
                = FileSystems.getDefault().newWatchService();

        Path path = Paths.get(".");

        path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);

        System.out.println("Starting watch");
        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println(
                        "Event kind:" + event.kind()
                                + ". File affected: " + event.context() + ".");
            }
            key.reset();
        }
    }
}
