package com.ultraplexcinema.error;

public class CinemaNotFoundException extends RuntimeException {
    public CinemaNotFoundException(String message) {
        super(message);
    }
}
