package uy.com.sofka.TallerReactividad;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.exceptions.CsvException;

public class CsvUtilFile {
  public PlayerRepository playerRepository;

  private CsvUtilFile(){}

  public CsvUtilFile(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  public List<Player> getPlayers() throws URISyntaxException,IOException, CsvException {
    List<Player> players = new ArrayList<Player>();
    
    String csvFile = CsvUtilFile.class.getClassLoader().getResource("data.min.csv").getFile();
    CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
    
    try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(csvFile))
                                    .withCSVParser(parser)
                                    .build()) {
    
      //READ CSV LINE BY LINE
      List<String[]> csvLines = new ArrayList<>();
      String[] line = csvReader.readNext(); // first line is not going to count
      while ((line = csvReader.readNext()) != null) {
        csvLines.add(line);
      }
      
      csvLines.forEach(csvLine -> players.add(
                        new Player(
                            csvLine[0], //id
                            csvLine[1], //name
                            Integer.parseInt(csvLine[2].trim()), //age
                            csvLine[3], //icon
                            csvLine[4], //national
                            Integer.parseInt(csvLine[5].trim()), //winners
                            Integer.parseInt(csvLine[6].trim()), //games
                            csvLine[7] //club
                        )
      ));
    }
    return players;
  }
  
}
