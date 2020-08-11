package com.live.online.common.base.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

/**
 * Json 转换工具类
 * @author 朱帅
 */
public class JsonUtils {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    public static JsonNode parse(String json) {
        return parseObject(json, JsonNode.class);
    }

    public static ObjectNode parseObject(String json) {
        return parseObject(json, ObjectNode.class);
    }

    public static <T> T parseObject(String json, Class<T> valueType) {
        try {
            return MAPPER.readValue(json, valueType);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T parseObject(JsonNode node, Class<T> valueType) {
        try {
            return MAPPER.treeToValue(node, valueType);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> List<T> parseArray(JsonNode node, Class<T> valueType) {
        return parseArray(node.asText(), valueType);
    }

    public static <T> List<T> parseArray(String jsonStr, Class<T> valueType) {
        try {
            JavaType t = MAPPER.getTypeFactory().constructParametricType(List.class, valueType);
            return MAPPER.readValue(jsonStr, t);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String toJsonString(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String toJsonStringWithPrettyPrinter(Object object) {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static JsonNode toJsonNode(Object object) {
        return MAPPER.valueToTree(object);
    }

    public static ObjectNode newObjectNode() {
        return MAPPER.createObjectNode();
    }

    public static ArrayNode newArrayNode() {
        return MAPPER.createArrayNode();
    }

}