package com.example;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.time.Instant;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Created by Tailor888 on 18.11.2017.
 */
public class FileAttributes {

    public static void main(String[] args) throws IOException {
        Path file = Paths.get("src\\main\\java\\com\\example\\FileAttributes.java");
        BasicFileAttributeView basicView =
                Files.getFileAttributeView(file, BasicFileAttributeView.class);

        BasicFileAttributes basicAttribs = basicView.readAttributes();
        System.out.println("size: " + basicAttribs.size());
        
        //TIME
        System.out.println();
        FileTime newAccessTime = FileTime.from(basicAttribs.lastModifiedTime().toInstant().minus(10, DAYS));
        basicView.setTimes(newAccessTime, null , null);

        //STORE - drive
        System.out.println();
        FileStore store = Files.getFileStore(file);
        System.out.println("total space: " + store.getTotalSpace());
        System.out.println("unallocated space: " + store.getUnallocatedSpace());
        System.out.println("usable space: " + store.getUsableSpace());

        //STORE - broad
        System.out.println();
        Iterable<FileStore> fileStores = FileSystems.getDefault().getFileStores();
        for (FileStore fileStore : fileStores) {
            System.out.println("total space: " + fileStore.getTotalSpace());
            System.out.println("unallocated space: " + fileStore.getUnallocatedSpace());
            System.out.println("usable space: " + fileStore.getUsableSpace());
        }

        //OWNER
        System.out.println();
        FileOwnerAttributeView ownerView = Files.getFileAttributeView(
                file, FileOwnerAttributeView.class);
        UserPrincipal owner = ownerView.getOwner();
        System.out.println(owner.getName());

        //CUSTOM
        UserDefinedFileAttributeView userDefView = Files.getFileAttributeView(
                file, UserDefinedFileAttributeView.class);
        List<String> attribList = userDefView.list();
        System.out.println("attributes: " + attribList);

        String name = "hiddenOwner";
        String value = "Me";
        userDefView.write(name, Charset.defaultCharset().encode(value));

        attribList = userDefView.list();
        System.out.println("attributes: " + attribList);

        ByteBuffer attrValue = ByteBuffer.allocate(userDefView.size(name));
        userDefView.read(name, attrValue);
        attrValue.flip();
        System.out.println(Charset.defaultCharset().decode(attrValue).toString());
    }
}
