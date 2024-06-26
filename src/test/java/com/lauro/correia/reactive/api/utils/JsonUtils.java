package com.lauro.correia.reactive.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lauro.correia.reactive.api.model.Post;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public abstract class JsonUtils {

    private static final String ROOT_JSON_PATH = "src/test/resources/json/";
    private static ObjectMapper mapper = new ObjectMapper();

    protected static <T> T parseToJavaObject(final File file, final Class<T> clazz) {
        try {
            return mapper.readValue(file, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON to Java Object", e);
        }
    }

    protected static File getJsonFile(final String jsonFile) {
        try {
            return ResourceUtils.getFile(ROOT_JSON_PATH + jsonFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("JSON File not found");
        }
    }

    protected static <T> List<T> parseToList(File file, Class<T> clazz) {
        try {
            return mapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON to List");
        }
    }
}
