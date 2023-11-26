package com.lauro.correia.reactive.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class JsonUtils {

    private static final String ROOT_JSON_PATH = "src/test/resources/json/";

    protected static <T> T parseToJavaObject(final File src, final Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(src, clazz);
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
}
