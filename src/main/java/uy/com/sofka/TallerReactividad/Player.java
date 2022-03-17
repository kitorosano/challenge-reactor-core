package uy.com.sofka.TallerReactividad;

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

    public String get_Id() {
        return _id;
    }

    public void set_Id(String _id) {
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
}
