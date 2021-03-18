package com.demo.config;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Priority;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomControllerAdvice implements ResponseBodyAdvice {

    private static final Logger logger = LoggerFactory.getLogger(CustomControllerAdvice.class);

//    @ExceptionHandler(value = Exception.class)
//    public Map errorHandler(Exception ex) {
//        Map map = new HashMap();
//        map.put("code", 500);
//        map.put("msg", ex.getMessage());
//        logger.error("handle error:", ex);
//        return map;
//    }
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType, Class selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {


        // handle execptions
        if(body instanceof Map && ((Map) body).get("status") != null){
            return body;
        }
        Map map = new HashMap();
        // success return
        map.put("code", 20000);
        if(body instanceof IPage){
            Map<String, Object> data  = new HashMap<>();
            data.put("items", ((IPage)(body)).getRecords());
            data.put("total", ((IPage)(body)).getTotal());
            data.put("pages", ((IPage)(body)).getPages());
            map.put("data", data);
        }else {
            map.put("data", body);
        }
        // for stringHttpConverter
        if(selectedConverterType == StringHttpMessageConverter.class){
            try {
                return jacksonObjectMapper.writeValueAsString(map);
            } catch (JsonProcessingException e) {
                logger.error("jackson parse error", e);
                return "jackson parse error";
            }
        }
        return map;
    }
}
