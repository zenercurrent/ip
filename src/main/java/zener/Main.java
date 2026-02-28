package zener;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import zener.ui.DialogContainer;
import zener.ui.Ui;

/** JavaFX Application Main */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            DialogContainer dialogContainer = (DialogContainer) fxmlLoader.getNamespace().get("dialogContainer");
            Ui ui = new Ui(dialogContainer);
            Zenerbot bot = Zenerbot.getInstance();
            bot.run(ui); // important step


            Scene scene = new Scene(ap);
            stage.setTitle("Zenerbot");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
