package com.foo.app;

import com.google.common.collect.ImmutableList;
import org.rocksdb.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class App {
    static {
        RocksDB.loadLibrary();
    }

    private final static String NAME = "foo";
    static File dbDir;
    static RocksDB db;

    public static void main(String[] args) {
        System.out.println("Hello World!");
        final Options options = new Options();
        options.setCreateIfMissing(true);
        dbDir = new File("/tmp/rocks-db", NAME);
        try {
            Files.createDirectories(dbDir.getParentFile().toPath());
            Files.createDirectories(dbDir.getAbsoluteFile().toPath());
            db = RocksDB.open(options, dbDir.getAbsolutePath());

            List<String> keys = ImmutableList.of("1 key", "2 key", "3 key", "3 key", "4 key");
            List<String> values = ImmutableList.of("1 value", "2 value", "3 value", "4 value", "4 value");
            for (int i = 0; i < keys.size(); i++) {
                db.put(keys.get(i).getBytes(), values.get(i).getBytes());
            }
            RocksIterator iterator = db.newIterator();
            iterator.seek("2 key".getBytes());
            while (iterator.isValid()) {
                System.out.println(new String(iterator.value()));
                iterator.next();
            }

        } catch (IOException | RocksDBException ex) {
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
            //log.error("Error initializng RocksDB, check configurations and permissions, exception: {}, message: {}, stackTrace: {}",
            //ex.getCause(), ex.getMessage(), ex.getStackTrace());
        }
    }
}
