package com.camposeduardo.guess.controller;


import com.camposeduardo.guess.service.ChampionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ChampionController {

    @Autowired
    private ChampionService championService;
}
