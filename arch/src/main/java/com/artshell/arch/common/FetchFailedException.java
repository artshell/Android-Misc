package com.artshell.arch.common;

/**
 * @author artshell on 2018/7/31
 */
public class FetchFailedException extends IllegalStateException {
    public FetchFailedException() {
    }

    public FetchFailedException(String s) {
        super(s);
    }

    public FetchFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public FetchFailedException(Throwable cause) {
        super(cause);
    }
}
