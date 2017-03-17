package GUI;

import Links.Show;
import com.sun.scenario.effect.impl.state.LinearConvolveKernel;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.awt.*;
import java.util.LinkedList;

/**
 * Created by florian on 19.12.16.
 */
public class Selection extends Application{

    private static Show selection;
    private static LinkedList<Show> shows;

    public static Show select(LinkedList<Show> sh){
        shows = sh;
        launch();
        return selection;
    }

    public static void main(String[] args){
         LinkedList<Show> shows = new LinkedList<>();




        Show sel = select(shows);
        System.out.print(sel);
    }

    public static void setSelectedShow(Show show){
        selection = show;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("Select a Show");
        VBox box = new VBox(shows.size());
        ScrollPane pane = new ScrollPane();

        for(int i = 0;i<shows.size();i++){
            box.getChildren().add(new ShowButton(shows.get(i)));
        }
        box.setAlignment(Pos.CENTER);
        pane.setContent(box);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        pane.setFitToHeight(false);
        pane.setMaxHeight(600);
        stage.setScene(new Scene(pane));
        stage.showAndWait();
    }
}
