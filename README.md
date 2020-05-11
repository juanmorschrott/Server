# Simple HTTP Java Server

This is just a really simple MVC based HTTP server.

### Configuration File

You can modify server settings from the Configuration.java file:

```
...
├───src
│   ├───main
│   │   ├───java
│   │   │   └───com
│   │   │       └───playspace
│   │   │           ├───config
...
```

#### Config values:

- PORT
- SESSION_KEY_DURATION
- SESSION_KEY_LENGTH

### Run tests

```
mvn test
```

### Run server

You can run the jar file included:

```
java -cp Server-1.0-SNAPSHOT.jar "com.playspace.Server"
```

Or use the exec maven plugin included:

```
mvn compile
mvn exec:java
```

### Postman

You will find a folder named Postman with some test requests.
