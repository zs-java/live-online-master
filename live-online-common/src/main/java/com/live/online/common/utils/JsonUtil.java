package com.live.online.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Json 转换工具类
 *
 * @author LiuJin
 * @author qi.jin@o-ibp.com
 */
public class JsonUtil {

    private static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper();

    static {
        //对日期格式化格式
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        DEFAULT_OBJECT_MAPPER
            // 注册预设模块
            .registerModule(new Jdk8Module())
            // 序列化时忽略 null 值
            .setSerializationInclusion(Include.NON_NULL)
            // 解序列化时允许出现 POJO 中未声明的属性
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            // 按 ISO 标准格式输出日期文本
            .registerModule(javaTimeModule)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    }

    private JsonUtil() {
    }

    public static ObjectMapper getDefaultObjectMapper() {
        return DEFAULT_OBJECT_MAPPER;
    }

    /**
     * 将 Json 内容解析后转为指定类型的对象
     *
     * @param content  Json 文本内容
     * @param classOfT 待转换的类
     * @return 若解析成功，返回对应的 {@code classOfT} 对象实例
     * @throws IllegalArgumentException 若解析失败，抛出
     */
    public static <T> T fromJsonString(String content, Class<T> classOfT) throws IllegalArgumentException {
        try {
            return DEFAULT_OBJECT_MAPPER.readValue(content, classOfT);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T fromJson(JsonNode node, Class<T> classOfT) {
        try {
            return DEFAULT_OBJECT_MAPPER.treeToValue(node, classOfT);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 将指定 POJO 对象转换为 Json 字符串文本
     *
     * @param object 待转换的对象
     * @return 转换后的 Json 字符串文本
     */
    public static String toJsonString(Object object) {
        try {
            return DEFAULT_OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static JsonNode toJson(Object object) {
        return DEFAULT_OBJECT_MAPPER.valueToTree(object);
    }

    /**
     * 创建一个 {@link ObjectNode} 实例
     *
     * @return {@link ObjectNode} 实例
     */
    public static ObjectNode newObject() {
        return DEFAULT_OBJECT_MAPPER.createObjectNode();
    }

    /**
     * 创建一个 {@link ArrayNode} 实例
     *
     * @return {@link ArrayNode} 实例
     */
    public static ArrayNode newArray() {
        return DEFAULT_OBJECT_MAPPER.createArrayNode();
    }

    /**
     * @param json  需要转换为json对象的json字符串
     * @param clazz 需要转换对象的类型
     * @return
     * @Description json转对象
     * @deprecated 请使用 {@link #fromJsonString(String, Class)})} 方法
     */
    @Deprecated
    public static <T> T parseObject(String json, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        return fromJsonString(json, clazz);
    }

    /**
     * @param object 需要转换的对象
     * @return
     * @Description 对象转json字符串
     * @deprecated 请使用 {@link #toJsonString(Object)} 方法
     */
    @Deprecated
    public static <T> String toJSONString(T object) {
        if (object == null) {
            return null;
        }
        return toJsonString(object);
    }

}
