package Links;

import Browser.TVDB;
import Links.Exceptions.EpisodeNotFoundException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;


import java.io.IOException;

import java.net.InterfaceAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by florian on 12.12.16.
 */
public class Show{
    String cover;
    String name;
    String description;
    String link;
    LinkHost linkHost;
    LinkedList<Integer> seasons = new LinkedList<Integer>();

    public Show(String name,String description,String cover){

        this.cover = cover;
        this.name = name;
        this.description = description;

    }



    public Show(String name,String link,LinkHost linkHost){
        this.name = name;
        this.link = link;

        try {
            this.cover = TVDB.getBanner(name);
            this.description = TVDB.getDescription(name);
        } catch (Exception e) {
            this.description = "no description available, no db entry returned";
            try {
                this.cover =this.getClass().getClassLoader().getResource("blurry.jpg").toURI().toURL().toString();
            } catch (MalformedURLException e1) {
            } catch (URISyntaxException e1) {
            }

        }
        this.linkHost = linkHost;
    }

    public String getCover(){
        return this.cover;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public String toString(){
        return(this.name);
    }


    public String getUrl() {
        return link;
    }

    public void setSeasons(int seasons){
        this.seasons = new LinkedList<Integer>();
        for(int i = 0;i<seasons;i++){
            this.seasons.add(-1);
        }
    }

    public int getSeasons(){
        if(this.seasons == null){
            this.setSeasons(linkHost.getNumberofSeasons());
        }
        return this.seasons.size();
    }

    public int getNumberofEpisodes(int season)throws EpisodeNotFoundException{
        if(this.getSeasons()<season-1){
            throw new EpisodeNotFoundException("Season not available");
        }
        if(this.seasons.get(season)==-1){
          this.seasons.set(season,linkHost.getNumberofEpisodes(season));
        }
        return this.seasons.get(season);
    }
}
