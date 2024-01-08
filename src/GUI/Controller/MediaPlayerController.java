package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.awt.*;
import java.io.File;
import java.time.Duration;


public class MediaPlayerController {

    @FXML
    private Button btnPlay;

    @FXML
    private Label lblDuration;

    @FXML
    private MediaView mediaView;
    @FXML
    private Slider slider;

    @FXML
    private Media media;
    private MediaPlayer mediaPlayer;

    private boolean isPlayed = false;

    @FXML
    void selectMedia(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Media");
        File selectedFile = fileChooser.showOpenDialog(null);

        if(selectedFile != null){
            String url = selectedFile.toURI().toString();

            media = new Media(url);
            mediaPlayer = new MediaPlayer(media);

            mediaView.setMediaPlayer(mediaPlayer);

            mediaPlayer.currentTimeProperty().addListener(((observable, oldValue, newValue) -> {
                slider.setValue(newValue.toSeconds());
                lblDuration.setText("Duration: " + (int) slider.getValue() + " / " + (int)media.getDuration().toSeconds());
            }));

            mediaPlayer.setOnReady(() ->{
                javafx.util.Duration totalDuration = media.getDuration();
                slider.setMax(totalDuration.toSeconds());
                lblDuration.setText("Duration: 00 / " + (int)media.getDuration().toSeconds());
            });

            Scene scene = new mediaView.getScene();
            mediaView.fitWidthProperty().bind(scene.widthProperty());
            mediaView.fitWidthProperty().bind(scene.heightProperty());

            //mediaPlayer.setAutoPlay(true);

        }
    }
    @FXML
    void btnPlay(MouseEvent mouseEvent) {

        if(!isPlayed){
            btnPlay.setText("Pause");
            mediaPlayer.play();
            isPlayed = true;
        }else{
            btnPlay.setText("Play");
            mediaPlayer.pause();
            isPlayed = false;
        }
    }
    @FXML
    void btnStop(MouseEvent mouseEvent) {
        btnPlay.setText("Play");
        mediaPlayer.stop();
        isPlayed = false;
    }
    @FXML
    private void sliderPressed(MouseEvent event){
        mediaPlayer.seek(Duration.seconds(slider.getValue()));
    }
}
