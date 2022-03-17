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
    assertEquals(98, players.size());
  }
}
