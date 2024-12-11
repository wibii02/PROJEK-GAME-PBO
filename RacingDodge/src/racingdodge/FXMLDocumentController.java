package racingdodge;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class FXMLDocumentController implements Initializable {

    @FXML
    private ImageView playerCar; // Mobil pemain
    @FXML
    private ImageView rintanganMobil; // Mobil rintangan
    @FXML
    private ImageView btnPlay; // Tombol Play
    @FXML
    private Button scoreButton; // Tombol skor

    private int score = 0; // Inisialisasi skor
    private final double MOVE_STEP = 10.0; // Langkah pergerakan mobil pemain
    private final double RINTANGAN_SPEED = 3.0; // Kecepatan rintangan
    private final double SCENE_WIDTH = 281.0; // Lebar antarmuka (prefWidth AnchorPane)
    private final double SCENE_HEIGHT = 537.0; // Tinggi antarmuka (prefHeight AnchorPane)

    private AnimationTimer timer;
    private boolean gameRunning = false; // Menentukan apakah permainan sedang berjalan

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Fokus pada mobil pemain untuk menerima input keyboard
        playerCar.setFocusTraversable(true);

        // Atur klik pada tombol Play untuk memulai permainan
        btnPlay.setOnMouseClicked(event -> startGame());
    }

    @FXML
    private void BuatGerak(KeyEvent event) {
        if (!gameRunning) return; // Abaikan input jika permainan tidak berjalan

        switch (event.getCode()) {
            case D: // Tombol D untuk gerak ke kanan
                if (playerCar.getLayoutX() + playerCar.getFitWidth() + MOVE_STEP <= SCENE_WIDTH) {
                    // Gerakkan mobil ke kanan jika tidak melewati batas
                    playerCar.setLayoutX(playerCar.getLayoutX() + MOVE_STEP);
                }
                break;
            case A: // Tombol A untuk gerak ke kiri
                if (playerCar.getLayoutX() - MOVE_STEP >= 0) {
                    // Gerakkan mobil ke kiri jika tidak melewati batas
                    playerCar.setLayoutX(playerCar.getLayoutX() - MOVE_STEP);
                }
                break;
            case W: // Tombol W untuk gerak ke atas (berhenti pada posisi bawah layar)
                if (playerCar.getLayoutY() - MOVE_STEP >= 0) {
                    playerCar.setLayoutY(playerCar.getLayoutY() - MOVE_STEP);
                }
                break;
            case S: // Tombol S untuk gerak ke bawah
                if (playerCar.getLayoutY() + MOVE_STEP <= SCENE_HEIGHT - playerCar.getFitHeight() - 10) {
                    playerCar.setLayoutY(playerCar.getLayoutY() + MOVE_STEP);
                }
                break;
            default:
                break;
        }
    }


    private void startGame() {
        // Reset posisi awal mobil pemain dan rintangan
        playerCar.setLayoutX((SCENE_WIDTH - playerCar.getFitWidth()) / 2);
        playerCar.setLayoutY(SCENE_HEIGHT - playerCar.getFitHeight() - 10);
        score = 0; // Reset skor ke 0
        scoreButton.setText("SCORE: " + score); // Perbarui teks skor

        resetObstaclePosition();

        // Sembunyikan tombol Play setelah permainan dimulai
        btnPlay.setVisible(false);
        btnPlay.setManaged(false);

        // Tandai permainan sedang berjalan
        gameRunning = true;

        // Mulai animasi rintangan
        startObstacleAnimation();
    }

    private void startObstacleAnimation() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Gerakkan rintangan ke bawah
                rintanganMobil.setLayoutY(rintanganMobil.getLayoutY() + RINTANGAN_SPEED);

                // Jika rintangan keluar dari layar, reset ke atas dengan posisi acak
                if (rintanganMobil.getLayoutY() > SCENE_HEIGHT) {
                    resetObstaclePosition();
                    updateScore(); // Tambahkan skor
                }

                // Cek tabrakan
                checkCollision();
            }
        };
        timer.start();
    }

    private void resetObstaclePosition() {
        // Reset posisi rintangan ke atas layar dengan posisi X acak
        rintanganMobil.setLayoutY(-rintanganMobil.getFitHeight());
        rintanganMobil.setLayoutX(Math.random() * (SCENE_WIDTH - rintanganMobil.getFitWidth()));
    }

    private void checkCollision() {
        // Deteksi tabrakan sederhana berdasarkan bounding box
        if (playerCar.getBoundsInParent().intersects(rintanganMobil.getBoundsInParent())) {
            System.out.println("Collision Detected! Game Over.");
            // Hentikan permainan saat tabrakan terjadi
            timer.stop();

            // Tampilkan tombol Play kembali
            btnPlay.setVisible(true);
            btnPlay.setManaged(true);

            // Tampilkan skor final di tombol
            scoreButton.setText("SCORE: " + score);

            // Reset skor dan set gameRunning menjadi false
            score = 0;
            gameRunning = false;
        }
    }

    private void updateScore() {
        if (gameRunning) {
            score++;
            scoreButton.setText("SCORE: " + score); // Perbarui teks skor
        }
    }
}
