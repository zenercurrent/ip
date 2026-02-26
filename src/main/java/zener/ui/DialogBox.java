package zener.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * The type Dialog box.
 */
public class DialogBox extends HBox {
    private Label text;
    private ImageView displayPicture;

    private DialogBox(String s, Image i) {
        text = new Label(s);
        displayPicture = new ImageView(i);

        // styling
        text.setWrapText(true);
        displayPicture.setFitWidth(100.0);
        displayPicture.setFitHeight(100.0);

        this.setAlignment(Pos.TOP_RIGHT);
        this.getChildren().addAll(text, displayPicture);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    /**
     * Create a user dialog box. (facing right)
     *
     * @param s the string text
     * @param i the display image
     * @return the dialog box
     */
    public static DialogBox createUserDialog(String s, Image i) {
        return new DialogBox(s, i);
    }

    /**
     * Create a bot dialog box. (facing left)
     *
     * @param s the string text
     * @param i the display image
     * @return the dialog box
     */
    public static DialogBox createBotDialog(String s, Image i) {
        var dbox = new DialogBox(s, i);
        dbox.flip();

        return dbox;
    }
}
