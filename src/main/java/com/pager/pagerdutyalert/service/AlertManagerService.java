package com.pager.pagerdutyalert.service;

import com.pager.pagerdutyalert.entity.DeveloperEntity;
import com.pager.pagerdutyalert.entity.TeamEntity;
import com.pager.pagerdutyalert.pojo.request.CreateTeamRequest;
import com.pager.pagerdutyalert.pojo.request.Developer;
import com.pager.pagerdutyalert.pojo.request.ReceiveAlertRequest;
import com.pager.pagerdutyalert.pojo.request.Team;
import com.pager.pagerdutyalert.pojo.response.Data;
import com.pager.pagerdutyalert.pojo.response.TeamResponse;
import com.pager.pagerdutyalert.repository.DeveloperRepository;
import com.pager.pagerdutyalert.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlertManagerService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private DeveloperRepository developerRepository;




    @Transactional
    public void saveTeamInfo(CreateTeamRequest createTeamRequest) {
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setName(createTeamRequest.getTeam().getName());
        teamEntity = teamRepository.saveAndFlush(teamEntity);

        for(Developer developer : createTeamRequest.getDevelopers()) {
            DeveloperEntity developerEntity = new DeveloperEntity();
            developerEntity.setName(developer.getName());
            developerEntity.setTeamId(teamEntity.getId());
            developerEntity.setPhoneNumber(developer.getPhoneNumber());
            developerRepository.saveAndFlush(developerEntity);
        }

    }

    public void receiveAlert(ReceiveAlertRequest receiveAlertRequest) throws UnsupportedEncodingException {
        Optional<TeamEntity> teamEntity= teamRepository.findById(receiveAlertRequest.getTeamId());

        List<DeveloperEntity> developers = developerRepository.findAllById(teamEntity.get().getId());

        List<Developer> developerList = new ArrayList<>();
        for(DeveloperEntity developerEntity : developers) {
            Developer developer =  new Developer();
            developer.setName(developerEntity.getName());
            developer.setPhoneNumber(developerEntity.getPhoneNumber());
            developerList.add(developer);
        }
        int min = 0;
        int max = developerList.size()-1;

        int random = (int) (Math.random() * (max - min + 1) + min);
        Developer developer = developerList.get(random);

        String message = "call Triggered to " + developer.getName();

        //call the client

        HttpPost request = new HttpPost("https://run.mocky.io/v3/fd99c100-f88a-4d70-aaf7-393dbbd5d99f");
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Accept", "application/json");
        request.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/65.0.3325.181 Chrome/65.0.3325.181 Safari/537.36");



        String json = "{\"phone_number\": [" + developer.getPhoneNumber() + "],\"message\":  [" + message + "]}";
        StringEntity body = new StringEntity(json);
        request.setEntity(body);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            //System.out.println("status code " + response.getStatusLine().getStatusCode());
            //Thread.sleep(5000);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public TeamResponse getTeamInfo() {

        List<TeamEntity> teamEntities = teamRepository.findAll();

        List<Data> dataList= new ArrayList<>();
        Team team = new Team();
        for(TeamEntity teamEntity : teamEntities) {

            List<DeveloperEntity> developers = developerRepository.findAllById(teamEntity.getId());
            team.setName(teamEntity.getName());
            List<Developer> developerList = new ArrayList<>();
            for(DeveloperEntity developerEntity : developers) {
                Developer developer =  new Developer();
                developer.setName(developerEntity.getName());
                developer.setPhoneNumber(developerEntity.getPhoneNumber());
                developerList.add(developer);
            }
            Data data =  new Data();

            data.setTeam(team);
            data.setDevelopers(developerList);
            dataList.add(data);
        }
        TeamResponse teamResponse = new TeamResponse();
        teamResponse.setData(dataList);
        return teamResponse;

    }
}
