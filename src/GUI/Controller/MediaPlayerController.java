package GUI.Controller;

import BE.Movie;
import GUI.Model.MovieModel;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.util.Duration;
import utility.PMCException;
import java.time.LocalDate;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class MediaPlayerController implements Initializable {
    public BorderPane borderPane;
    @FXML Slider volumeSlider;
    private MovieModel movieModel;
    @FXML
    private Button btnPlay;

    @FXML
    private Label lblDuration;

    @FXML
    private MediaView mediaView;
    @FXML
    private Slider slider;
    BrowseViewController parentController;
    private Stage stage;
    @FXML
    private Media media;
    private MediaPlayer mediaPlayer;

    private boolean isPlayed = false;
    public MediaPlayerController() throws PMCException {
        try {
            movieModel = MovieModel.getInstance();
        } catch (Exception e) {
            throw new PMCException("Could not fetch model instance", e);
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

    public void setParentController(BrowseViewController parentController) {
        this.parentController = parentController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Movie movie = movieModel.getCurrentMovie();
        LocalDate currentDate = LocalDate.now();
        Movie updatedMovie = new Movie(movie.getId(), movie.getName(), movie.getRating(), movie.getFilelink(), String.valueOf(currentDate.getYear()));
        try {
            movieModel.update(updatedMovie);
        } catch (PMCException e) {
            throw new RuntimeException(e);
        }

        File selectedFile = new File(movie.getFilelink());
        System.out.println(updatedMovie.getYear());

        if (selectedFile != null) {
            String url = selectedFile.toURI().toString();

            media = new Media(url);
            mediaPlayer = new MediaPlayer(media);

            mediaView.setMediaPlayer(mediaPlayer);

            mediaPlayer.currentTimeProperty().addListener(((observable, oldValue, newValue) -> {
                slider.setValue(newValue.toSeconds());
                lblDuration.setText("Duration: " + (int) slider.getValue() + " / " + (int) media.getDuration().toSeconds());
            }));

            mediaPlayer.setOnReady(() -> {
                javafx.util.Duration totalDuration = media.getDuration();
                slider.setMax(totalDuration.toSeconds());
                lblDuration.setText("Duration: 00 / " + (int) media.getDuration().toSeconds());
            });

            volumeSlider.setValue(mediaPlayer.getVolume() * 100); // Set slider value based on initial volume
            volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                mediaPlayer.setVolume(newValue.doubleValue() / 100); // Set volume based on slider value
            });

            Platform.runLater(() -> {
                Stage stage = (Stage) mediaView.getScene().getWindow();

                // Add ChangeListeners to update the MediaView dimensions when the stage is resized
                stage.widthProperty().addListener((observable, oldValue, newValue) -> {
                    mediaView.setFitWidth(newValue.doubleValue());
                });
                stage.heightProperty().addListener((observable, oldValue, newValue) -> {
                    mediaView.setFitHeight(newValue.doubleValue());
                });

                stage.setOnCloseRequest(event -> {
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                    }
                });

                stage.setFullScreen(true);
            });
        }
    }

    public void toggleFullscreen(ActionEvent actionEvent) {
        Stage stage = (Stage) mediaView.getScene().getWindow();
        stage.setFullScreen(!stage.isFullScreen());
    }
}


