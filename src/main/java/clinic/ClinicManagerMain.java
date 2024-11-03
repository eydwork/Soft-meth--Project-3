package clinic;

import clinic.controller.ClinicManagerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClinicManagerMain extends Application {

    private static final int HEIGHT = 403;
    private static final int WIDTH = 575;

    @Override
    public void start(Stage homeStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clinic/clinic-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, WIDTH, HEIGHT); // Set preferred width and height
        ClinicManagerController controller = new ClinicManagerController();

        homeStage.setTitle("Clinic Application");
        homeStage.setScene(scene);
        homeStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}