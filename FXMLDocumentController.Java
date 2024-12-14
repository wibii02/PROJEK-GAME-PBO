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
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        gambar.setLayoutX(x);
    }
    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        gambar.setLayoutY(y);
    }

    public ImageView getGambar() {
        return gambar;
    }
}
class PlayerCar extends Kendaraan {
    public PlayerCar(ImageView gambar) {
        super(gambar);
    }
}

class RintanganMobil extends Kendaraan {
    public RintanganMobil(ImageView gambar) {
        super(gambar);
    }
}

public class FXMLDocumentController implements Initializable {

    @FXML
    private ImageView playerCar;
    @FXML
    private ImageView rintanganMobil1;
    @FXML
    private ImageView rintanganMobil2;
    @FXML
    private ImageView btnPlay;
    @FXML
    private Button scoreButton;
    @FXML
    private Label ScoreAkhir; // Label untuk skor akhir
    @FXML
    private Label gameOver;   // Label untuk "GAME OVER"
    @FXML
    private Label restart;    // Label untuk "APAKAH ANDA INGIN BERMAIN LAGI"
    @FXML
    private Button buttonYes; // Tombol "YES"
    @FXML
    private Button buttonNo;  // Tombol "NO"
