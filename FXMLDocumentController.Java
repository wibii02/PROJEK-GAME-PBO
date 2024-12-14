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

    private int score = 0;
    private final double MOVE_STEP = 10.0;
    private final double RINTANGAN_SPEED = 3.0;
    private final double SCENE_WIDTH = 281.0;
    private final double SCENE_HEIGHT = 537.0;

    private AnimationTimer gameTimer;
    private boolean gameRunning = false;

    private PlayerCar playerCarInstance;
    private RintanganMobil[] rintanganList;
    private Random random;

    private static final String SCORE_PREFIX = "SCORE: ";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerCarInstance = new PlayerCar(playerCar);
        rintanganList = new RintanganMobil[]{
            new RintanganMobil(rintanganMobil1),
            new RintanganMobil(rintanganMobil2)
        };

        playerCar.setFocusTraversable(true);
        btnPlay.setOnMouseClicked(event -> startGame());
        random = new Random();

        // Atur posisi awal rintangan
        for (RintanganMobil rintangan : rintanganList) {
            resetObstaclePosition(rintangan);
        }

        // Sembunyikan elemen akhir permainan
        ScoreAkhir.setVisible(false);
        gameOver.setVisible(false);
        restart.setVisible(false);
        buttonYes.setVisible(false);
        buttonNo.setVisible(false);

        buttonYes.setOnAction(event -> onYesClicked());
        buttonNo.setOnAction(event -> onNoClicked());
    }

    @FXML
    private void BuatGerak(KeyEvent event) {
        if (!gameRunning) return;

        switch (event.getCode()) {
            case D:
                playerCarInstance.setX(Math.min(playerCarInstance.getX() + MOVE_STEP, SCENE_WIDTH - playerCarInstance.getGambar().getFitWidth()));
                break;
            case A:
                playerCarInstance.setX(Math.max(playerCarInstance.getX() - MOVE_STEP, 0));
                break;
            case W:
                playerCarInstance.setY(Math.max(playerCarInstance.getY() - MOVE_STEP, 0));
                break;
            case S:
                playerCarInstance.setY(Math.min(playerCarInstance.getY() + MOVE_STEP, SCENE_HEIGHT - playerCarInstance.getGambar().getFitHeight()));
                break;
        }
    }

    private void startGame() {
        playerCarInstance.setX((SCENE_WIDTH - playerCarInstance.getGambar().getFitWidth()) / 2);
        playerCarInstance.setY(SCENE_HEIGHT - playerCarInstance.getGambar().getFitHeight() - 10);
        score = 0;
        updateScoreDisplay();

        for (RintanganMobil rintangan : rintanganList) {
            resetObstaclePosition(rintangan);
        }

        btnPlay.setVisible(false);
        btnPlay.setManaged(false);

        // Sembunyikan elemen akhir permainan
        ScoreAkhir.setVisible(false);
        gameOver.setVisible(false);
        restart.setVisible(false);
        buttonYes.setVisible(false);
        buttonNo.setVisible(false);

        gameRunning = true;
        startObstacleAnimation();
    }
    private void startObstacleAnimation() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (RintanganMobil rintangan : rintanganList) {
                    try {
                        moveObstacle(rintangan);
                    } catch (Exception e) {
                        System.err.println("Error saat memindahkan rintangan: " + e.getMessage());
                    }
                }
                checkCollision();
            }
        };
        gameTimer.start();
    }
