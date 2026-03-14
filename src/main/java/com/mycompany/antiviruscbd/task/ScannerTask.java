package com.mycompany.antiviruscbd.task;

import com.mycompany.antiviruscbd.Scanner;
import javafx.concurrent.Task;
import java.io.File;

/**
 * Tarea asíncrona para escanear archivos en busca de malware.
 * Extiende JavaFX Task para integración con la UI y procesamiento en segundo plano.
 */
public class ScannerTask extends Task<String> {
    
    private final File archivo;
    
    /**
     * Constructor de la tarea de escaneo.
     * @param archivo El archivo a escanear
     */
    public ScannerTask(File archivo) {
        this.archivo = archivo;
    }
    
    /**
     * Ejecuta el escaneo del archivo en un hilo de fondo.
     * @return El resultado del escaneo (nombre de amenaza o "Archivo limpio")
     * @throws Exception Si ocurre un error durante el escaneo
     */
    @Override
    protected String call() throws Exception {
        // Actualizar mensaje de estado inicial
        updateMessage("Analizando archivo...");
        updateProgress(0, 100);
        
        // Simular análisis profundo con tiempo de procesamiento realista
        Thread.sleep(500);
        updateProgress(30, 100);
        
        updateMessage("Leyendo contenido del archivo...");
        Thread.sleep(300);
        updateProgress(60, 100);
        
        updateMessage("Buscando firmas de malware...");
        
        // Realizar el escaneo real
        String resultado = Scanner.escanear(archivo);
        
        Thread.sleep(200);
        updateProgress(100, 100);
        
        // Actualizar mensaje final basado en el resultado
        if (resultado.equals("Archivo limpio")) {
            updateMessage("✓ Escaneo completado - Archivo limpio");
        } else if (resultado.startsWith("Error")) {
            updateMessage("✗ " + resultado);
        } else {
            updateMessage("⚠ Malware detectado: " + resultado);
        }
        
        return resultado;
    }
}
