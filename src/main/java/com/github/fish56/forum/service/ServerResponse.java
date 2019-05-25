package com.github.fish56.forum.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ServerResponse {
    public static ResponseEntity createErrorMessage(int status, String errorMessage){
        Map<String, String> map = new HashMap<>();
        map.put("message", errorMessage);
        return ResponseEntity.status(status).body(JSONObject.toJSONString(map));
    }
}
