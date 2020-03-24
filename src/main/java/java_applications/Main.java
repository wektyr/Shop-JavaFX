package java_applications;

import entity.Uzytkownik;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Objects;

/**
 * Klasa rozpoczynając działanie aplikacji.
 *
 * @author Wiktor
 * @author Jaskowski
 * @version 1.0
 */

public class Main extends Application {

    private static Stage window;
    private static EntityManagerFactory emf;
    private static EntityManager entityManager;
    private static Uzytkownik loggedUser;

    @Override
    public void start(Stage primaryStage) throws Exception{
        emf = Persistence.createEntityManagerFactory("persistence-unit");
        entityManager = emf.createEntityManager();
        window = primaryStage;
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/logujWindow.fxml"))));
        window.setTitle("Sklep");
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    public static Stage getWindow() { return window; }
    public static EntityManagerFactory getEntityManagerFactory() { return emf; }
    public static EntityManager getEntityManager() {
        return entityManager;
    }
    public static Uzytkownik getLoggedUser() {
        return loggedUser;
    }
    public static void setLoggedUser(Uzytkownik loggedUser) { Main.loggedUser = loggedUser; }
}
