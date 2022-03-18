package uy.com.sofka.TallerReactividad;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

public interface PlayerRepository extends ReactiveMongoRepository<Player,String> {

  @Query(value = "{'age' : {'$gte': ?0}}")
  Flux<Player> findByAgeGreaterOrEqual(Integer age);

  @Query(value = "{'club' : '?0'}")
  Flux<Player> findByClub(String club);

  @Query(value = "{'age' : {'$gte': ?0}, 'club' : '?1'}")
  Flux<Player> findByAgeGreaterOrEqualAndClub(Integer age, String club);
  
}
