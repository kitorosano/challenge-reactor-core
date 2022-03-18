package uy.com.sofka.TallerReactividad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class PlayerServices {

  @Autowired
  PlayerRepository playerRepository;

  
  public Flux<Player> getPlayers(){
    Flux<Player> players = playerRepository
                          .findAll()
                          .buffer(100)
                          .flatMap(player -> Flux.fromStream(player.parallelStream()));
    return players;
  }

}