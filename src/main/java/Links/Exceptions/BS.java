package Links.Exceptions;

import Browser.Browser;
import Browser.Exceptions.MultipleElementsFoundException;
import Browser.Exceptions.NoElementFoundException;
import GUI.Selection;
import Links.LinkHost;
import Links.Show;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by florian on 09.01.17.
 */
public class BS extends LinkHost{
    final static String baseUrl ="https://bs.to";
    static Browser browser;
    static Logger logger = Logger.getLogger(BS.class.getName());
    private static BS singelton;

    private BS(){}
    public static LinkedList<Show> lookUpShows(String name) throws IOException, MultipleElementsFoundException, NoElementFoundException {

        getBrowser().disableScript();
        getBrowser().pointToPage(baseUrl+"/andere-serien");
        LinkedList<HtmlElement> allShows = browser.getMultipleELementsByXpath("//li/a");
        LinkedList<Show> results = new LinkedList<>();
        for(int i = 0;i<allShows.size();i++){
            HtmlElement currentElement = allShows.get(i);
            String currentName = currentElement.getTextContent();
            if(StringUtils.contains(currentName,name)){
                String link = baseUrl+"/"+(String) currentElement.getAttribute("href");
                results.add(new Show(currentName,link,getSingelton()));
            }
        }
        return results;
    }


    public static LinkedList<String> lookUpEpisode(Show show, int season, int episode) throws ShowNotFoundException, IOException, EpisodeNotFoundException {
        return null;
    }

    private static Browser getBrowser(){
        if(browser == null){
            browser = browser = new Browser();
        }
        return browser;
    }

    private static void getNumberofSeasons(Show show) throws IOException, NoElementFoundException {
        getBrowser().pointToPage(show.getUrl());
        LinkedList<HtmlElement> seasons = getBrowser().getMultipleELementsByXpath("//*[@id=\"sp_left\"]/ul/li");
        show.setSeasons(seasons.size());
    }

    private static int getNumberOfEpisodes(Show show,int season){
    //TODO return
        return 0;
    }

    public static void main(String[] args)throws Exception{
       System.out.println( Selection.select(lookUpShows("Top")));

    }

    public static LinkHost getSingelton(){
        if (singelton == null){
            singelton = new BS();
        }
        return singelton;
    }
}
