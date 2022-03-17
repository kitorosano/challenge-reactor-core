package uy.com.sofka.TallerReactividad;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@DataMongoTest
public class CSVUtilTest {
  private static final Logger log = LoggerFactory.getLogger(PlayerRepository.class);

  @Autowired
  public PlayerRepository playerRepository;

  private CsvUtilFile csvUtilFile;

  @BeforeEach
  void before(){
      this.csvUtilFile= new CsvUtilFile(playerRepository);
  }

  @Test
  void reactive_obtenerJugadores(){
    List<Player> players = csvUtilFile.getPlayers()
                          .collectList()
                          .block();

    assertEquals(199, players.size());
  }

  
  @Test
  void reactive_filtrarJugadoresMayoresA35(){
    Flux<Player> players = csvUtilFile.getPlayers();
    Mono<Map<String, Collection<Player>>> listFilter = players
                                                      .filter(player -> player.age >= 35)
                                                      .map(player -> {
                                                        player.name = player.name.toUpperCase(Locale.ROOT);
                                                        return player;
                                                      })
                                                      .buffer(100)
                                                      .flatMap(playerA -> players
                                                              .filter(playerB -> playerA.stream()
                                                                      .anyMatch(a ->  a.club.equals(playerB.club)))
                                                      )
                                                      .distinct()
                                                      .collectMultimap(Player::getClub);

    assertEquals(5, listFilter.block().size());
  }
}
