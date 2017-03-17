package GUI;

import Links.Show;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

/**
 * Created by florian on 19.12.16.
 */
public class ShowButton extends VBox{
    Show show;

    public ShowButton(final Show show){
        this.show = show;
        ShowButton ref = this;
        ImageView cover = new ImageView(new Image(show.getCover(), 758, 140, false, false));
        cover.resize(758,140);
        this.getChildren().add(0,cover);
        TextFlow desc = new TextFlow(new Text(show.getDescription()));
        desc.setTextAlignment(TextAlignment.JUSTIFY);
        desc.setMaxWidth(758);
        TitledPane ap = new TitledPane();
        ap.setText(show.getName());
        ap.setExpanded(false);
        ap.setContent(desc);
        this.getChildren().add(1,ap);
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Selection.setSelectedShow(show);
                ((Node)event.getSource()).getScene().getWindow().hide();
            }});

    }

}
