package com.arch.mvp;

/**
 * Created by Chatikyan on 01.07.2017.
 */

class ArchMvpException extends RuntimeException {

    ArchMvpException(String message) {
        super(message);
    }

    ArchMvpException(String message, Throwable cause) {
        super(message, cause);
    }

    ArchMvpException(Throwable cause) {
        super(cause);
    }
}
