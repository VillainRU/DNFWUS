package com.example.DNFWUS;

// Наследуемся от RuntimeException, чтобы не писать try-catch
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}