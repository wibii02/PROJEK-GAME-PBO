package racingdodge;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

abstract class Kendaraan {
    private double x;
    private double y;
    private ImageView gambar;

    public Kendaraan(ImageView gambar) {
        this.gambar = gambar;
        this.x = gambar.getLayoutX();
        this.y = gambar.getLayoutY();
    }
