package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utility.PMCException;

import java.io.IOException;

public class WarningController {
    public void understoodBtn(ActionEvent actionEvent) throws PMCException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
            Parent newWindow = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Warning");
            stage.setScene(new Scene(newWindow));

            MainViewController controller = loader.getController();
            controller.setParentController(this);
            controller.setStage(stage);
            
            stage.showAndWait();

            controller.closeStage();
        } catch (IOException e) {
            throw new PMCException("Could not load new Stage", e);
        }
    }

    private void setStage(Stage stage) {
    }

    private void setParentController(WarningController warningController) {
    }
}
