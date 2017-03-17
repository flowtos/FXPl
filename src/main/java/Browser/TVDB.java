package Browser;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by florian on 21.12.16.
 */
public class TVDB {
    static final String language = "de";
    public static int getID(String name) throws IOException, UnirestException {
        name = name.replace(" ","_");
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type","application/json");
        JSONObject token = Unirest.post("https://api.thetvdb.com/login").headers(header).body("{\"apikey\":\"C59F77E0BB7EFB52\"}").asJson().getBody().getObject();
        header.put("Authorization","Bearer "+token.get("token"));
        HttpResponse<JsonNode> searchResponse = Unirest.get("https://api.thetvdb.com/search/series?name="+name).headers(header).asJson();
        JSONArray results = searchResponse.getBody().getObject().getJSONArray("data");
        return results.getJSONObject(0).getInt("id");
    }

    public static String getDescription(String name)throws IOException, UnirestException{
        name = name.replace(" ","_");
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type","application/json");
        header.put("accept-language",language);
        JSONObject token = Unirest.post("https://api.thetvdb.com/login").headers(header).body("{\"apikey\":\"C59F77E0BB7EFB52\"}").asJson().getBody().getObject();
        header.put("Authorization","Bearer "+token.get("token"));
        HttpResponse<JsonNode> searchResponse = Unirest.get("https://api.thetvdb.com/search/series?name="+name).headers(header).asJson();
        JSONArray results = searchResponse.getBody().getObject().getJSONArray("data");
        return results.getJSONObject(0).getString("overview");
    }

    public static String getBanner(String name)throws IOException, UnirestException{
        name = name.replace(" ","_");
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type","application/json");
        JSONObject token = Unirest.post("https://api.thetvdb.com/login").headers(header).body("{\"apikey\":\"C59F77E0BB7EFB52\"}").asJson().getBody().getObject();
        header.put("Authorization","Bearer "+token.get("token"));
        HttpResponse<JsonNode> searchResponse = Unirest.get("https://api.thetvdb.com/search/series?name="+name).headers(header).asJson();
        JSONArray results = searchResponse.getBody().getObject().getJSONArray("data");
        return "https://thetvdb.com/banners/"+results.getJSONObject(0).getString("banner");
    }


    public static void main(String[] args)throws Exception{
        System.out.print(getDescription("supernatural"));
    }
}
