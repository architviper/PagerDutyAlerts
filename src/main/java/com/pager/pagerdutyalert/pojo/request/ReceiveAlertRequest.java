package com.pager.pagerdutyalert.pojo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReceiveAlertRequest {

    @JsonProperty("team_id")
    private Long teamId;
}
