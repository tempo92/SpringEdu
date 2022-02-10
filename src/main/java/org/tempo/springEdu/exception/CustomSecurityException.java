package org.tempo.springEdu.exception;

public class CustomSecurityException extends RuntimeException {

    public CustomSecurityException(String msg) {
            super(msg);
        }
}
