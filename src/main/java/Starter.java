
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Starter extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/FrontPage.fxml"));
        primaryStage.setTitle("Pharmacy Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
