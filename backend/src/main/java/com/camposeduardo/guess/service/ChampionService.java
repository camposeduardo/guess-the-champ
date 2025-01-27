package com.camposeduardo.guess.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ChampionService {

    @Autowired
    private RestTemplate restTemplate;

}
