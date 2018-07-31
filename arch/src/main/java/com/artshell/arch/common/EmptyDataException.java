package com.artshell.arch.common;

/**
 * @author artshell on 2018/7/31
 */
public class EmptyDataException extends IllegalStateException {
    public EmptyDataException() {
    }

    public EmptyDataException(String s) {
        super(s);
    }

    public EmptyDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyDataException(Throwable cause) {
        super(cause);
    }
}
