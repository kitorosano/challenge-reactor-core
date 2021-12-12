package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class controller {
    @Autowired
    PlayerServices playerServices;


    @GetMapping("/{club}")
    public String getPlayers(@PathVariable("club") String Club){
        return playerServices.obtenerMayores34(Club);
    }

/*    @GetMapping("/{club}")
    public ResponseEntity<?> getNada(@PathVariable("club") String Club){
        return new ResponseEntity(Club, HttpStatus.OK);
    }*/


}
