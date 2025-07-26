package com.imaee.propinq.shared.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExceptionUtils {

    private static final String CONSTRUCTOR_EXCEPTION_MESSAGE = "Utility class";

    private ExceptionUtils() {
        throw new UnsupportedOperationException(CONSTRUCTOR_EXCEPTION_MESSAGE);
    }

    @FunctionalInterface
    public interface ThrowingSupplier<T> {
        T get() throws Exception;
    }

    @FunctionalInterface
    public interface ThrowingRunnable {
        void run() throws Exception;
    }

    /**
     * Executes the given supplier and catches any exception, wrapping it in a ResponseStatusException
     * with the specified HTTP status.
     *
     * @param supplier the operation to execute
     * @param status   the HTTP status to use if an exception occurs
     * @param <T>      the return type
     *
     * @return the result of the supplier
     *
     * @throws ResponseStatusException if any exception occurs during execution
     */
    public static <T> T runCatching(ThrowingSupplier<T> supplier, HttpStatus status) {
        try { return supplier.get(); }
        catch (Exception ex) { throw new ResponseStatusException(status, ex.getMessage(), ex); }
    }

    /**
     * Executes the given supplier and catches any exception, wrapping it in a ResponseStatusException
     * with the specified HTTP status and custom message.
     *
     * @param supplier the operation to execute
     * @param status   the HTTP status to use if an exception occurs
     * @param message  custom error message
     * @param <T>      the return type
     *
     * @return the result of the supplier
     *
     * @throws ResponseStatusException if any exception occurs during execution
     */
    public static <T> T runCatching(ThrowingSupplier<T> supplier, HttpStatus status, String message) {
        try { return supplier.get(); }
        catch (Exception ex) { throw new ResponseStatusException(status, message, ex); }
    }

    /**
     * Executes the given runnable and catches any exception, wrapping it in a ResponseStatusException
     * with the specified HTTP status.
     *
     * @param runnable the operation to execute
     * @param status   the HTTP status to use if an exception occurs
     *
     * @throws ResponseStatusException if any exception occurs during execution
     */
    public static void runCatching(ThrowingRunnable runnable, HttpStatus status) {
        try { runnable.run(); }
        catch (Exception ex) { throw new ResponseStatusException(status, ex.getMessage(), ex); }
    }
}