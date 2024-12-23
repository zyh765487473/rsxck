package com.qfwebsite.rsx.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class UrlUtils {

    public static Map<String, String> getParam(String conent) throws UnsupportedEncodingException {
        String[] pairs = conent.split("&");
        Map<String, String> params = new HashMap<>();
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String key = URLDecoder.decode(keyValue[0], "UTF-8");
            String value = URLDecoder.decode(keyValue[1], "UTF-8");
            params.put(key, value);
        }
        return params;
    }
}
