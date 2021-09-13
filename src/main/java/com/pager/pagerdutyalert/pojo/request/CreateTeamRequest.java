package com.pager.pagerdutyalert.pojo.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateTeamRequest {
    private Team team;

    private List<Developer> developers;
}
