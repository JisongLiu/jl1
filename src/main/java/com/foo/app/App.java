package com.foo.app;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class App 
{
    private final static String NAME = "foo";
    static File dbDir;
    static RocksDB db;
    public static void main( String[] args )
    {
        RocksDB.loadLibrary();
        System.out.println( "Hello World!" );
        final Options options = new Options();
        options.setCreateIfMissing(true);
        dbDir = new File("/tmp/rocks-db", NAME);
        try {
          Files.createDirectories(dbDir.getParentFile().toPath());
          Files.createDirectories(dbDir.getAbsoluteFile().toPath());
          db = RocksDB.open(options, dbDir.getAbsolutePath());

          String key = "foo";
          String value = "bar";
          db.put(key.getBytes(), value.getBytes());
          final byte[] get = db.get(key.getBytes());
          System.out.println(new String(get));

        } catch(IOException | RocksDBException ex) {
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
            //log.error("Error initializng RocksDB, check configurations and permissions, exception: {}, message: {}, stackTrace: {}",
            //ex.getCause(), ex.getMessage(), ex.getStackTrace());
        }
    }
}
