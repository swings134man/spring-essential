package com.lucas.cloudgateway.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @package : com.lucas.cloudgateway.utils
 * @name : JsonUtil.java
 * @date : 2025. 3. 20. 오후 4:09
 * @author : lucaskang(swings134man)
 * @Description: Json <-> Object Convert Util
**/
@Component
@RequiredArgsConstructor
public class JsonUtil {
    private final ObjectMapper objectMapper;
    private static ObjectMapper mapper;

    @PostConstruct
    public void init() {
        mapper = this.objectMapper;
    }

    public static ObjectMapper getMapper() {
        return JsonUtil.mapper;
    }

    public static <T> T toObject(String json, TypeReference<T> typeRef) throws IOException {
        return mapper.readValue(json, typeRef);
    }

    public static <T> T toObject(Object object, TypeReference<T> typeRef) {
        return mapper.convertValue(object, typeRef);
    }

    public static <T> T toObject(Map<?, ?> map, Class<T> toValueType) {
        return mapper.convertValue(map, toValueType);
    }

    public static <T> T toObject(String json, Class<T> toValueType) throws IOException {
        return mapper.readValue(json, toValueType);
    }


    public static <T> List<T> toListObject(String json, Class<T> toValueType) throws IOException {
        JavaType type = JsonUtil.getMapper().getTypeFactory().constructCollectionType(List.class, toValueType);
        return mapper.readValue(json, type);
    }

    public static String toJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }
}
