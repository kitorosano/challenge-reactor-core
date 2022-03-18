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
  // private static final Logger log = LoggerFactory.getLogger(PlayerRepository.class);

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
    Integer age = 35;

    Flux<Player> players = csvUtilFile.getPlayers();
    Mono<List<Player>> listFilter = players
                              .filter(player -> player.age >= age)
                              .buffer(100)
                              .flatMap(playerBuffer -> Flux.fromIterable(playerBuffer))
                              .collectList();

    listFilter.block().forEach((player) -> {
      System.out.println("\n" + player);
    });
    assertEquals(5, listFilter.block().size());
  }

  @Test
  void reactive_filtrarJugadoresEquipoJuventus(){
    String equipo = "Juventus";

    Flux<Player> players = csvUtilFile.getPlayers();
    Mono<List<Player>> listFilter = players
                                    .filter(player -> player.getClub().equals(equipo))
                                    .buffer(100)
                                    .flatMap(playerBuffer -> Flux.fromIterable(playerBuffer))
                                    .collectList();

    listFilter.block().forEach((player) -> {
      System.out.println("\n" + player);
    });

    assertEquals(15, listFilter.block().size());
  }

  @Test
  void reactive_RankingJugadoresPorNacionalidad() {
    Flux<Player> players = csvUtilFile.getPlayers();
    Mono<Map<String, Collection<Player>>> listFilter = players
                                                      .sort((a, b) -> b.getWinners() - a.getWinners())
                                                      .collectMultimap(Player::getNational);

    listFilter.block().forEach((national, playerList) -> {
      System.out.println("\n" + national);
      playerList.stream().forEach(player-> System.out.println(player.name + "- Partidos ganados: " + player.winners));
    });
  }
}

