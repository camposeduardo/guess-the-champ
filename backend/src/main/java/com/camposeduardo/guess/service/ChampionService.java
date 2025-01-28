package com.camposeduardo.guess.service;

import com.camposeduardo.guess.entity.Champion;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class ChampionService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    public Champion generateRandomChampion() {
        List<String> champions = getAllChampionsName();
        String randomIndex = getRandomChampion(champions);
        return getChampionInfo(randomIndex);
    }

    public List<String> getAllChampionsName() {
        String URI = "https://ddragon.leagueoflegends.com/cdn/15.2.1/data/en_US/champion.json";
        Map<String, Object> response = (Map<String, Object>) restTemplate.getForObject(URI, Map.class).get("data");
        List<String> names = new ArrayList<>(response.keySet());
        return names;
    }

    public Champion getChampionInfo(String championName) {
        String url = "https://ddragon.leagueoflegends.com/cdn/15.2.1/data/en_US/champion/%s.json";
        String URI = String.format(url, championName);

        Map<String, Object> response = (Map<String, Object>) restTemplate.getForObject(URI, Map.class).get("data");
        Map<String, Object> championDetails = (Map<String, Object>) response.get(championName);

        return objectMapper.convertValue(championDetails, Champion.class);
    }

    public String getRandomChampion(List<String> champions) {
        Random generator = new Random();
        int index = (int) (Math.random() * champions.size() + 1);
        return champions.get(index);
    }
}
