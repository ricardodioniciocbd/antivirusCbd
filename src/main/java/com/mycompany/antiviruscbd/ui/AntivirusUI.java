package com.mycompany.antiviruscbd.ui;

import com.mycompany.antiviruscbd.task.ScannerTask;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

/**
 * Interfaz de usuario del antivirus educativo.
 * Proporciona una interfaz gráfica limpia y profesional para escanear archivos.
 */
public class AntivirusUI {
    
    private Label estado;
    private ProgressBar barra;
    private Button btnScan;
    
    /**
     * Crea y configura la interfaz de usuario.
     * @param stage El Stage principal de la aplicación
     * @return VBox conteniendo todos los componentes de la UI
     */
    public VBox crearUI(Stage stage) {
        // Título de la aplicación
        Label titulo = new Label("🛡 Antivirus Educativo 2025");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Estado del sistema
        estado = new Label("Estado: En espera");
        estado.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
        estado.setWrapText(true);
        
        // Barra de progreso
        barra = new ProgressBar(0);
        barra.setPrefWidth(350);
        barra.setStyle("-fx-accent: #3498db;");
        
        // Botón de escaneo
        btnScan = new Button("📁 Escanear Archivo");
        btnScan.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-background-color: #3498db; " +
            "-fx-text-fill: white; " +
            "-fx-padding: 10 20 10 20; " +
            "-fx-background-radius: 5; " +
            "-fx-cursor: hand;"
        );
        
        // Efecto hover para el botón
        btnScan.setOnMouseEntered(e -> 
            btnScan.setStyle(
                "-fx-font-size: 14px; " +
                "-fx-background-color: #2980b9; " +
                "-fx-text-fill: white; " +
                "-fx-padding: 10 20 10 20; " +
                "-fx-background-radius: 5; " +
                "-fx-cursor: hand;"
            )
        );
        
        btnScan.setOnMouseExited(e -> 
            btnScan.setStyle(
                "-fx-font-size: 14px; " +
                "-fx-background-color: #3498db; " +
                "-fx-text-fill: white; " +
                "-fx-padding: 10 20 10 20; " +
                "-fx-background-radius: 5; " +
                "-fx-cursor: hand;"
            )
        );
        
        // Configurar acción del botón
        btnScan.setOnAction(e -> handleScanAction(stage));
        
        // Contenedor para el botón (centrado)
        HBox buttonBox = new HBox(btnScan);
        buttonBox.setStyle("-fx-alignment: center;");
        
        // Layout principal
        VBox layout = new VBox(15, titulo, buttonBox, barra, estado);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #ecf0f1; -fx-background-radius: 10;");
        
        return layout;
    }
    
    /**
     * Maneja la acción de escaneo cuando se presiona el botón.
     * @param stage El Stage principal para mostrar el FileChooser
     */
    private void handleScanAction(Stage stage) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Seleccionar archivo para escanear");
        fc.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Todos los archivos", "*.*"),
            new FileChooser.ExtensionFilter("Archivos de texto", "*.txt"),
            new FileChooser.ExtensionFilter("Documentos", "*.pdf", "*.doc", "*.docx"),
            new FileChooser.ExtensionFilter("Ejecutables", "*.exe", "*.bat")
        );
        
        File archivo = fc.showOpenDialog(stage);
        
        if (archivo != null) {
            // Crear y configurar la tarea de escaneo
            ScannerTask tarea = new ScannerTask(archivo);
            
            // Vincular propiedades de la tarea a la UI
            bindTaskToUI(tarea);
            
            // Ejecutar la tarea en un hilo daemon
            Thread hilo = new Thread(tarea);
            hilo.setDaemon(true);
            hilo.start();
        }
    }
    
    /**
     * Vincula las propiedades de la tarea a los componentes de la UI.
     * @param tarea La tarea de escaneo a vincular
     */
    private void bindTaskToUI(ScannerTask tarea) {
        // Deshabilitar botón durante el escaneo
        btnScan.setDisable(true);
        
        // Vincular progreso y mensaje
        barra.progressProperty().bind(tarea.progressProperty());
        estado.textProperty().bind(tarea.messageProperty());
        
        // Manejar finalización exitosa
        tarea.setOnSucceeded(ev -> {
            // Desvincular propiedades antes de modificarlas manualmente
            estado.textProperty().unbind();
            barra.progressProperty().unbind();
            
            String resultado = tarea.getValue();
            
            // Actualizar estado con estilo según el resultado
            if (resultado.equals("Archivo limpio")) {
                estado.setText("✓ " + resultado);
                estado.setStyle("-fx-font-size: 14px; -fx-text-fill: #27ae60; -fx-font-weight: bold;");
            } else if (resultado.startsWith("Error")) {
                estado.setText("✗ " + resultado);
                estado.setStyle("-fx-font-size: 14px; -fx-text-fill: #e74c3c; -fx-font-weight: bold;");
            } else {
                estado.setText("⚠ Malware detectado: " + resultado);
                estado.setStyle("-fx-font-size: 14px; -fx-text-fill: #e67e22; -fx-font-weight: bold;");
            }
            
            barra.setProgress(1);
            btnScan.setDisable(false);
        });
        
        // Manejar fallos
        tarea.setOnFailed(ev -> {
            estado.textProperty().unbind();
            barra.progressProperty().unbind();
            estado.setText("✗ Error durante el escaneo");
            estado.setStyle("-fx-font-size: 14px; -fx-text-fill: #e74c3c; -fx-font-weight: bold;");
            barra.setProgress(0);
            btnScan.setDisable(false);
        });
        
        // Manejar cancelación
        tarea.setOnCancelled(ev -> {
            estado.textProperty().unbind();
            barra.progressProperty().unbind();
            estado.setText("Escaneo cancelado");
            estado.setStyle("-fx-font-size: 14px; -fx-text-fill: #95a5a6;");
            barra.setProgress(0);
            btnScan.setDisable(false);
        });
    }
    
    /**
     * Reinicia la UI a su estado inicial.
     */
    private void resetUI() {
        estado.setText("Estado: En espera");
        estado.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
        barra.setProgress(0);
        btnScan.setDisable(false);
    }
}
