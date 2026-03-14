package com.mycompany.antiviruscbd.historial;

import com.mycompany.antiviruscbd.modelo.Deteccion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Gestor del historial de detecciones.
 */
public class HistorialDetecciones {
    private static HistorialDetecciones instancia;
    private ObservableList<Deteccion> detecciones;
    
    private HistorialDetecciones() {
        this.detecciones = FXCollections.observableArrayList();
    }
    
    public static HistorialDetecciones getInstancia() {
        if (instancia == null) {
            instancia = new HistorialDetecciones();
        }
        return instancia;
    }
    
    public void agregarDeteccion(Deteccion deteccion) {
        detecciones.add(0, deteccion); // Agregar al inicio
        
        // Limitar a 100 detecciones
        if (detecciones.size() > 100) {
            detecciones.remove(detecciones.size() - 1);
        }
    }
    
    public ObservableList<Deteccion> getDetecciones() {
        return detecciones;
    }
    
    public void limpiarHistorial() {
        detecciones.clear();
    }
    
    public int getTotalDetecciones() {
        return detecciones.size();
    }
}
