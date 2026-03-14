package com.mycompany.antiviruscbd.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Modelo de datos para representar una detección de malware.
 */
public class Deteccion {
    private String nombreArchivo;
    private String rutaCompleta;
    private String amenazaDetectada;
    private LocalDateTime fechaHora;
    private long tamaño;
    private String estado; // "Infectado", "Limpio", "Error"
    private String nivelPeligrosidad; // "Ligero", "Moderado", "Peligroso"
    private String tipoMalware; // "Trojan", "Ransomware", "Spyware", etc.
    
    public Deteccion(String nombreArchivo, String rutaCompleta, String amenazaDetectada, long tamaño) {
        this.nombreArchivo = nombreArchivo;
        this.rutaCompleta = rutaCompleta;
        this.amenazaDetectada = amenazaDetectada;
        this.tamaño = tamaño;
        this.fechaHora = LocalDateTime.now();
        
        // Determinar estado, nivel de peligrosidad y tipo
        if (amenazaDetectada.startsWith("Error")) {
            this.estado = "Error";
            this.nivelPeligrosidad = "N/A";
            this.tipoMalware = "N/A";
        } else if (amenazaDetectada.equals("Archivo limpio")) {
            this.estado = "Limpio";
            this.nivelPeligrosidad = "Ninguno";
            this.tipoMalware = "Ninguno";
        } else {
            this.estado = "Infectado";
            determinarTipoYPeligrosidad(amenazaDetectada);
        }
    }
    
    private void determinarTipoYPeligrosidad(String amenaza) {
        // Determinar tipo de malware y nivel de peligrosidad
        if (amenaza.contains("Trojan")) {
            this.tipoMalware = "Troyano";
            this.nivelPeligrosidad = "Peligroso";
        } else if (amenaza.contains("Ransomware")) {
            this.tipoMalware = "Ransomware";
            this.nivelPeligrosidad = "Peligroso";
        } else if (amenaza.contains("Spyware") || amenaza.contains("KeyLogger")) {
            this.tipoMalware = "Spyware";
            this.nivelPeligrosidad = "Moderado";
        } else if (amenaza.contains("Worm")) {
            this.tipoMalware = "Gusano";
            this.nivelPeligrosidad = "Peligroso";
        } else if (amenaza.contains("Backdoor")) {
            this.tipoMalware = "Puerta Trasera";
            this.nivelPeligrosidad = "Peligroso";
        } else if (amenaza.contains("Miner")) {
            this.tipoMalware = "Minero";
            this.nivelPeligrosidad = "Moderado";
        } else if (amenaza.contains("Adware")) {
            this.tipoMalware = "Adware";
            this.nivelPeligrosidad = "Ligero";
        } else if (amenaza.contains("Exploit")) {
            this.tipoMalware = "Exploit";
            this.nivelPeligrosidad = "Peligroso";
        } else if (amenaza.contains("Macro")) {
            this.tipoMalware = "Macro Maliciosa";
            this.nivelPeligrosidad = "Moderado";
        } else if (amenaza.contains("EICAR")) {
            this.tipoMalware = "Archivo de Prueba";
            this.nivelPeligrosidad = "Ligero";
        } else {
            this.tipoMalware = "Desconocido";
            this.nivelPeligrosidad = "Moderado";
        }
    }
    
    public String getNombreArchivo() {
        return nombreArchivo;
    }
    
    public String getRutaCompleta() {
        return rutaCompleta;
    }
    
    public String getAmenazaDetectada() {
        return amenazaDetectada;
    }
    
    public String getFechaHoraFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return fechaHora.format(formatter);
    }
    
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    
    public long getTamaño() {
        return tamaño;
    }
    
    public String getTamañoFormateado() {
        if (tamaño < 1024) {
            return tamaño + " B";
        } else if (tamaño < 1024 * 1024) {
            return String.format("%.2f KB", tamaño / 1024.0);
        } else {
            return String.format("%.2f MB", tamaño / (1024.0 * 1024.0));
        }
    }
    
    public String getEstado() {
        return estado;
    }
    
    public String getNivelPeligrosidad() {
        return nivelPeligrosidad;
    }
    
    public String getTipoMalware() {
        return tipoMalware;
    }
}
