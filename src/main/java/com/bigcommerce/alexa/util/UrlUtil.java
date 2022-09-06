package com.bigcommerce.alexa.util;

import java.util.Arrays;
import java.util.Optional;

public class UrlUtil {
    public static String createUrl(String baseUrl, String... paths) {
        final Optional<String> oPath = Arrays.stream(paths).reduce((path1, path2) -> path1.concat("/").concat(path2));
        return oPath.map(s -> baseUrl.concat("/").concat(s))
          .orElse(baseUrl);
    }
}