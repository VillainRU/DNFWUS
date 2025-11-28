package com.example.DNFWUS;

import com.example.DNFWUS.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // 1. Говорим Spring: "Это глобальный обработчик ошибок"
public class GlobalExceptionHandler {

    // 2. Говорим: "Если где-то вылетела ResourceNotFoundException, запусти этот метод"
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {

        // 3. Формируем красивый JSON-ответ
        Map<String, String> response = new HashMap<>();
        response.put("error", "Not Found");
        response.put("message", ex.getMessage()); // Сообщение, которое мы написали в Сервисе

        // 4. Возвращаем статус 404 и наш JSON
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}