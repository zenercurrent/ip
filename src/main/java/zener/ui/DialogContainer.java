package zener.ui;

import javafx.scene.layout.VBox;

/**
 * The dialog container that contains the conversation between the user and bot.
 * Automatically adds stuff to the container by method call.
 */
public class DialogContainer extends VBox {

    /**
     * Adds a dialog to the dialog container
     *
     * @param dbox the dialog box object
     */
    public void addDialog(DialogBox dbox) {
        this.getChildren().add(dbox);
    }
}
