package main.java.com.app.quantitymeasurement.Exception;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message)                  { super(message); }
    public DatabaseException(String message, Throwable cause) { super(message, cause); }
}