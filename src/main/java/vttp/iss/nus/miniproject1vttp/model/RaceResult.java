package vttp.iss.nus.miniproject1vttp.model;

import java.io.Serializable;
import java.util.List;

public class RaceResult implements Serializable{
 
    String season;
    String raceName;
    List<ResultDetails> raceResults;
    
    public String getSeason() {
        return season;
    }
    public void setSeason(String season) {
        this.season = season;
    }
    public String getRaceName() {
        return raceName;
    }
    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }
    public List<ResultDetails> getRaceResults() {
        return raceResults;
    }
    public void setRaceResults(List<ResultDetails> raceResults) {
        this.raceResults = raceResults;
    }
    public RaceResult(String season, String raceName, List<ResultDetails> raceResults) {
        this.season = season;
        this.raceName = raceName;
        this.raceResults = raceResults;
    }
    public RaceResult() {
    }

}