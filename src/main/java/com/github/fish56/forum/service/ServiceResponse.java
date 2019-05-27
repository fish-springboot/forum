package com.github.fish56.forum.service;

import lombok.Data;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Data
public class ServiceResponse<T> {
    private Integer errorStatus;
    private String errorMessage;
    private T data;

    public boolean hasError(){
        return errorMessage != null;
    }
    public boolean isSuccess(){
        return errorMessage == null;
    }
    public ResponseEntity getErrorResponseEntity(){
        if (isSuccess()){
            throw new RuntimeException("没有异常信息，无法创建错误报文");
        }
        Map<String, String> map = new HashMap<>();
        map.put("message", errorMessage);
        return ResponseEntity.status(errorStatus).body(map);
    }

    /**
     * 这里并不知道成功的状态吗是多少，需要调用者来决定
     * @param status
     * @return
     */
    public ResponseEntity getSuccessResponseEntity(int status){
        if (hasError()){
            throw new RuntimeException("有异常信息，无法创建成功的响应报文");
        }
        return ResponseEntity.status(status).body(data);
    }

    /**
     * 给个默认值200
     * @return
     */
    public ResponseEntity getSuccessResponseEntity(){
        if (hasError()){
            throw new RuntimeException("有异常信息，无法创建成功的响应报文");
        }
        return ResponseEntity.status(200).body(data);
    }

    public static ServiceResponse getInstance(int errorStatus, String errorMessage){
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setErrorStatus(errorStatus);
        serviceResponse.setErrorMessage(errorMessage);
        return serviceResponse;
    }
    public static ServiceResponse getInstance(Object data){
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setData(data);
        return serviceResponse;
    }
}
