package uy.com.sofka.TallerReactividad;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "PlayersMin")
public class Player {

  @Id
  public String _id;
  public String name;
  public int age;
  public String icon;
  public String national;
  public int winners;
  public int games;
  public String club;

  public Player(){}

  public Player(String _id, String name, int age, String icon, String national, int winners, int games, String club) {
    this._id = _id;
    this.name = name;
    this.age = age;
    this.icon = icon;
    this.national = national;
    this.winners = winners;
    this.games = games;
    this.club = club;
  }

  public String get_id() {
    return this._id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }


  public String getName() {
      return name;
  }

  public void setName(String name) {
      this.name = name;
  }

  public int getAge() {
      return age;
  }

  public void setAge(int age) {
      this.age = age;
  }

  public String getIcon() {
      return icon;
  }

  public void setIcon(String icon) {
      this.icon = icon;
  }

  public String getNational() {
      return national;
  }

  public void setNational(String national) {
      this.national = national;
  }

  public int getWinners() {
      return winners;
  }

  public void setWinners(int winners) {
      this.winners = winners;
  }

  public int getGames() {
      return games;
  }

  public void setGames(int games) {
      this.games = games;
  }

  public String getClub() {
      return club;
  }

  public void setClub(String club) {
      this.club = club;
  }


  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Player)) {
      return false;
    }
    Player player = (Player) o;
    return Objects.equals(_id, player._id) && Objects.equals(name, player.name) && age == player.age && Objects.equals(icon, player.icon) && Objects.equals(national, player.national) && winners == player.winners && games == player.games && Objects.equals(club, player.club);
  }

  @Override
  public int hashCode() {
    return Objects.hash(_id, name, age, icon, national, winners, games, club);
  }
  

  @Override
  public String toString() {
    return "{" +
      " _id='" + get_id() + "'" +
      ", name='" + getName() + "'" +
      ", age='" + getAge() + "'" +
      "[...], club='" + getClub() + "'" +
      "}";
  }

}
