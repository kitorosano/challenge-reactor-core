package uy.com.sofka.TallerReactividad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import uy.com.sofka.TallerReactividad.PlayerRepository;

@Service
public class PlayerServices {

  @Autowired
  PlayerRepository playerRepository;

}