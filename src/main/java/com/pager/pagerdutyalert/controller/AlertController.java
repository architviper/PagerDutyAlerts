package com.pager.pagerdutyalert.controller;


import com.pager.pagerdutyalert.pojo.request.CreateTeamRequest;
import com.pager.pagerdutyalert.pojo.request.ReceiveAlertRequest;
import com.pager.pagerdutyalert.pojo.response.TeamResponse;
import com.pager.pagerdutyalert.service.AlertManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlertController {

    @Autowired
    private AlertManagerService alertManagerService;


    @RequestMapping(
            value = "/api/v1/createteam",
            method = RequestMethod.POST)
    public GenericApiResponse saveTeamInfo(@RequestBody CreateTeamRequest createTeamRequest) {
        try {
            alertManagerService.saveTeamInfo(createTeamRequest);
            return GenericApiResponse.success();
        } catch (Exception ex) {

            return GenericApiResponse.withError(ex.getMessage());
        }

    }


    @RequestMapping(
            value = "/api/v1/receivealert",
            method = RequestMethod.POST)
    public GenericApiResponse receiveAlert(@RequestBody ReceiveAlertRequest receiveAlertRequest) {
        try {
            alertManagerService.receiveAlert(receiveAlertRequest);
            return GenericApiResponse.success();
        } catch (Exception ex) {

            return GenericApiResponse.withError(ex.getMessage());
        }

    }



    @RequestMapping(
            value = "/api/v1/getteaminfo",
            method = RequestMethod.GET)
    public TeamResponse getTeamInfo() {
        try {
            return alertManagerService.getTeamInfo();
        } catch (Exception ex) {

            return null;
        }

    }
}
