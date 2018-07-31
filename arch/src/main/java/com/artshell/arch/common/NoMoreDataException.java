package com.artshell.arch.common;

/**
 * @author artshell on 2018/7/31
 */
public class NoMoreDataException extends IllegalStateException {
    public NoMoreDataException() {
    }

    public NoMoreDataException(String s) {
        super(s);
    }

    public NoMoreDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoMoreDataException(Throwable cause) {
        super(cause);
    }
}
