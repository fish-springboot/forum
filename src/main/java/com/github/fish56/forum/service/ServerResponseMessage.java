package com.github.fish56.forum.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回一个转态码 + 一个message是很常见的事情
 * 所以这里写一个函数来简化这个行为
 */
public class ServerResponseMessage {
    public static ResponseEntity get(int status, String message){
        Map<String, String> map = new HashMap<>();
        map.put("message", message);
        return ResponseEntity.status(status).body(JSONObject.toJSONString(map));
    }
}
