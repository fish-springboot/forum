package com.github.fish56.forum.service;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 获得出错的报文
 */
public class ErrorResponseEntity {
    public static ResponseEntity get(int status, String message){
        Map<String, String> resultMessage = new HashMap<>();
        resultMessage.put("message", message);
        return ResponseEntity.status(status).body(resultMessage);
    }
}
