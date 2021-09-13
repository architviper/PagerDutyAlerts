package com.pager.pagerdutyalert.pojo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Developer {

    private String name;

    @JsonProperty("phone_number")
    private String phoneNumber;
}
