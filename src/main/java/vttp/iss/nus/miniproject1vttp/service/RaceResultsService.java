package vttp.iss.nus.miniproject1vttp.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import vttp.iss.nus.miniproject1vttp.model.RaceResult;
import vttp.iss.nus.miniproject1vttp.model.ResultDetails;

@Service
public class RaceResultsService {

    private final Logger logger = Logger.getLogger(RaceResultsService.class.getName());

    private final RestTemplate restTemplate;

    public RaceResultsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // @Cacheable("availableYears")
    public List<String> getAvailableYear() {
        String apiUrl = "https://ergast.com/api/f1/seasons.json?limit=1000&offset=41.json";

        try {
            String json = restTemplate.getForObject(apiUrl, String.class);
            JsonReader reader = Json.createReader(new StringReader(json));

            JsonObject mrData = reader.readObject().getJsonObject("MRData");
            JsonObject seasonTable = mrData.getJsonObject("SeasonTable");
            JsonArray seasons = seasonTable.getJsonArray("Seasons");

            List<String> availableYears = new ArrayList<>();

            for (JsonValue seasonValue : seasons) {
                JsonObject seasonJson = seasonValue.asJsonObject();
                String season = seasonJson.getString("season");
                availableYears.add(season);
            }

            return availableYears;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<String> getAvailableRounds(String year) {
        String url = "https://ergast.com/api/f1/" + year + ".json";

        // System.out.println(url);

        try {
            String json = restTemplate.getForObject(url, String.class);
            JsonReader reader = Json.createReader(new StringReader(json));

            logger.info(url);
            // logger.info(json);

            JsonObject mrData = reader.readObject().getJsonObject("MRData");
            JsonObject raceTable = mrData.getJsonObject("RaceTable");
            JsonArray races = raceTable.getJsonArray("Races");

            List<String> availableRounds = new ArrayList<>();
            List<String> raceNames = new ArrayList<>();

            for (JsonValue raceValue : races) {
                JsonObject raceJson = raceValue.asJsonObject();
                String round = raceJson.getString("round");
                String raceName = raceJson.getString("raceName");
                availableRounds.add(round);
                raceNames.add(raceName);

            }

            return availableRounds;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<RaceResult> getResultsByYearAndRound(String year, String round) {
        String url = "https://ergast.com/api/f1/" + year + "/" + round + "/results.json";
    
        RestTemplate template = new RestTemplate();
    
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "formula1");
        HttpEntity<String> entity = new HttpEntity<>(headers);
    
        try {
            ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, entity, String.class);
            String json = response.getBody();
    
            logger.info(url);
            logger.info(json);
    
            JsonReader reader = Json.createReader(new StringReader(json));
            JsonObject mrData = reader.readObject().getJsonObject("MRData");
            JsonObject raceTable = mrData.getJsonObject("RaceTable");
            JsonArray races = raceTable.getJsonArray("Races");
    
            List<RaceResult> raceResults = new ArrayList<>();
    
            for (JsonValue raceValue : races) {
                JsonObject raceJson = raceValue.asJsonObject();
    
                String season = raceJson.getString("season");
                String raceName = raceJson.getString("raceName");
    
                JsonArray results = raceJson.getJsonArray("Results");
    
                List<ResultDetails> resultsStringList = new ArrayList<>();
    
                for (JsonValue resultValue : results) {
                    JsonObject resultJson = resultValue.asJsonObject();
    
                    String positionText = resultJson.getString("positionText");
    
                    JsonObject driverJson = resultJson.getJsonObject("Driver");
                    String driverName = (driverJson != null)
                            ? driverJson.getString("givenName") + " " + driverJson.getString("familyName")
                            : "Unknown Driver";
    
                    JsonObject constructorJson = resultJson.getJsonObject("Constructor");
                    String constructor = (constructorJson != null)
                            ? constructorJson.getString("name")
                            : "Unknown Constructor";
    
                    JsonObject timeJson = resultJson.getJsonObject("Time");
                    String time = (timeJson != null)
                            ? timeJson.getString("time")
                            : "No Time Available";
    
                    String status = resultJson.getString("status");
    
                    resultsStringList.add(new ResultDetails(positionText, driverName, constructor, time, status));
                }
    
                raceResults.add(new RaceResult(season, raceName, resultsStringList));
            }
    
            return raceResults;
    
        } catch (HttpClientErrorException.BadRequest e) {
            logger.log(Level.SEVERE, "Bad Request Error: " + e.getResponseBodyAsString(), e);
            return List.of();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An unexpected error occurred", e);
            return List.of();
        }
    }

    public Map<String, String> getRoundsAndRaceNames(String year) {
        String url = "https://ergast.com/api/f1/" + year + ".json";

        try {
            String json = restTemplate.getForObject(url, String.class);
            JsonReader reader = Json.createReader(new StringReader(json));

            JsonObject mrData = reader.readObject().getJsonObject("MRData");
            JsonObject raceTable = mrData.getJsonObject("RaceTable");
            JsonArray races = raceTable.getJsonArray("Races");

            Map<String, String> roundsAndRaceNames = new HashMap<>();

            for (JsonValue raceValue : races) {
                JsonObject raceJson = raceValue.asJsonObject();
                String roundNumber = raceJson.getString("round");
                String raceName = raceJson.getString("raceName");
                roundsAndRaceNames.put(roundNumber, raceName);
            }

            return roundsAndRaceNames;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

}
