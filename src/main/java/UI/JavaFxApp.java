//package UI;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.image.Image;
//import javafx.stage.Stage;
//import javafx.stage.WindowEvent;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ConfigurableApplicationContext;
//
//import java.io.IOException;
//import java.util.Objects;
//
//@SpringBootApplication
//public class JavaFxApp extends Application {
//
//    private ConfigurableApplicationContext context;
//
//    @Override
//    public void init() {
//        context = SpringApplication.run(JavaFxApp.class);
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/UI/FXML.fxml")));
//        Parent root = fxmlLoader.load();
//
//        Scene scene = new Scene(root);
//        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/UI/icon.png")));
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Math expressions calculator");
//        primaryStage.setOnCloseRequest((WindowEvent event) -> {
//            JavaFxController controller = fxmlLoader.getController();
//            try {
//                controller.cleanTemps();
//            } catch (IOException e) {
//
//            }
//        });
//        primaryStage.show();
//    }
//
//    @Override
//    public void stop() {
//        context.stop();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
