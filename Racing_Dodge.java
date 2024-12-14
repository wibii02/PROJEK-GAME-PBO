/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package racingdodge;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author acer
 */
public class RacingDodge extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Memuat file FXML
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        // Membuat scene dan mengatur ukuran
        Scene scene = new Scene(root, 281, 537);
        
        // Mengatur judul dan scene utama
        primaryStage.setTitle("Racing Dodge Game");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch(args); // Memulai aplikasi
    }
}
