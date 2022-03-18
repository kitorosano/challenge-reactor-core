package uy.com.sofka.TallerReactividad;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class PlayerController {
  @Autowired
  private PlayerServices playerServices;

  @GetMapping(value = "/jugadores")
  public ResponseEntity<?> getAllPlayers(@RequestParam(value = "age", required = false) Integer age, @RequestParam(value = "club", required = false) String club){
    try {
      Flux<Player> players = playerServices.getPlayers(age, club);
      
      return new ResponseEntity<Flux<Player>>(players, HttpStatus.OK);
    }catch (Exception e){
      return new ResponseEntity<String>(e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // @GetMapping(value = "/ranking")
  // public ResponseEntity<String> getRankingsNationalities(){
  //   try {
  //     Mono<Map<String, Collection<Player>>> ranking = playerServices.getRankingDescOnNationalities();

  //     String response = "";
  //     ranking.share().block().forEach((national, playerList) -> {
  //       response.concat("\n" + national);
  //       playerList.stream().forEach(player-> response.concat(player.name + "- Partidos ganados: " + player.winners));
  //     });
  // 
  //     return new ResponseEntity<>(response, HttpStatus.OK);
  //   }catch (Exception e){
  //     return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  //   }
  // }


}
