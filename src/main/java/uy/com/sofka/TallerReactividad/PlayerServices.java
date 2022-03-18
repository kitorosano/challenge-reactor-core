package uy.com.sofka.TallerReactividad;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlayerServices {

  @Autowired
  PlayerRepository playerRepository;

  
  public Flux<Player> getPlayers(Integer age, String club){
    
    // Find without filters, both null
    if(age == null && club == null)
      return playerRepository.findAll()
                            .buffer(100)
                            .flatMap(player -> Flux.fromStream(player.parallelStream()));
    
    // Find if only age is not null
    if(age != null && club == null)
      return playerRepository.findByAgeGreaterOrEqual(age)
                            .buffer(100)
                            .flatMap(player -> Flux.fromStream(player.parallelStream()));

    // Find if only club is not null
    if(age == null && club != null)
      return playerRepository.findByClub(club)
                            .buffer(100)
                            .flatMap(player -> Flux.fromStream(player.parallelStream()));

    // Find using both filters
    return playerRepository.findByAgeGreaterOrEqualAndClub(age, club)
                          .buffer(100)
                          .flatMap(player -> Flux.fromStream(player.parallelStream()));
  }

  public Mono<Map<String, Collection<Player>>> getRankingDescOnNationalities(){
    return playerRepository.findAll()
                          .buffer(100)
                          .flatMap(player -> Flux.fromStream(player.parallelStream()))
                          .sort((a, b) -> b.getWinners() - a.getWinners())
                          .collectMultimap(Player::getNational);
  }

}