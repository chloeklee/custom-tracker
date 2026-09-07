package chloe.customtrackerapp.ui.news.request;

import chloe.core.exception.BusinessException;
import chloe.core.payload.ResponseCode;
import chloe.core.payload.slack.SlackPayload;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.function.Consumer;

@Getter
public class SubscribeRequest {

    private String time;
    private String language;

    private static final String TIME_FORMAT = "^([01]\\d|2[0-3]):([0-5]\\d)$";
    private static final String DEFAULT_TIME = "09:00";

    private static final Set<String> VALID_LANGUAGES = new HashSet<>(Arrays.asList(Locale.getISOLanguages()));
    private static final String DEFAULT_LANGUAGE = Locale.ENGLISH.getLanguage();

    private static final SubscribeRequest DEFAULT_REQUEST = new SubscribeRequest(DEFAULT_TIME, DEFAULT_LANGUAGE);

    private final Map<String, Consumer<String>> validators = Map.of(
            "time", time -> {

                if (StringUtils.isBlank(time) || !time.matches(TIME_FORMAT)) {

                    throw new InvalidParameterException("Invalid time parameter input: "+time);
                }

                this.time = time;
            },
            "lang", lang -> {

                if (!VALID_LANGUAGES.contains(lang)) {

                    throw new InvalidParameterException("Invalid lang parameter input: "+lang);
                }

                this.language = lang;
            }
    );

    private SubscribeRequest(String time, String language) {

        this.time = time;
        this.language = language;
    }

    public static SubscribeRequest from(Object object) {

        if (object instanceof SlackPayload slackPayload) {

            return from(slackPayload);
        }

        throw BusinessException.from(ResponseCode.INTERNAL_SERVER_ERROR); // TODO: more detailed exception
    }

    private static SubscribeRequest from(SlackPayload payload) {

        SubscribeRequest request = DEFAULT_REQUEST;

        String text = payload.getText();
        if (StringUtils.isBlank(text)) {

            return request;
        }

        Arrays.stream(text.trim().split("--"))
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .filter(t -> request.validators.keySet().stream().anyMatch(t::startsWith))
                .forEach(paramText -> {

                    if (StringUtils.isBlank(paramText)) {

                        throw new InvalidParameterException("Undefined parameter exists");
                    }

                    String[] params = paramText.split("\\s+");
                    if (params.length != 2) {

                       throw new InvalidParameterException("Parameter value not found:: "+params[0]);
                    }

                    request.validators
                            .get(params[0])
                            .accept(params[1]);
                });

        return request;
    }
}
