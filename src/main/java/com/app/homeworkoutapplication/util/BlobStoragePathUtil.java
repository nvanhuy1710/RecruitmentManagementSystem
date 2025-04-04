package com.app.homeworkoutapplication.util;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BlobStoragePathUtil {

    @Value("${storage.container}")
    private String container;

    private String AVATAR_PATH = "/user/{id}/avatar/";

    public String getAvatarPath(Long id, String fileName) {
        Map<String, String> values = new HashMap<>();
        values.put("id", id.toString());
        return container + StringSubstitutor.replace(AVATAR_PATH, values, "{", "}") + fileName;
    }
}
