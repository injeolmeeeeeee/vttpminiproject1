package vttp.iss.nus.miniproject1vttp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import vttp.iss.nus.miniproject1vttp.model.RaceResult;
import vttp.iss.nus.miniproject1vttp.model.ResultDetails;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class LastResultsService {

    public List<RaceResult> getLastResults() {
        String url = "https://ergast.com/api/f1/current/last/results.json";
        RestTemplate template = new RestTemplate();

        try {
            String json = template.getForObject(url, String.class);

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
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }


}