package uy.com.sofka.TallerReactividad;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import reactor.core.publisher.Flux;
import uy.com.sofka.TallerReactividad.PlayerRepository;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CsvUtilFile {
  public PlayerRepository playerRepository;
  
  private CsvUtilFile(){}

  public CsvUtilFile(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
}

  public Flux<Player> getPlayers(){
    Flux<Player> players = playerRepository
                          .findAll()
                          .buffer(100)
                          .flatMap(player -> Flux.fromStream(player.parallelStream()));
    return players;
  }
  
}
