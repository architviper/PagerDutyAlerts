package com.pager.pagerdutyalert.pojo.response;


import com.pager.pagerdutyalert.pojo.request.Developer;
import com.pager.pagerdutyalert.pojo.request.Team;

import java.util.List;

@lombok.Data
public class Data {

    private Team team;
    private List<Developer> developers;
}
