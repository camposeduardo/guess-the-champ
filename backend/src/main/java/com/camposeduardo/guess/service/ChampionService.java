package com.camposeduardo.guess.service;

import com.camposeduardo.guess.entity.Champion;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ChampionService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public String generateRandomChampionLore() {
        List<String> champions = getAllChampionsName();
        // JarvanIV and Nunu there's a match problem in the response from the API, fix it later
        String champion = getRandomChampion(champions);
        return getChampionLore(champion);
    }

    public List<String> getAllChampionsName() {
        String URI = "https://ddragon.leagueoflegends.com/cdn/15.2.1/data/en_US/champion.json";
        Map<String, Object> response = (Map<String, Object>) restTemplate.getForObject(URI, Map.class).get("data");
        List<String> names = new ArrayList<>(response.keySet());
        return names;
    }

    public String getChampionLore(String championName) {
        String url = "https://ddragon.leagueoflegends.com/cdn/15.2.1/data/en_US/champion/%s.json";
        String URI = String.format(url, championName);

        Map<String, Object> response = (Map<String, Object>) restTemplate.getForObject(URI, Map.class).get("data");
        Map<String, Object> championDetails = (Map<String, Object>) response.get(championName);

        Champion champion = objectMapper.convertValue(championDetails, Champion.class);
        String championLore = champion.getLore();
        String toReplace = null;

        if (champion.getName().contains(" ")) {
            toReplace = champion.getName();
        } else {
            toReplace = champion.getId();
        }

        // removing the champion's name from the lore
        return championLore.replaceAll(toReplace, "*".repeat(toReplace.length()));
    }

    public String getRandomChampion(List<String> champions) {
        int index = (int) (Math.random() * champions.size() + 1);
        return champions.get(index);
    }
}
