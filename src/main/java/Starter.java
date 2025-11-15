import Util.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

public class Starter extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        try (SessionFactory factory = HibernateUtil.getSessionFactory()) {
        }
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/AdminDashBoard.fxml"))));
        stage.show();

    }
}
