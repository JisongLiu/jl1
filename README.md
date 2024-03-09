# Example RocksDB Java Program

An example Java Program that interacts with RocksDB

## Build
```sh
$ mvn package
```

## Run
```
$ java -cp target/java-rocksdb-1.0-SNAPSHOT.jar com.foo.app.App
```

### Sidenote

I run into this error when running the above java command:
```sh
$ java -cp target/java-rocksdb-1.0-SNAPSHOT.jar com.foo.app.App
Error: Unable to initialize main class com.foo.app.App
Caused by: java.lang.NoClassDefFoundError: org/rocksdb/RocksDBException
```

I can add the rocksdb jar to the `target/` directory by running:
```sh
$ wget -P target/ https://repo1.maven.org/maven2/org/rocksdb/rocksdbjni/8.11.3/rocksdbjni-8.11.3.jar
```

And then running
```sh
$ java -cp "target/*" com.foo.app.App
Hello World!
bar
```
