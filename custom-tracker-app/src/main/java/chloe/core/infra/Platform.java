package chloe.core.infra;

import java.security.InvalidParameterException;
import java.util.Arrays;

public enum Platform {

    SLACK
    ;

    public static Platform find(String platform) {

        return Arrays.stream(Platform.values())
                .filter(p -> p.name().equals(platform.toUpperCase()))
                .findFirst()
                .orElseThrow(InvalidParameterException::new);
    }
}
