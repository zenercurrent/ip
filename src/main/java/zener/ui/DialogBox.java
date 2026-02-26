package zener.ui;

import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Dialog box for Zenerbot JavaFX UI. Can be differentiated by user/bot dialog boxes.
 * Display pictures are preset in /resources/images
 */
public class DialogBox extends HBox {
    private static final Image userImage = new Image(
            Objects.requireNonNull(DialogBox.class.getResourceAsStream("/images/fella.png")));
    private static final Image botImage = new Image(
            Objects.requireNonNull(DialogBox.class.getResourceAsStream("/images/angry_fella.png")));

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
     * @return the dialog box
     */
    public static DialogBox createUserDialog(String s) {
        return new DialogBox(s, userImage);
    }

    /**
     * Create a bot dialog box. (facing left)
     *
     * @param s the string text
     * @return the dialog box
     */
    public static DialogBox createBotDialog(String s) {
        var dbox = new DialogBox(s, botImage);
        dbox.flip();

        return dbox;
    }
}
