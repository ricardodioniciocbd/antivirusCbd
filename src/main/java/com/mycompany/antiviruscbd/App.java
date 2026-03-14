package com.mycompany.antiviruscbd;

import com.mycompany.antiviruscbd.ui.AntivirusUIv2;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Aplicación principal del Antivirus Educativo.
 * Punto de entrada de la aplicación JavaFX.
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Crear la interfaz de usuario mejorada
        AntivirusUIv2 ui = new AntivirusUIv2();
        Scene scene = new Scene(ui.crearUI(stage), 900, 650);
        
        // Configurar el Stage
        stage.setTitle("Antivirus FX 2025");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
