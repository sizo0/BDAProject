package gui;

import DatabasesManager.DatabaseInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    final DatabaseInitializer dbM;

    public App() throws IOException {
        dbM = new DatabaseInitializer();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        fxmlLoader.setController(new MainWindowController(dbM));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("BDAProject - Federated Database");
        stage.setOnCloseRequest(event -> {
            try {
                dbM.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
