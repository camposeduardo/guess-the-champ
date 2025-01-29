package com.camposeduardo.guess.service;

import com.camposeduardo.guess.entity.Champion;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${ddragon.url.allchampions}")
    private String url_allchampions;

    @Value("${ddragon.url.singlechampion}")
    private String url_singlechampion;

    public String generateRandomChampionLore() {
        List<String> champions = getAllChampionsName();
        // JarvanIV, Nunu, Velkoz there's a match problem in the response from the API, fix it later
        String champion = getRandomChampion(champions);
        return getChampionLore(champion);
    }

    public List<String> getAllChampionsName() {
        Map<String, Object> response = (Map<String, Object>) restTemplate.getForObject(url_allchampions, Map.class).get("data");
        List<String> names = new ArrayList<>(response.keySet());
        return names;
    }

    public String getChampionLore(String championName) {
        String URL = String.format(url_singlechampion, championName);

        Map<String, Object> response = (Map<String, Object>) restTemplate.getForObject(URL, Map.class).get("data");
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
