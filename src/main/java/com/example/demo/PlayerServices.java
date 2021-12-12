package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class PlayerServices {

    @Autowired
    PlayerRepository playerRepository;




    public String obtenerMayores34(String Club){
      /*  Flux<Player> list = CsvUtilFile.getPlayers();
*/
    /*     Flux.fromIterable(list)
                .filter(p -> p.getClub().equals(Club));*/
        return Club;
    }




}
