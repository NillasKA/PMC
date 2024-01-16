package GUI.Controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class FullscreenMedia extends Application {

    private double normalWidth;
    private double normalHeight;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        String mediaUrl = "example.mp4"; // replace with your own media file
        Media media = new Media(mediaUrl);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        Scene scene = new Scene(mediaView.getParent(), 640, 480);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(false);
        primaryStage.show();

        mediaPlayer.play();

        // Bind the dimensions of the MediaView to its parent (scene) dimensions
        mediaView.fitWidthProperty().bind(mediaView.getScene().widthProperty());
        mediaView.fitHeightProperty().bind(mediaView.getScene().heightProperty());

        // Store the normal dimensions of the MediaView
        normalWidth = mediaView.getFitWidth();
        normalHeight = mediaView.getFitHeight();

        primaryStage.setOnCloseRequest(event -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
        });

        // Handle the fullscreen changed event
        primaryStage.fullScreenProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                mediaView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth());
                mediaView.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight());
            } else {
                mediaView.setFitWidth(normalWidth);
                mediaView.setFitHeight(normalHeight);
            }
        });
    }
}
