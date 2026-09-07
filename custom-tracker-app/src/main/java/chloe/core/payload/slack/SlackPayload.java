package chloe.core.payload.slack;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SlackPayload {

    private String teamId;
    private String teamName;

    private String enterpriseId;
    private String enterpriseName;

    private String channelId;
    private String channelName;

    private String userId;
    private String userName;

    private String command;
    private String text;

    private String responseUrl;
    private String triggerId;
    private String apiAppId;
}
