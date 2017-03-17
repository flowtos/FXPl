package Browser;

import Browser.Exceptions.MultipleElementsFoundException;
import Browser.Exceptions.NoElementFoundException;
import Links.Exceptions.BS;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.host.event.Event;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by florian on 08.12.16.
 */
public class Browser {
    WebClient webClient;
    HtmlPage currentPage;
    static Logger logger = Logger.getLogger(Browser.class.getName());

    public Browser(){
        //Standart Konstruktor Chrome
        this.webClient = new WebClient(BrowserVersion.CHROME);
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
    }

    public void pointToPage(String url) throws IOException{
        logger.log(Level.INFO,"start opening page: "+url);
        currentPage = webClient.getPage(url);
        logger.log(Level.INFO,"pointed to Page: "+url);
    }

    public void pressSpecificButton(String xpath) throws MultipleElementsFoundException,NoElementFoundException,IOException{
        //searches button with xpath throws exception if multiple or no element found
        LinkedList<HtmlElement> elements = new LinkedList<HtmlElement>();

        elements.addAll((List<HtmlElement>)(List<?>)currentPage.getByXPath(xpath));

        if(elements.size() == 0){
            throw new NoElementFoundException("XPath returned no Element");
        }
        if(elements.size() >1){
            throw new MultipleElementsFoundException("XPath returned multiple Elements");
        }

        if(!(elements.get(0) instanceof HtmlButton)){
            throw new NoElementFoundException("XPath returned the wrong Element");
        }

        currentPage = ((HtmlButton) elements.get(0)).click();
    }

    public String getSpecificAttribute(String xpath,String attribute)throws MultipleElementsFoundException,NoElementFoundException,IOException {
        //returns attribute as string form element found with xpath throws exception if multiple or no element found
        LinkedList<HtmlElement> elements = new LinkedList<HtmlElement>();
        elements.addAll((List<HtmlElement>)(List<?>)currentPage.getByXPath(xpath));

        if (elements.size() == 0) {
            throw new NoElementFoundException("XPath returned no Element");
        }
        if (elements.size() > 1) {
            throw new MultipleElementsFoundException("XPath returned multiple Elements");
        }
        if(attribute.equals("textContent")){
            return elements.get(0).getTextContent();
        }
        return (elements.get(0)).getAttribute(attribute);
    }

    public void modifySpecificInput(String xpath,String value)throws MultipleElementsFoundException,NoElementFoundException,IOException{
        LinkedList<HtmlElement> elements = new LinkedList<HtmlElement>();
        elements.addAll((List<HtmlElement>)(List<?>)currentPage.getByXPath(xpath));

        if(elements.size() == 0){
            throw new NoElementFoundException("XPath returned no Element");
        }
        if(elements.size() >1){
            throw new MultipleElementsFoundException("XPath returned multiple Elements");
        }

        if(!(elements.get(0) instanceof HtmlInput)){
            throw new NoElementFoundException("XPath returned the wrong Element");
        }

        HtmlInput input = (HtmlInput) elements.get(0);
        input.setValueAttribute(value);
        input.fireEvent(Event.TYPE_CHANGE);
        currentPage = currentPage.getPage();
    }

    public void pressNthButton(String xpath,int n) throws NoElementFoundException, IOException {
        LinkedList<HtmlElement> elements = new LinkedList<HtmlElement>();
        elements.addAll((List<HtmlElement>)(List<?>)currentPage.getByXPath(xpath));

        if(elements.size() < n-1){
            throw new NoElementFoundException("XPath returned no nth Element");
        }
        if(!(elements.get(n-1) instanceof HtmlButton)){
            throw new NoElementFoundException("nth Element no Button");
        }

        HtmlButton button = (HtmlButton) elements.get(n-1);
        currentPage = button.click();
    }

    public LinkedList<HtmlElement> getMultipleELementsByXpath(String xpath) throws NoElementFoundException {
        LinkedList<HtmlElement> result = new LinkedList<HtmlElement>();
        result.addAll((List<HtmlElement>)(List<?>)currentPage.getByXPath(xpath));
        if(result.size()<1){
            throw new NoElementFoundException("xpath returned no elements");
        }
        return(result);
    }

    public static void main(String[] args) throws  Exception{
        Browser browser = new Browser();
        browser.pointToPage("http://www.mensa-kl.de/");
        String Ausgabe1 = browser.getSpecificAttribute("//*[@id=\"tr-37712\"]/p[2]","textContent");
        System.out.print(Ausgabe1);
    }

    public void disableScript(){
        this.webClient.getOptions().setJavaScriptEnabled(false);
    }

    public void enableScript(){
        this.webClient.getOptions().setJavaScriptEnabled(true);
    }

}
