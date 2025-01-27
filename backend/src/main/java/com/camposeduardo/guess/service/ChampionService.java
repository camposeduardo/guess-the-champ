package com.camposeduardo.guess.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ChampionService {

    @Autowired
    private RestTemplate restTemplate;

    public List<String> getAllChampionsName() {
        String URI = "https://ddragon.leagueoflegends.com/cdn/15.2.1/data/en_US/champion.json";
        Map<String, Object> response = (Map<String, Object>) restTemplate.getForObject(URI, Map.class).get("data");
        List<String> names = new ArrayList<>(response.keySet());
        return names;
    }
}
