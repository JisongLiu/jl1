package com.foo.app;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Runner {
    static {
        RocksDB.loadLibrary();
    }

    private final static String NAME = "foo";
    static File dbDir;
    static RocksDB db;

    public boolean run() {
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
            return true;
        } catch (IOException | RocksDBException ex) {
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
            //log.error("Error initializng RocksDB, check configurations and permissions, exception: {}, message: {}, stackTrace: {}",
            //ex.getCause(), ex.getMessage(), ex.getStackTrace());
            return false;
        }
    }

    public boolean crawlerUsingHttpClient() {
        // Create an instance of HttpClient
        HttpClient client = HttpClients.createDefault();

        // Create a new GET request for the specified URL
        HttpGet request = new HttpGet("http://www.google.com");

        try {
            // Execute the request and receive the response
            HttpResponse response = client.execute(request);

            // Print the response status line
            System.out.println("Response Status Line: " + response.getStatusLine());

            // Convert the response entity to a String and print it
            String responseBody = EntityUtils.toString(response.getEntity());
            System.out.println("Response Body: " + responseBody);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean crawler(String input) {
        String urlString = Strings.isNullOrEmpty(input) ? "http://google.com" : input; // Replace with your target URL
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method to GET
            connection.setRequestMethod("GET");

            // Set request headers, if needed
            // connection.setRequestProperty("Content-Type", "application/json");

            // Get response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Print the response
            System.out.println(response);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
