package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import utility.PMCException;

import java.io.IOException;

public class WarningController {
    public void understoodBtn(ActionEvent actionEvent) throws PMCException {
        try {
            // Load the MainView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
            Parent root = loader.load();

            // Get the reference to the button's window
            Window currentWindow = ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

            // Cast the window to Stage
            Stage currentStage = (Stage) currentWindow;

            // Close the current stage (warning window)
            currentStage.close();

            // Create a new instance of BorderPane for the MainView scene
            BorderPane mainRoot = new BorderPane();
            mainRoot.setCenter(root);

            // Set the new scene for the current stage
            currentStage.setScene(new Scene(mainRoot));

            // Show the current stage (MainView)
            currentStage.show();
        } catch (IOException e) {
            throw new PMCException("Could not load MainView", e);
        }
    }
}




