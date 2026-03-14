package com.mycompany.antiviruscbd.ui;

import com.mycompany.antiviruscbd.modelo.Deteccion;
import com.mycompany.antiviruscbd.modelo.Estadisticas;
import com.mycompany.antiviruscbd.historial.HistorialDetecciones;
import com.mycompany.antiviruscbd.task.ScannerTask;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;

/**
 * Interfaz de usuario mejorada del antivirus educativo.
 * Versión 2 con panel de resultados, historial y estadísticas.
 */
public class AntivirusUIv2 {
    
    private Label lblEstadoProteccion;
    private Label lblUltimoEscaneo;
    private Label lblArchivosEscaneados;
    private Label lblAmenazasDetectadas;
    private Label lblArchivosLimpios;
    private Circle escudo;
    private Button btnScan;
    private File archivoActual;
    
    // Panel de resultado actual
    private VBox panelResultadoActual;
    private Label lblArchivoActual;
    private Label lblPesoActual;
    private Label lblEstadoActual;
    private Label lblPeligrosidadActual;
    private Label lblTipoMalwareActual;
    private Button btnEliminarArchivo;
    private Button btnLimpiarInterfaz;
    
    // Tabla de historial
    private TableView<Deteccion> tablaHistorial;
    
    // Estadísticas
    private Estadisticas estadisticas;
    
    public AntivirusUIv2() {
        this.estadisticas = new Estadisticas();
        this.archivoActual = null;
    }

    /**
     * Crea y configura la interfaz de usuario mejorada.
     * @param stage El Stage principal de la aplicación
     * @return BorderPane conteniendo todos los componentes de la UI
     */
    public BorderPane crearUI(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #2d5016, #1a3010);");
        
        // Panel superior (Header)
        root.setTop(crearHeader());
        
        // Panel central (Dashboard)
        root.setCenter(crearDashboard(stage));
        
        // Panel inferior (Historial)
        root.setBottom(crearHistorial());
        
        return root;
    }
    
    /**
     * Crea el panel superior con título y fecha.
     */
    private VBox crearHeader() {
        Label titulo = new Label("🛡 Antivirus Educativo 2025");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        lblUltimoEscaneo = new Label("Último Escaneo: Nunca");
        lblUltimoEscaneo.setStyle("-fx-font-size: 12px; -fx-text-fill: #b0b0b0;");
        
        VBox header = new VBox(5, titulo, lblUltimoEscaneo);
        header.setPadding(new Insets(15));
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3);");
        
        return header;
    }
    
    /**
     * Crea el panel central con escudo, estadísticas y botones.
     */
    private VBox crearDashboard(Stage stage) {
        VBox dashboard = new VBox(20);
        dashboard.setPadding(new Insets(20));
        dashboard.setAlignment(Pos.CENTER);
        
        // Escudo de protección
        VBox panelEscudo = crearPanelEscudo();
        
        // Estadísticas
        HBox panelEstadisticas = crearPanelEstadisticas();
        
        // Botón de escaneo
        btnScan = new Button("🔍 Escanear Archivo");
        btnScan.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-background-color: #00d4aa; " +
            "-fx-text-fill: white; " +
            "-fx-padding: 15 30 15 30; " +
            "-fx-background-radius: 10; " +
            "-fx-cursor: hand; " +
            "-fx-font-weight: bold;"
        );
        btnScan.setOnAction(e -> handleScanAction(stage));
        
        // Panel de resultado actual (inicialmente oculto)
        panelResultadoActual = crearPanelResultadoActual();
        panelResultadoActual.setVisible(false);
        
        dashboard.getChildren().addAll(panelEscudo, panelEstadisticas, btnScan, panelResultadoActual);
        
        return dashboard;
    }

    /**
     * Crea el panel del escudo de protección.
     */
    private VBox crearPanelEscudo() {
        escudo = new Circle(60);
        escudo.setFill(Color.web("#00d4aa"));
        escudo.setStroke(Color.WHITE);
        escudo.setStrokeWidth(3);
        
        lblEstadoProteccion = new Label("Este PC está Protegido");
        lblEstadoProteccion.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        VBox panelEscudo = new VBox(10, escudo, lblEstadoProteccion);
        panelEscudo.setAlignment(Pos.CENTER);
        
        return panelEscudo;
    }
    
    /**
     * Crea el panel de estadísticas.
     */
    private HBox crearPanelEstadisticas() {
        // Archivos escaneados
        VBox statEscaneados = crearStatBox("Archivos Escaneados", "0");
        lblArchivosEscaneados = (Label) statEscaneados.getChildren().get(1);
        
        // Amenazas detectadas
        VBox statAmenazas = crearStatBox("Amenazas Detectadas", "0");
        lblAmenazasDetectadas = (Label) statAmenazas.getChildren().get(1);
        
        // Archivos limpios
        VBox statLimpios = crearStatBox("Archivos Limpios", "0");
        lblArchivosLimpios = (Label) statLimpios.getChildren().get(1);
        
        HBox panelEstadisticas = new HBox(30, statEscaneados, statAmenazas, statLimpios);
        panelEstadisticas.setAlignment(Pos.CENTER);
        panelEstadisticas.setPadding(new Insets(10));
        panelEstadisticas.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2); -fx-background-radius: 10;");
        
        return panelEstadisticas;
    }
    
    /**
     * Crea una caja de estadística individual.
     */
    private VBox crearStatBox(String titulo, String valor) {
        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-font-size: 12px; -fx-text-fill: #b0b0b0;");
        
        Label lblValor = new Label(valor);
        lblValor.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        VBox box = new VBox(5, lblTitulo, lblValor);
        box.setAlignment(Pos.CENTER);
        
        return box;
    }

    /**
     * Crea el panel de resultado actual (archivo escaneado).
     */
    private VBox crearPanelResultadoActual() {
        Label titulo = new Label("📄 Resultado del Escaneo Actual");
        titulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        // Grid para mostrar información
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));
        
        // Etiquetas
        Label lblArchivoLabel = new Label("Archivo:");
        Label lblPesoLabel = new Label("Tamaño:");
        Label lblEstadoLabel = new Label("Estado:");
        Label lblPeligrosidadLabel = new Label("Peligrosidad:");
        Label lblTipoLabel = new Label("Tipo:");
        
        String estiloLabel = "-fx-font-size: 12px; -fx-text-fill: #b0b0b0;";
        lblArchivoLabel.setStyle(estiloLabel);
        lblPesoLabel.setStyle(estiloLabel);
        lblEstadoLabel.setStyle(estiloLabel);
        lblPeligrosidadLabel.setStyle(estiloLabel);
        lblTipoLabel.setStyle(estiloLabel);
        
        // Valores
        lblArchivoActual = new Label("-");
        lblPesoActual = new Label("-");
        lblEstadoActual = new Label("-");
        lblPeligrosidadActual = new Label("-");
        lblTipoMalwareActual = new Label("-");
        
        String estiloValor = "-fx-font-size: 12px; -fx-text-fill: white; -fx-font-weight: bold;";
        lblArchivoActual.setStyle(estiloValor);
        lblPesoActual.setStyle(estiloValor);
        lblEstadoActual.setStyle(estiloValor);
        lblPeligrosidadActual.setStyle(estiloValor);
        lblTipoMalwareActual.setStyle(estiloValor);
        
        // Agregar al grid
        grid.add(lblArchivoLabel, 0, 0);
        grid.add(lblArchivoActual, 1, 0);
        grid.add(lblPesoLabel, 0, 1);
        grid.add(lblPesoActual, 1, 1);
        grid.add(lblEstadoLabel, 0, 2);
        grid.add(lblEstadoActual, 1, 2);
        grid.add(lblPeligrosidadLabel, 0, 3);
        grid.add(lblPeligrosidadActual, 1, 3);
        grid.add(lblTipoLabel, 0, 4);
        grid.add(lblTipoMalwareActual, 1, 4);
        
        // Botones de acción
        btnEliminarArchivo = new Button("🗑️ Eliminar Archivo");
        btnEliminarArchivo.setStyle(
            "-fx-font-size: 12px; " +
            "-fx-background-color: #e74c3c; " +
            "-fx-text-fill: white; " +
            "-fx-padding: 8 15 8 15; " +
            "-fx-background-radius: 5; " +
            "-fx-cursor: hand;"
        );
        btnEliminarArchivo.setOnAction(e -> eliminarArchivoInfectado());
        
        btnLimpiarInterfaz = new Button("🧹 Limpiar Interfaz");
        btnLimpiarInterfaz.setStyle(
            "-fx-font-size: 12px; " +
            "-fx-background-color: #95a5a6; " +
            "-fx-text-fill: white; " +
            "-fx-padding: 8 15 8 15; " +
            "-fx-background-radius: 5; " +
            "-fx-cursor: hand;"
        );
        btnLimpiarInterfaz.setOnAction(e -> limpiarInterfaz());
        
        HBox botonesBox = new HBox(10, btnEliminarArchivo, btnLimpiarInterfaz);
        botonesBox.setAlignment(Pos.CENTER);
        
        VBox panel = new VBox(10, titulo, grid, botonesBox);
        panel.setPadding(new Insets(15));
        panel.setAlignment(Pos.CENTER);
        panel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3); -fx-background-radius: 10;");
        panel.setMaxWidth(500);
        
        return panel;
    }

    /**
     * Crea el panel de historial con tabla de detecciones.
     */
    @SuppressWarnings("unchecked")
    private VBox crearHistorial() {
        Label titulo = new Label("📋 Historial de Detecciones");
        titulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        // Crear tabla
        tablaHistorial = new TableView<>();
        tablaHistorial.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2);");
        tablaHistorial.setPrefHeight(200);
        
        // Columnas
        TableColumn<Deteccion, String> colFecha = new TableColumn<>("Fecha/Hora");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaHoraFormateada"));
        colFecha.setPrefWidth(150);
        
        TableColumn<Deteccion, String> colArchivo = new TableColumn<>("Archivo");
        colArchivo.setCellValueFactory(new PropertyValueFactory<>("nombreArchivo"));
        colArchivo.setPrefWidth(200);
        
        TableColumn<Deteccion, String> colAmenaza = new TableColumn<>("Amenaza");
        colAmenaza.setCellValueFactory(new PropertyValueFactory<>("amenazaDetectada"));
        colAmenaza.setPrefWidth(150);
        
        TableColumn<Deteccion, String> colTamaño = new TableColumn<>("Tamaño");
        colTamaño.setCellValueFactory(new PropertyValueFactory<>("tamañoFormateado"));
        colTamaño.setPrefWidth(100);
        
        TableColumn<Deteccion, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colEstado.setPrefWidth(100);
        
        TableColumn<Deteccion, String> colPeligrosidad = new TableColumn<>("Peligrosidad");
        colPeligrosidad.setCellValueFactory(new PropertyValueFactory<>("nivelPeligrosidad"));
        colPeligrosidad.setPrefWidth(120);
        
        TableColumn<Deteccion, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoMalware"));
        colTipo.setPrefWidth(120);
        
        tablaHistorial.getColumns().addAll(colFecha, colArchivo, colAmenaza, colTamaño, 
                                           colEstado, colPeligrosidad, colTipo);
        
        // Vincular con el historial
        tablaHistorial.setItems(HistorialDetecciones.getInstancia().getDetecciones());
        
        // Botón limpiar historial
        Button btnLimpiarHistorial = new Button("🗑️ Limpiar Historial");
        btnLimpiarHistorial.setStyle(
            "-fx-font-size: 12px; " +
            "-fx-background-color: #e74c3c; " +
            "-fx-text-fill: white; " +
            "-fx-padding: 8 15 8 15; " +
            "-fx-background-radius: 5; " +
            "-fx-cursor: hand;"
        );
        btnLimpiarHistorial.setOnAction(e -> {
            HistorialDetecciones.getInstancia().limpiarHistorial();
            estadisticas.reiniciar();
            actualizarEstadisticas();
            actualizarEstadoProteccion();
        });
        
        HBox headerBox = new HBox(10, titulo, btnLimpiarHistorial);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setPadding(new Insets(10));
        
        VBox panel = new VBox(5, headerBox, tablaHistorial);
        panel.setPadding(new Insets(10));
        panel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3);");
        
        return panel;
    }

    /**
     * Maneja la acción de escaneo cuando se presiona el botón.
     */
    private void handleScanAction(Stage stage) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Seleccionar archivo para escanear");
        fc.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Todos los archivos", "*.*"),
            new FileChooser.ExtensionFilter("Archivos de texto", "*.txt"),
            new FileChooser.ExtensionFilter("Documentos", "*.pdf", "*.doc", "*.docx"),
            new FileChooser.ExtensionFilter("Ejecutables", "*.exe", "*.bat", "*.scr")
        );
        
        File archivo = fc.showOpenDialog(stage);
        
        if (archivo != null) {
            ejecutarEscaneo(archivo);
        }
    }
    
    /**
     * Ejecuta el escaneo de un archivo.
     */
    private void ejecutarEscaneo(File archivo) {
        archivoActual = archivo;
        btnScan.setDisable(true);
        
        ScannerTask tarea = new ScannerTask(archivo);
        
        tarea.setOnSucceeded(e -> {
            String resultado = tarea.getValue();
            
            // Crear detección
            Deteccion deteccion = new Deteccion(
                archivo.getName(),
                archivo.getAbsolutePath(),
                resultado,
                archivo.length()
            );
            
            // Agregar al historial
            HistorialDetecciones.getInstancia().agregarDeteccion(deteccion);
            
            // Mostrar en panel de resultado
            mostrarResultadoEnPanel(deteccion);
            
            // Actualizar estadísticas
            estadisticas.registrarEscaneo(resultado);
            actualizarEstadisticas();
            actualizarEstadoProteccion();
            actualizarUltimoEscaneo();
            
            btnScan.setDisable(false);
        });
        
        tarea.setOnFailed(e -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error durante el escaneo");
            alert.setContentText("No se pudo completar el escaneo del archivo.");
            alert.showAndWait();
            
            btnScan.setDisable(false);
        });
        
        Thread hilo = new Thread(tarea);
        hilo.setDaemon(true);
        hilo.start();
    }

    /**
     * Muestra el resultado del escaneo en el panel de resultado actual.
     */
    private void mostrarResultadoEnPanel(Deteccion deteccion) {
        // Hacer visible el panel
        panelResultadoActual.setVisible(true);
        
        // Actualizar labels
        lblArchivoActual.setText(deteccion.getNombreArchivo());
        lblPesoActual.setText(deteccion.getTamañoFormateado());
        lblEstadoActual.setText(deteccion.getEstado());
        lblPeligrosidadActual.setText(deteccion.getNivelPeligrosidad());
        lblTipoMalwareActual.setText(deteccion.getTipoMalware());
        
        // Colorear según estado
        if (deteccion.getEstado().equals("Infectado")) {
            lblEstadoActual.setTextFill(Color.web("#e74c3c"));
            
            // Colorear peligrosidad
            switch (deteccion.getNivelPeligrosidad()) {
                case "Peligroso":
                    lblPeligrosidadActual.setTextFill(Color.web("#e74c3c"));
                    break;
                case "Moderado":
                    lblPeligrosidadActual.setTextFill(Color.web("#e67e22"));
                    break;
                case "Ligero":
                    lblPeligrosidadActual.setTextFill(Color.web("#f39c12"));
                    break;
                default:
                    lblPeligrosidadActual.setTextFill(Color.WHITE);
            }
        } else if (deteccion.getEstado().equals("Limpio")) {
            lblEstadoActual.setTextFill(Color.web("#27ae60"));
            lblPeligrosidadActual.setTextFill(Color.web("#27ae60"));
        } else {
            lblEstadoActual.setTextFill(Color.web("#95a5a6"));
            lblPeligrosidadActual.setTextFill(Color.web("#95a5a6"));
        }
    }
    
    /**
     * Elimina el archivo infectado actual.
     */
    private void eliminarArchivoInfectado() {
        if (archivoActual == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("No hay archivo seleccionado");
            alert.setContentText("Primero debes escanear un archivo.");
            alert.showAndWait();
            return;
        }
        
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("¿Estás seguro?");
        confirmacion.setContentText("Se eliminará el archivo: " + archivoActual.getName());
        
        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    Files.delete(archivoActual.toPath());
                    Alert exito = new Alert(Alert.AlertType.INFORMATION);
                    exito.setTitle("Éxito");
                    exito.setHeaderText("Archivo eliminado");
                    exito.setContentText("El archivo ha sido eliminado correctamente.");
                    exito.showAndWait();
                    
                    limpiarInterfaz();
                } catch (IOException ex) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText("No se pudo eliminar el archivo");
                    error.setContentText(ex.getMessage());
                    error.showAndWait();
                }
            }
        });
    }

    /**
     * Limpia la interfaz del panel de resultado actual.
     */
    private void limpiarInterfaz() {
        // Ocultar panel de resultado
        panelResultadoActual.setVisible(false);
        
        // Limpiar labels
        lblArchivoActual.setText("-");
        lblPesoActual.setText("-");
        lblEstadoActual.setText("-");
        lblPeligrosidadActual.setText("-");
        lblTipoMalwareActual.setText("-");
        
        // Limpiar referencia
        archivoActual = null;
    }
    
    /**
     * Actualiza las estadísticas en la interfaz.
     */
    private void actualizarEstadisticas() {
        lblArchivosEscaneados.setText(String.valueOf(estadisticas.getTotalArchivosEscaneados()));
        lblAmenazasDetectadas.setText(String.valueOf(estadisticas.getTotalAmenazasDetectadas()));
        lblArchivosLimpios.setText(String.valueOf(estadisticas.getTotalArchivosLimpios()));
    }
    
    /**
     * Actualiza el estado de protección (escudo).
     */
    private void actualizarEstadoProteccion() {
        if (estadisticas.getTotalAmenazasDetectadas() > 0) {
            escudo.setFill(Color.web("#e74c3c"));
            lblEstadoProteccion.setText("¡Amenazas Detectadas!");
            lblEstadoProteccion.setTextFill(Color.web("#e74c3c"));
        } else {
            escudo.setFill(Color.web("#00d4aa"));
            lblEstadoProteccion.setText("Este PC está Protegido");
            lblEstadoProteccion.setTextFill(Color.WHITE);
        }
    }
    
    /**
     * Actualiza la fecha del último escaneo.
     */
    private void actualizarUltimoEscaneo() {
        java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        lblUltimoEscaneo.setText("Último Escaneo: " + ahora.format(formatter));
    }
}
