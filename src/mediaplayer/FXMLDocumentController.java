/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaplayer;

import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 *
 * @author Modupe Okenla
 */
public class FXMLDocumentController implements Initializable {
    private String filepath;
    private MediaPlayer mediaplayer;
    @FXML
    private Label label;
    
    @FXML
    private MediaView mediaView;
    
    @FXML
    private Slider slider;
    
    @FXML
    private Slider seekslider;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select video file(*mp4,*mkv)", "*.mp4","*.mkv");
            fileChooser.getExtensionFilters().add(filter);
            File file = fileChooser.showOpenDialog(null);
            filepath = file.toURI().toString();
            
            if(filepath != null) {
                Media media = new Media(filepath);
                mediaplayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaplayer);
                    DoubleProperty width = mediaView.fitWidthProperty();
                    DoubleProperty height = mediaView.fitHeightProperty();
                    
                    width.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
                    height.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));
                    
                    slider.setValue(mediaplayer.getVolume() * 100);
                    slider.valueProperty().addListener(new InvalidationListener() {
                    

                    @Override
                    public void invalidated(javafx.beans.Observable observable) {
                        mediaplayer.setVolume(slider.getValue() / 100);
                    }
                    });
                    
                    mediaplayer.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) -> {
                        seekslider.setValue(newValue.toSeconds());
                });
                    
                    seekslider.maxProperty().bind(Bindings.createDoubleBinding(() -> mediaplayer.getTotalDuration().toSeconds(), mediaplayer.totalDurationProperty()));
                    
                    seekslider.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
                    @Override
                    public void handle(javafx.scene.input.MouseEvent eventi) {
                        mediaplayer.seek(Duration.seconds(seekslider.getValue()));
                    }
                });
                    
                mediaplayer.play();
            }
    }
    
    
    @FXML
    private void playVideo(ActionEvent event) {
        mediaplayer.play();
        mediaplayer.setRate(1);
    }
    
    @FXML
    private void pauseVideo(ActionEvent event) {
        mediaplayer.pause();
    }
    
    @FXML
    private void stopVideo(ActionEvent event) {
        mediaplayer.stop();
    }
    
    @FXML
    private void fastForwardVideo(ActionEvent event) {
        mediaplayer.setRate(2);
    }
    
    @FXML
    private void forwardVideo(ActionEvent event) {
        mediaplayer.setRate(1.5);
    }
    
    @FXML
    private void rewindVideo(ActionEvent event) {
        mediaplayer.setRate(.75);
    }
    
    @FXML
    private void rewindmoreVideo(ActionEvent event) {
        mediaplayer.setRate(.5);
    }
    
    @FXML
    private void exitVideo(ActionEvent event) {
        System.exit(0);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
