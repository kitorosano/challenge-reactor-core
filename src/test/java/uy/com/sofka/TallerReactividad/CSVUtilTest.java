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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
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

  @Test
  void gettingCsvUrl() {
    URL resource = CSVUtilTest.class.getClassLoader().getResource("data.min.csv");
    System.out.println(resource.getFile());
    System.out.println(resource.getPath());
  }

  @BeforeEach
  void before(){
    this.csvUtilFile= new CsvUtilFile(playerRepository);
  }

  //WORKING WITH MINIFIED DATA.CSV FILE (200 Lines)

  @Test
  void reactive_min_obtenerJugadores() throws IOException, URISyntaxException, CsvException{
    List<Player> players = csvUtilFile.getPlayers();
    assertEquals(199, players.size());
  }

  @Test
  void reactive_min_filtrarJugadoresMayoresA35() throws IOException, URISyntaxException, CsvException{
    Integer age = 35;

    List<Player> players = csvUtilFile.getPlayers();
    Flux<Player> listFlux = Flux.fromStream(players.parallelStream()).cache();
    Mono<List<Player>> listFilter = listFlux
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
  void reactive_min_filtrarJugadoresEquipoJuventus() throws IOException, URISyntaxException, CsvException{
    String equipo = "Juventus";

    List<Player> players = csvUtilFile.getPlayers();
    Flux<Player> listFlux = Flux.fromStream(players.parallelStream()).cache();
    Mono<List<Player>> listFilter = listFlux
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
  void reactive_min_RankingJugadoresPorNacionalidad() throws IOException, URISyntaxException, CsvException{
    List<Player> players = csvUtilFile.getPlayers();
    Flux<Player> listFlux = Flux.fromStream(players.parallelStream()).cache();
    Mono<Map<String, Collection<Player>>> listFilter = listFlux
                                                      .sort((a, b) -> b.getWinners() - a.getWinners())
                                                      .collectMultimap(Player::getNational);

    listFilter.block().forEach((national, playerList) -> {
      System.out.println("\n" + national);
      playerList.stream().forEach(player-> System.out.println(player.name + "- Partidos ganados: " + player.winners));
    });
  }

  // ORIGINAL DATA.CSV FILE (18207 Lines)

  @Test
  void reactive_obtenerJugadores() throws IOException, URISyntaxException, CsvException{
    List<Player> players = csvUtilFile.getPlayers();
    assertEquals(18207, players.size());
  }

  @Test
  void reactive_filtrarJugadoresMayoresA35() throws IOException, URISyntaxException, CsvException{
    Integer age = 35;

    List<Player> players = csvUtilFile.getPlayers();
    Flux<Player> listFlux = Flux.fromStream(players.parallelStream()).cache();
    Mono<List<Player>> listFilter = listFlux
                              .filter(player -> player.getAge() >= age)
                              .buffer(100)
                              .flatMap(playerBuffer -> Flux.fromIterable(playerBuffer))
                              .collectList();

    listFilter.block().forEach((player) -> {
      System.out.println("\n" + player);
    });
    assertEquals(489, listFilter.block().size());
  }

  @Test
  void reactive_filtrarJugadoresEquipoJuventus() throws IOException, URISyntaxException, CsvException{
    String equipo = "Juventus";

    List<Player> players = csvUtilFile.getPlayers();
    Flux<Player> listFlux = Flux.fromStream(players.parallelStream()).cache();
    Mono<List<Player>> listFilter = listFlux
                                    .filter(player -> player.getClub().equals(equipo))
                                    .buffer(100)
                                    .flatMap(playerBuffer -> Flux.fromIterable(playerBuffer))
                                    .collectList();

    listFilter.block().forEach((player) -> {
      System.out.println("\n" + player);
    });

    assertEquals(25, listFilter.block().size());
  }

  @Test
  void reactive_RankingJugadoresPorNacionalidad() throws IOException, URISyntaxException, CsvException{
    List<Player> players = csvUtilFile.getPlayers();
    Flux<Player> listFlux = Flux.fromStream(players.parallelStream()).cache();
    Mono<Map<String, Collection<Player>>> listFilter = listFlux
                                                      .sort((a, b) -> b.getWinners() - a.getWinners())
                                                      .collectMultimap(Player::getNational);

    listFilter.block().forEach((national, playerList) -> {
      System.out.println("\n" + national);
      playerList.stream().forEach(player-> System.out.println(player.name + "- Partidos ganados: " + player.winners));
    });
  }
}

