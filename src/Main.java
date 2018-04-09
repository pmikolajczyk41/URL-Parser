import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Database.DBconnector.connect();     //establish connection with database

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("View/URLsurfer.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("URLsurfer");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {   //default method does nothing
        Database.DBconnector.closeConnection();     //close connection with database
    }
}
