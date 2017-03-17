package Links;

import Browser.Exceptions.MultipleElementsFoundException;
import Browser.Exceptions.NoElementFoundException;
import Links.Exceptions.EpisodeNotFoundException;
import Links.Exceptions.ShowNotFoundException;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by florian on 27.12.16.
 */
public abstract class LinkHost {
    public static LinkedList<Show> lookUpShows(String name) throws IOException, MultipleElementsFoundException, NoElementFoundException{return null;}//Returns a list of all shows matching the name to let the user select the right one
    public static LinkedList<String> lookUpEpisode(Show show, int season, int episode) throws ShowNotFoundException,IOException,EpisodeNotFoundException{return null;}//returns all found hosters as a list of strings
    public static int getNumberofSeasons(){return 0;};
    public static int getNumberofEpisodes(int season){return 0;};
    public static LinkHost getSingelton(){return null;};
}